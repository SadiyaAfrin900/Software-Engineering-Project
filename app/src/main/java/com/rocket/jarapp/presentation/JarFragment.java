package com.rocket.jarapp.presentation;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.rocket.jarapp.R;
import com.rocket.jarapp.objects.Jar;

public class JarFragment extends Fragment {

    private EditText jarNameInput;
    private EditText jarBudgetInput;
    private Spinner jarThemeSpinner;
    private String[] jarThemes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jar, container, false);

        jarThemeSpinner = view.findViewById(R.id.jar_theme_spinner);

        // Compile list of all colors
        jarThemes = new String[Jar.Colour.values().length];
        for (int i = 0; i < Jar.Colour.values().length; i++) {
            jarThemes[i] = Jar.Colour.values()[i].name().toLowerCase();
        }

        //creates drop down list for jar themes
        ArrayAdapter<String> themeAdapter = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_list_item_1, jarThemes);
        themeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jarThemeSpinner.setAdapter(themeAdapter);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        jarNameInput = view.findViewById(R.id.jar_name_text);
        jarBudgetInput = view.findViewById(R.id.jar_budget_text);
    }

    public String getJarName() {
        return jarNameInput.getText().toString();
    }

    public String getJarBudget() {
        return jarBudgetInput.getText().toString();
    }

    public Jar.Colour getJarTheme() {
        int position = jarThemeSpinner.getSelectedItemPosition();
        return Jar.Colour.values()[position];
    }

    public void setJarName(String name) {
        jarNameInput.setText(name);
    }

    public void setJarBudgetInput(String budget) {
        jarBudgetInput.setText(budget);
    }

    public void setJarThemeSelector(Jar.Colour jarTheme) {
        for (int i = 0; i < jarThemes.length; i++) {
            String theme = jarThemes[i];
            if (theme.equals(jarTheme.name())) {
                jarThemeSpinner.setSelection(i);
                break;
            }
        }
    }
}
