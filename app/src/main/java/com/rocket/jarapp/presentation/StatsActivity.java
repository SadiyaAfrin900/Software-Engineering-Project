package com.rocket.jarapp.presentation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.rocket.jarapp.business.Statistics;
import com.rocket.jarapp.objects.Jar;
import java.util.List;

import com.rocket.jarapp.R;
import com.rocket.jarapp.business.AccessJars;

public class StatsActivity extends AppCompatActivity {

    private JarStatRecycleViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        AccessJars accessJars = new AccessJars();
        List<Jar> jars = accessJars.getAllJars();

        TextView totalSavingsTV = findViewById(R.id.jar_stat_total_savings);
        double savings = Statistics.getTotalSavings(jars)*100;
        String totalSavingsString = "TOTAL SAVINGS: "+ Math.round(savings*100)/100.0 + "%";
        totalSavingsTV.setText(totalSavingsString);

        RecyclerView recyclerView = findViewById(R.id.jar_stats_layout_recycler);
        adapter = new JarStatRecycleViewAdapter(jars);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }
}
