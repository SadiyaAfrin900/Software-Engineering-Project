package com.rocket.jarapp.presentation;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rocket.jarapp.R;
import com.rocket.jarapp.business.AccessExpenses;
import com.rocket.jarapp.business.AccessTags;
import com.rocket.jarapp.business.TagValidator;
import com.rocket.jarapp.objects.Date;
import com.rocket.jarapp.objects.Expense;
import com.rocket.jarapp.objects.Tag;
import com.rocket.jarapp.objects.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExpenseFragment extends Fragment {
    public static final String EXPENSE_ID = "EXPENSE_ID";

    private static final int DEFAULT_EXPENSE_ID = -1;

    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    private Date selectedExpenseDate;
    private Time selectedExpenseTime;

    private EditText expenseName;
    private EditText expenseAmount;
    private EditText expenseNote;
    private EditText expenseDate;
    private EditText expenseTime;

    private List<String> tagNames; //list tag names to show up on the select tag dialog
    private List<String> newTags;  //new created tags for saving to persistence when Update clicked
    private List<Integer> selectedTagPositions; //position list to save selected tag positions when clicking OK and reset when clicking Cancel in the dialog
    private List<Integer> newSelectTagPositions;   //temp position list of tags that are selected directly in the dialog
    private List<Integer> newDeselectTagPositions; //temp position list of tags that are deselected directly in the dialog
    private boolean[] isCheckedTags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        // Get access to the systems domain objects
        AccessTags accessTags = new AccessTags();
        AccessExpenses accessExpenses = new AccessExpenses();

        // Initialize tag lists
        tagNames = accessTags.getAllTagNames();
        newTags = new ArrayList<>();
        selectedTagPositions = new ArrayList<>();
        newSelectTagPositions = new ArrayList<>();
        newDeselectTagPositions = new ArrayList<>();
        isCheckedTags = new boolean[tagNames.size()];

        //Setup tag positions of selected expense for selecting tag dialog
        if (getArguments() != null) {
            Expense selectedExpense = accessExpenses.getExpenseById(getArguments().getInt(EXPENSE_ID, DEFAULT_EXPENSE_ID));

            if (selectedExpense != null) {
                // Determine the indexes of the tagNames list that we should have checked
                for (Tag tag : selectedExpense.getTags()) {
                    if (tagNames.contains(tag.getName())) {
                        selectedTagPositions.add(tagNames.indexOf(tag.getName()));
                        isCheckedTags[tagNames.indexOf(tag.getName())] = true;
                    }
                }
                selectedExpenseDate = selectedExpense.getDate();
                selectedExpenseTime = selectedExpense.getTime();
            }
            else {
                Messages.fatalError(getContext(), "Specified expense id was invalid");
            }
        }
        else {
            selectedExpenseDate = null;
            selectedExpenseTime = null;
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        expenseName = view.findViewById(R.id.tb_expense_name);
        expenseAmount = view.findViewById(R.id.tb_expense_price);
        expenseNote = view.findViewById(R.id.tb_expense_note);
        expenseDate = view.findViewById(R.id.tb_expense_date);
        expenseTime = view.findViewById(R.id.tb_expense_time);
        setUpFragmentEvents();
    }

    //Setup all events (listeners) in the activity
    private void setUpFragmentEvents() {
        setUpDialogs();
        setUpButtonsOnClick();
        setUpOnSetListener();
    }

    //Setup dialog for selectedExpenseDateStr and selectedExpenseTimeStr fields
    private void setUpDialogs() {
        expenseDateOnFocusChange();
        expenseTimeOnFocusChange();
    }

    //Open a selectedExpenseTimeStr picker for user to select a selectedExpenseTimeStr
    private void expenseTimeOnFocusChange() {
        expenseTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendar = Calendar.getInstance();
                    int hour = calendar.get(Calendar.HOUR_OF_DAY);
                    int minute = calendar.get(Calendar.MINUTE);

                    try {
                        if (selectedExpenseTime != null) {
                            String[] timeInfo = selectedExpenseTime.toString().split(":");
                            hour = Integer.valueOf(timeInfo[0]);
                            minute = Integer.valueOf(timeInfo[1]);
                        }
                    } catch (NumberFormatException ex) {
                        Messages.fatalError(getContext(), "Could not parse expense's selectedExpenseTimeStr");
                    } catch (Exception ex) {
                        Messages.fatalError(getContext(), "Expense's selectedExpenseTimeStr was not provided");
                    }

                    TimePickerDialog dialog = new TimePickerDialog(
                            getContext(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            timeSetListener,
                            hour, minute, true
                    );

                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_NEGATIVE) {
                                expenseTime.clearFocus();
                            }
                        }
                    });

                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });
    }

    //Open a selectedExpenseDateStr picker for user to select a selectedExpenseDateStr
    private void expenseDateOnFocusChange() {
        expenseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    int month = calendar.get(Calendar.MONTH);
                    int year = calendar.get(Calendar.YEAR);

                    try {
                        if (selectedExpenseDate != null) {
                            String[] dateInfo = selectedExpenseDate.toString().split("/");
                            day = Integer.valueOf(dateInfo[0]);
                            month = Integer.valueOf(dateInfo[1]) - 1;
                            year = Integer.valueOf(dateInfo[2]);
                        }
                    } catch (NumberFormatException ex) {
                        Messages.fatalError(getContext(), "Could not parse expense's selectedExpenseDateStr");
                    } catch (Exception ex) {
                        Messages.fatalError(getContext(), "Expense's selectedExpenseDateStr was not provided");
                    }

                    DatePickerDialog dialog = new DatePickerDialog(
                            getContext(),
                            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                            dateSetListener,
                            year, month, day
                    );

                    dialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == DialogInterface.BUTTON_NEGATIVE) {
                                expenseDate.clearFocus();
                            }
                        }
                    });
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }
            }
        });
    }

    //Change EditText values when users select new selectedExpenseDateStr-selectedExpenseTimeStr
    private void setUpOnSetListener() {
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String date = String.format(Locale.getDefault(), "%d/%d/%d", dayOfMonth, (month + 1), year);
                expenseDate.setText(date);
                expenseDate.clearFocus();
            }
        };

        timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time = String.format(Locale.getDefault(), "%d:%02d", hourOfDay, minute);
                expenseTime.setText(time);
                expenseTime.clearFocus();
            }
        };
    }

    //Setup events for all buttons in this activity
    private void setUpButtonsOnClick() {
        selectTagsButtonOnClick();
        createTagButtonOnClick();
    }

    private void createTagButtonOnClick() {
        Button createTags = getView().findViewById(R.id.btn_expense_create_tag);
        createTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newTag = getView().findViewById(R.id.tb_expense_new_tag);
                String tag = newTag.getText().toString().toLowerCase();
                String result = "";

                if (!TagValidator.isNotEmpty(tag)) {
                    result = "Cannot create an empty tag";
                }
                else if (!TagValidator.isNotDuplicate(tag, tagNames)) {
                    result = "Tag already exists";
                }
                else {
                    tagNames.add(tag);
                    newTags.add(tag);
                    selectedTagPositions.add(tagNames.size() - 1);

                    //Update new checked position for new added tag
                    boolean[] tempIsCheckedTags = new boolean[tagNames.size()];
                    tempIsCheckedTags[tempIsCheckedTags.length - 1] = true;

                    for (int i = 0; i < isCheckedTags.length; i++) {
                        tempIsCheckedTags[i] = isCheckedTags[i];
                    }
                    isCheckedTags = tempIsCheckedTags;

                    newTag.setText("");
                    hideKeyBoard(newTag);
                    newTag.clearFocus();
                    return;
                }

                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideKeyBoard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void selectTagsButtonOnClick() {
        Button selectTags = getView().findViewById(R.id.btn_expense_select_tags);
        selectTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder tagListBuilder = new AlertDialog.Builder(v.getContext());
                tagListBuilder.setTitle(R.string.tag_list_title);

                //Convert String ArrayList to String array for dialog usage
                String[] tempTagNames = new String[tagNames.size()];
                tempTagNames = tagNames.toArray(tempTagNames);

                newSelectTagPositions = new ArrayList<>();
                newDeselectTagPositions = new ArrayList<>();

                tagListBuilder.setMultiChoiceItems(tempTagNames, isCheckedTags, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            newSelectTagPositions.add(position);

                            if (newDeselectTagPositions.contains(position)) {
                                newDeselectTagPositions.remove(Integer.valueOf(position));
                            }
                        } else {
                            newDeselectTagPositions.add(position);

                            if (newSelectTagPositions.contains(position)) {
                                newSelectTagPositions.remove(Integer.valueOf(position));
                            }
                        }
                    }
                });

                tagListBuilder.setCancelable(false);
                tagListBuilder.setNeutralButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isCheckedTags = new boolean[tagNames.size()];

                        //Revert checklist back to initial state
                        for (int i = 0; i < selectedTagPositions.size(); i++) {
                            isCheckedTags[selectedTagPositions.get(i)] = true;
                        }
                    }
                });

                tagListBuilder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < newSelectTagPositions.size(); i++) {
                            if (!selectedTagPositions.contains(newSelectTagPositions.get(i))) {
                                selectedTagPositions.add(newSelectTagPositions.get(i));
                            }
                        }

                        for (int i = 0; i < newDeselectTagPositions.size(); i++) {
                            if (selectedTagPositions.contains(newDeselectTagPositions.get(i))) {
                                selectedTagPositions.remove(newDeselectTagPositions.get(i));
                            }
                        }
                    }
                });

                AlertDialog tagList = tagListBuilder.create();
                tagList.show();
            }
        });
    }

    public void updateTagData(List<String> tags, List<Integer> selectedTags) {
        tags.addAll(newTags);
        selectedTags.addAll(selectedTagPositions);
    }

    public String getExpenseName() {
        return expenseName.getText().toString();
    }

    public String getExpenseAmountStr() {
        return expenseAmount.getText().toString();
    }

    public String getExpenseNote() {
        return expenseNote.getText().toString();
    }

    public String getExpenseDateStr() {
        return expenseDate.getText().toString();
    }

    public String getExpenseTimeStr() {
        return expenseTime.getText().toString();
    }
}
