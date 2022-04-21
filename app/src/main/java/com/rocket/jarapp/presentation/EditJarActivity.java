package com.rocket.jarapp.presentation;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rocket.jarapp.R;
import com.rocket.jarapp.business.AccessJars;
import com.rocket.jarapp.business.JarValidator;
import com.rocket.jarapp.business.UpdateJars;
import com.rocket.jarapp.objects.Jar;

public class EditJarActivity extends AppCompatActivity {

    public static String JAR_ID = null;

    private AccessJars accessJars;
    private Jar jar;

    String name;
    double budget;
    Jar.Colour theme;
    int id;

    private JarFragment jarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_jar);

        setUpActivity();

        jarFragment = new JarFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.jar_fragment, jarFragment);
        fragmentTransaction.commit();

        accessJars = new AccessJars();
        id = getIntent().getIntExtra(JAR_ID, 0);
    }

    @Override
    public void onStart() {
        super.onStart();

        jar = accessJars.getJarById(id);
        // pass existing jar data to text edit fields
        if (jar != null) {
            name = jar.getName();
            budget = jar.getBudget();

            jarFragment.setJarName(name);
            jarFragment.setJarBudgetInput(String.valueOf((int) budget));
        }

        // Set the theme selector to the theme of the given jar
        jarFragment.setJarThemeSelector(jar.getTheme());

    }

    private void setUpActivity() {
        onClickCancel();
        onClickSave();
        onClickDelete();
    }

    public void onClickSave() {
        Button saveEditButton = findViewById(R.id.edit_jar_save_jar_button);
        saveEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                theme = jarFragment.getJarTheme();
                name = jarFragment.getJarName();
                String budgetStr = jarFragment.getJarBudget();

                if (JarValidator.isNameValid(name) && JarValidator.isBudgetValid(budgetStr)) {

                    budget = Double.valueOf(budgetStr);
                    double spendAmount = jar.getBudget() - jar.getBalance();


                    if (budget < spendAmount) {
                        jar.setBalance(0);
                    } else {
                        jar.setBalance(budget - spendAmount);
                    }

                    jar.setName(name);
                    jar.setBudget(budget);
                    jar.setTheme(theme);

                    UpdateJars updater = new UpdateJars();
                    updater.update(jar);

                    finish();
                }
            }
        });
    }

    public void onClickCancel() {
        Button cancelEditButton = findViewById(R.id.cancel_jar_button);
        cancelEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClickDelete() {
        Button deleteJarButton = findViewById(R.id.edit_jar_delete_jar_button);
        deleteJarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateJars updater = new UpdateJars();
                updater.delete(jar);
                finish();
            }
        });
    }
}
