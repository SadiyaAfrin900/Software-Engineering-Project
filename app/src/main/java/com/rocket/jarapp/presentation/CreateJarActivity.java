package com.rocket.jarapp.presentation;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.rocket.jarapp.R;
import com.rocket.jarapp.business.JarValidator;
import com.rocket.jarapp.business.UpdateJars;
import com.rocket.jarapp.objects.Jar;

public class CreateJarActivity extends AppCompatActivity {

    private JarFragment jarFragment;
    String name;
    String budget;
    Jar.Colour theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_jar);

        setUpActivity();

        jarFragment = new JarFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.jar_fragment, jarFragment);
        fragmentTransaction.commit();

    }

    //creates new jar with data user inputted
    public void onClickCreate() {
        Button create = findViewById(R.id.create_jar_button);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = jarFragment.getJarName();
                budget = jarFragment.getJarBudget();
                theme = jarFragment.getJarTheme();

                if (JarValidator.isNameValid(name) && JarValidator.isBudgetValid(budget)) {
                    String jarName = jarFragment.getJarName();
                    Jar.Colour jarTheme = jarFragment.getJarTheme();
                    double jarBudget = Double.valueOf(jarFragment.getJarBudget());

                    UpdateJars updateJars = new UpdateJars();
                    jarBudget = Math.round(jarBudget * 100) / 100.0;
                    int nextId = updateJars.getNextId();

                    updateJars.insert(new Jar(nextId, jarBudget, jarName, jarTheme));
                    finish();
                }
            }
        });
    }

    public void onClickCancel() {
        Button cancel = findViewById(R.id.cancel_jar_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setUpActivity() {
        onClickCancel();
        onClickCreate();
    }
}
