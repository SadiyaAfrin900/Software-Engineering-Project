package com.rocket.jarapp.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocket.jarapp.R;
import com.rocket.jarapp.objects.Expense;

import java.util.List;

public class AllExpenseRecycleViewAdapter extends RecyclerView.Adapter<AllExpenseRecycleViewAdapter.AllExpenseViewHolder> {

    private List<Expense> expenseList;

    public AllExpenseRecycleViewAdapter(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    @NonNull
    @Override
    public AllExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expense_list_item_layout, viewGroup, false);
        return new AllExpenseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllExpenseViewHolder allExpenseViewHolder, int i) {
        Expense expense = expenseList.get(i);

        allExpenseViewHolder.amountTV.setText(String.format("$%s", expense.getAmount()));
        allExpenseViewHolder.nameTV.setText(expense.getName());
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class AllExpenseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView amountTV;
        private TextView nameTV;

        public AllExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            amountTV = itemView.findViewById(R.id.expense_list_item_amount);
            nameTV = itemView.findViewById(R.id.expense_list_item_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) { }
    }

}
