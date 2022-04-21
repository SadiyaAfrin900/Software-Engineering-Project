package com.rocket.jarapp.presentation;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rocket.jarapp.R;
import com.rocket.jarapp.business.AccessJars;
import com.rocket.jarapp.business.AccessTags;
import com.rocket.jarapp.business.ExpenseValidator;
import com.rocket.jarapp.business.UpdateExpenses;
import com.rocket.jarapp.business.UpdateJars;
import com.rocket.jarapp.business.UpdateTags;
import com.rocket.jarapp.objects.Date;
import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.objects.Time;
import com.rocket.jarapp.objects.exceptions.RocketInvalidDateException;

import java.util.ArrayList;
import java.util.List;

public class CreateExpenseActivity extends AppCompatActivity {

    public static final String JAR_INDEX = "jarIndex";

    private static final int DEFAULT_VALUE = -1;

    private AccessJars accessJars;

    private UpdateJars updateJars;
    private UpdateExpenses updateExpenses;
    private UpdateTags updateTags;

    private Jar jar;

    private ExpenseFragment expenseFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_expense);

        setUpActivity();

        updateJars = new UpdateJars();
        updateExpenses = new UpdateExpenses();
        updateTags = new UpdateTags();

        expenseFragment = new ExpenseFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.expense_fragment, expenseFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void setUpActivity() {
        setUpSelectedJar();
        setUpButtonsOnClick();
    }

    // Get the jar the expense belongs to
    private void setUpSelectedJar() {
        accessJars = new AccessJars();
        int jarID = getIntent().getIntExtra(CreateExpenseActivity.JAR_INDEX, DEFAULT_VALUE);
        jar = accessJars.getJarById(jarID);
    }

    private void setUpButtonsOnClick() {
        cancelButtonOnClick();
        saveButtonOnClick();
    }

    public void saveButtonOnClick() {
        Button save = findViewById(R.id.create_expense_save);
        save.setOnClickListener(new View.OnClickListener() {
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
                    Expense newExpense = createExpenseFromEditText();

                    List<String> newTags = new ArrayList<>();
                    List<Integer> selectedTagPositions = new ArrayList<>();
                    expenseFragment.updateTagData(newTags, selectedTagPositions);

                    for (String tagName : newTags) {
                        Tag newTag = new Tag(updateTags.getNextId(), tagName);
                        updateTags.insert(newTag);
                    }

                    AccessTags accessTags = new AccessTags();
                    for (int position : selectedTagPositions) {
                        newExpense.addTag(accessTags.getAllTags().get(position));
                    }

                    updateExpenses.insert(newExpense);
                    jar.addExpense(newExpense);
                    updateJars.update(jar);
                    finish();
                    return;
                }

                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cancelButtonOnClick() {
        Button cancel = findViewById(R.id.create_expense_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private Expense createExpenseFromEditText() {
        String name = expenseFragment.getExpenseName();

        Date date;
        try {
            date = Date.dateFactory(expenseFragment.getExpenseDateStr());
        } catch (RocketInvalidDateException e) {
            date = new Date();
        }

        Time time = Time.timeFactory(expenseFragment.getExpenseTimeStr());
        String note = expenseFragment.getExpenseNote();

        double amount = 0;
        try {
            amount = Double.parseDouble(expenseFragment.getExpenseAmountStr());
        } catch (NumberFormatException ex) {
            Messages.warning(getApplicationContext(), "expense amount was an invalid number, setting value to zero");
        }

        return new Expense(updateExpenses.getNextId(), name, note, amount, date, time);
    }
}
