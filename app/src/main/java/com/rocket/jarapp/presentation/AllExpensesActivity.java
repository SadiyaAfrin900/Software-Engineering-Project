package com.rocket.jarapp.presentation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rocket.jarapp.R;
import com.rocket.jarapp.business.AccessExpenses;

public class AllExpensesActivity extends AppCompatActivity {

    private AllExpenseRecycleViewAdapter recycleViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_expenses);

        // Access to the systems expenses
        AccessExpenses accessExpenses = new AccessExpenses();

        // Setup the list of expenses
        RecyclerView recyclerView = findViewById(R.id.all_expenses_layout_recycler);
        recycleViewAdapter = new AllExpenseRecycleViewAdapter(accessExpenses.getAllExpenses());
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }
}
