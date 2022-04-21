package com.rocket.jarapp.presentation;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rocket.jarapp.R;
import com.rocket.jarapp.business.Statistics;
import com.rocket.jarapp.objects.Jar;

import java.util.List;

public class JarStatRecycleViewAdapter extends RecyclerView.Adapter<JarStatRecycleViewAdapter.JarStatExpenseViewHolder> {

    private List<Jar> jars;

    public JarStatRecycleViewAdapter(List<Jar> jars) {
        this.jars = jars;
    }

    @NonNull
    @Override
    public JarStatExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jar_stat_list_item_layout, parent, false);
        JarStatExpenseViewHolder holder = new JarStatExpenseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull JarStatExpenseViewHolder jarStatExpenseViewHolder, int i) {
        Jar jar = jars.get(i);
        double jarSavings = Statistics.getSavings(jar)*100;
        double averageExpense = Statistics.getAverageExpense(jar)*100;

        String savingsString = String.format("%.2f%%", jarSavings);
        String averageExpenseString = String.format("%.2f$", averageExpense);
        String numExpensesString = String.format("%d", jar.getNumExpenses());

        jarStatExpenseViewHolder.jarName.setText(jar.getName());
        jarStatExpenseViewHolder.savings.setText(savingsString);
        jarStatExpenseViewHolder.numOfExpenses.setText(numExpensesString);
        jarStatExpenseViewHolder.averageExpenseAmount.setText(averageExpenseString);
    }

    @Override
    public int getItemCount() {
        return jars.size();
    }

    public class JarStatExpenseViewHolder extends RecyclerView.ViewHolder {
        private TextView savings;
        private TextView numOfExpenses;
        private TextView averageExpenseAmount;
        private TextView jarName;

        public JarStatExpenseViewHolder(@NonNull View itemView) {
            super(itemView);
            savings = itemView.findViewById(R.id.jar_stat_list_item_savings);
            numOfExpenses = itemView.findViewById(R.id.jar_stat_list_item_num_of_expenses);
            averageExpenseAmount = itemView.findViewById(R.id.jar_stat_list_item_avg_expense_amount);
            jarName = itemView.findViewById(R.id.jar_stat_list_item_jar_name);
        }
    }
}
