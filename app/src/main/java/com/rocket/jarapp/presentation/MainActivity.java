package com.rocket.jarapp.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.rocket.jarapp.R;
import com.rocket.jarapp.business.AccessJars;
import com.rocket.jarapp.objects.Jar;
import com.rocket.jarapp.persistence.utils.DBHelper;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity {

    //View components
    private ViewPager viewPager;
    private ScreenSlidePageAdapter pagerAdapter;
    private FloatingActionButton addExpenseButton;

    private AccessJars accessJars;
    private List<Jar> jars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DBHelper.copyDatabaseToDevice(this);

        // Setup jar data set
        accessJars = new AccessJars();
        jars = accessJars.getAllJars();

        // Create view page
        viewPager = findViewById(R.id.home_view_pager);
        pagerAdapter = new ScreenSlidePageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        // Setup circle indicator
        CircleIndicator indicator = findViewById(R.id.home_circle_indicator);
        indicator.setViewPager(viewPager);

        pagerAdapter.registerDataSetObserver(indicator.getDataSetObserver());

        //Setup edit expense floating action button
        addExpenseButton = findViewById(R.id.fab_add_expense);
        addExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CreateExpenseActivity.class);
                intent.putExtra("jarIndex",viewPager.getCurrentItem());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        jars = accessJars.getAllJars();
        pagerAdapter.updateView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_task_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.task_menu_add_jar:
                Intent newJarIntent = new Intent(this, CreateJarActivity.class);
                startActivity(newJarIntent);
                return true;
            case R.id.task_menu_stats:
                Intent statsIntent = new Intent(this, StatsActivity.class);
                startActivity(statsIntent);
                return true;
            case R.id.task_see_all_expenses:
                Intent allExpensesIntent = new Intent(this, AllExpensesActivity.class);
                startActivity(allExpensesIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    /**
     * ScreenSlidPageAdapter
     *
     * Responsible for generating new jar pages upon request.
     */
    private class ScreenSlidePageAdapter extends FragmentStatePagerAdapter {

        ScreenSlidePageAdapter(FragmentManager fm) {
            super(fm);
        }

        public void updateView() {
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            if (jars.contains(object)) {
                return jars.indexOf(object);
            } else {
                return POSITION_NONE;
            }
        }

        @Override
        public Fragment getItem(int index) {
            return new JarPageFragment(jars.get(index));
        }

        @Override
        public int getCount() {
            return jars.size();
        }
    }
}
