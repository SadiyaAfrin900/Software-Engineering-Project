package com.rocket.jarapp.presentation;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.rocket.jarapp.R;
import com.rocket.jarapp.objects.Jar;

@SuppressLint("ValidFragment")
public class JarPageFragment extends Fragment {
    private HomeExpenseRecycleViewAdapter recycleViewAdapter;
    private Jar jar;

    public JarPageFragment() {}

    public JarPageFragment(Jar jar){
        this.jar = jar;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup rootJarView = (ViewGroup) inflater.inflate(R.layout.jar_layout, container, false);

        // Set the background color
        ConstraintLayout header = rootJarView.findViewById(R.id.jar_layout_header);
        header.setBackgroundColor(getContext().getColor(getColorCode(jar.getTheme())));

        // Set the jar's name
        TextView jarNameTV = rootJarView.findViewById(R.id.jar_layout_name);
        jarNameTV.setText(jar.getName());

        // Set the jar's budget
        TextView jarBudgetTV = rootJarView.findViewById(R.id.jar_layout_budget);
        jarBudgetTV.setText(String.format("%s / %s", jar.getBalance(), jar.getBudget()));

        // Setup the list of expenses
        RecyclerView recyclerView = rootJarView.findViewById(R.id.jar_layout_recycler);
        recycleViewAdapter = new HomeExpenseRecycleViewAdapter(jar.getExpenses(), jar.getId());
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Enable the edit button to launch the edit jar activity
        ImageButton jarEditButton = rootJarView.findViewById(R.id.jar_layout_edit);
        jarEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch the edit jar activity here, passing jar in as a parameter
                Intent intent = new Intent(v.getContext(), EditJarActivity.class);
                intent.putExtra(EditJarActivity.JAR_ID, jar.getId());
                startActivity(intent);
            }
        });

        return rootJarView;
    }

    /**
     * getColorCode
     *
     * Translate the theme color into a color resource id
     */
    private int getColorCode(Jar.Colour jarTheme) {
        switch (jarTheme) {
            case BLUE:
                return R.color.blue;
            case GREEN:
                return R.color.green;
            case PURPLE:
                return R.color.purple;
            case RED:
                return R.color.red;
            case YELLOW:
                return R.color.yellow;
            default:
                return R.color.black;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        recycleViewAdapter.updateExpenseList(jar.getExpenses());
    }
}
