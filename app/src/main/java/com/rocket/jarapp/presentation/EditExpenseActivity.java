package com.rocket.jarapp.presentation;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rocket.jarapp.R;
import com.rocket.jarapp.business.AccessExpenses;
import com.rocket.jarapp.business.AccessJars;
import com.rocket.jarapp.business.AccessTags;
import com.rocket.jarapp.business.ExpenseValidator;
import com.rocket.jarapp.business.UpdateExpenses;
import com.rocket.jarapp.business.UpdateTags;
import com.rocket.jarapp.objects.Date;
import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.objects.Time;
import com.rocket.jarapp.objects.exceptions.RocketInvalidDateException;

import java.util.ArrayList;
import java.util.List;

public class EditExpenseActivity extends AppCompatActivity {
    public static final String EXPENSE_ID = "expenseID";

    private static final int DEFAULT_ID = -1;

    private AccessExpenses accessExpenses;
    private AccessJars accessJars;
    private Expense selectedExpense;
    private Jar selectedJar;

    private UpdateExpenses updateExpenses;
    private UpdateTags updateTags;

    private EditText expenseName;
    private EditText expenseAmount;
    private EditText expenseNote;
    private EditText expenseDate;
    private EditText expenseTime;

    private ExpenseFragment expenseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_expense);

        setUpActivity();

        // Create updaters
        updateExpenses = new UpdateExpenses();
        updateTags = new UpdateTags();

        // Get the expense selected
        accessExpenses = new AccessExpenses();
        int expenseID = getIntent().getIntExtra(EXPENSE_ID, DEFAULT_ID);
        if (expenseID > DEFAULT_ID) {
            selectedExpense = accessExpenses.getExpenseById(expenseID);

            if (selectedExpense != null) {
                Bundle bundle = new Bundle();
                bundle.putInt(ExpenseFragment.EXPENSE_ID, selectedExpense.getId());

                expenseFragment = new ExpenseFragment();
                expenseFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.expense_fragment, expenseFragment);
                fragmentTransaction.commit();
            }
            else {
                Messages.fatalError(getApplicationContext(), "Could not find that expense");
                finish();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        expenseName = findViewById(R.id.tb_expense_name);
        expenseAmount = findViewById(R.id.tb_expense_price);
        expenseNote = findViewById(R.id.tb_expense_note);
        expenseDate = findViewById(R.id.tb_expense_date);
        expenseTime = findViewById(R.id.tb_expense_time);

        if (selectedExpense != null) {
            expenseName.setText(selectedExpense.getName());
            expenseAmount.setText(Double.toString(selectedExpense.getAmount()));
            expenseNote.setText(selectedExpense.getNote());
            expenseDate.setText(selectedExpense.getDateStr());
            expenseTime.setText(selectedExpense.getTimeStr());
        }
    }

    // Get the jar the expense belongs to
    private void setUpSelectedJar() {
        accessJars = new AccessJars();
        int jarID = getIntent().getIntExtra(CreateExpenseActivity.JAR_INDEX, DEFAULT_ID);
        selectedJar = accessJars.getJarById(jarID);
    }

    //Setup all events (listeners) in the activity
    private void setUpActivity() {
        setUpSelectedJar();
        setUpButtonsOnClick();
    }

    //Setup events for all buttons in this activity
    private void setUpButtonsOnClick() {
        cancelButtonOnClick();
        updateButtonOnClick();
        deleteButtonOnClick();
    }

    private void deleteButtonOnClick() {
        Button delete = findViewById(R.id.edit_expense_delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedJar.deleteExpense(selectedExpense);
                updateExpenses.delete(selectedExpense);
                finish();
            }
        });
    }

    private void updateButtonOnClick() {
        Button update = findViewById(R.id.edit_expense_update);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = expenseFragment.getExpenseAmountStr();
                String dateStr = expenseFragment.getExpenseDateStr();
                String timeStr = expenseFragment.getExpenseTimeStr();
                String result = "";

                String expenseName = expenseFragment.getExpenseName();

                if (expenseName == null || expenseName.length() == 0) {
                    result = "Expense name is empty";
                }
                else if (amountStr == null || amountStr.length() == 0) {
                    result = "Expense price is empty";
                }
                else if (!ExpenseValidator.isAmountNotZero(amountStr)) {
                    result = "Expense price cannot be 0";
                }
                else if (dateStr.isEmpty()) {
                    result = "Please specify a date";
                }
                else if (timeStr.isEmpty()) {
                    result = "Please specify a time";
                }
                else {
                    updateExpenseFromEditText();

                    List<String> newTags = new ArrayList<>();
                    List<Integer> selectedTagPositions = new ArrayList<>();
                    expenseFragment.updateTagData(newTags, selectedTagPositions);

                    for (String tagName : newTags) {
                        Tag newTag = new Tag(updateTags.getNextId(), tagName);
                        updateTags.insert(newTag);
                    }

                    AccessTags accessTags = new AccessTags();
                    for (int position = 0; position < accessTags.getAllTags().size(); position++) {
                        if (selectedTagPositions.contains(position)) {
                            selectedExpense.addTag(accessTags.getAllTags().get(position));
                        }
                        else {
                            selectedExpense.removeTag(accessTags.getAllTags().get(position));
                        }
                    }

                    updateExpenses.update(selectedExpense);
                    selectedJar.recalculateBalance();
                    finish();
                    return;
                }

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cancelButtonOnClick() {
        Button cancel = findViewById(R.id.edit_expense_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Create an expense from user input
    private void updateExpenseFromEditText() {
        selectedExpense.setName(expenseName.getText().toString());
        try {
            selectedExpense.setDate(Date.dateFactory(expenseDate.getText().toString()));
        } catch (RocketInvalidDateException e) {
            selectedExpense.setDate(new Date());
            System.out.println("Could not parse date");
        }

        selectedExpense.setTime(Time.timeFactory(expenseTime.getText().toString()));
        selectedExpense.setNote(expenseNote.getText().toString());

        try {
            selectedExpense.setAmount(Double.parseDouble(expenseAmount.getText().toString()));
        } catch (NumberFormatException ex) {
            Messages.fatalError(getApplicationContext(), "Could not format expense's value, setting price to zero");
            selectedExpense.setAmount(0);
        }
    }
}
