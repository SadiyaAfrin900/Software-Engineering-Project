package com.rocket.jarapp.presentation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocket.jarapp.R;
import com.rocket.jarapp.objects.Expense;

import java.util.List;

public class HomeExpenseRecycleViewAdapter extends RecyclerView.Adapter<HomeExpenseRecycleViewAdapter.HomeExpenseViewHolder> {
    private List<Expense> expenses;
    private int jarId;

    public HomeExpenseRecycleViewAdapter(List<Expense> expenses, int jarId) {
        this.expenses = expenses;
        this.jarId = jarId;
    }

    @NonNull
    @Override
    public HomeExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expense_list_item_layout, parent, false);
        HomeExpenseViewHolder holder = new HomeExpenseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeExpenseViewHolder expenseViewHolder, int i) {
        Expense expense = expenses.get(i);

        expenseViewHolder.amountTV.setText(String.format("$%s", expense.getAmount()));
        expenseViewHolder.nameTV.setText(expense.getName());
        expenseViewHolder.expense = expense;
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    /**
     * updateExpenseList
     *
     * Informs the HomeExpenseRecycleViewAdapter that the data it is representing has changed
     * and that the view must be reloaded.
     */
    public void updateExpenseList(List<Expense> expenses) {
        this.expenses = expenses;
        notifyDataSetChanged();
    }

    /**
     * HomeExpenseViewHolder
     *
     * Represents a single expense list item.
     */
    public class HomeExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView amountTV;
        private TextView nameTV;
        private Expense expense;

        public HomeExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            amountTV = itemView.findViewById(R.id.expense_list_item_amount);
            nameTV = itemView.findViewById(R.id.expense_list_item_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), EditExpenseActivity.class);
            intent.putExtra(EditExpenseActivity.EXPENSE_ID, expense.getId());
            intent.putExtra(CreateExpenseActivity.JAR_INDEX, jarId);
            itemView.getContext().startActivity(intent);
        }
    }

}

