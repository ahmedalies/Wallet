package com.free.ahmed.wallet;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.camera2.params.Face;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.transition.Fade;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.free.ahmed.wallet.Model.Consts;

import java.util.List;


public class AppActivity extends AppCompatActivity implements TransactionFragment.TransactionCallback{

    public static final int DATE_ACTIVITY_REQUEST = 1;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabPagerAdapter adapter;
    private LinearLayout linearLayout;
    private TextView textView;
    private ViewGroup fabLayout;
    private FloatingActionButton fab;
    private String mMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                TransactionFragment.newInstance().show(getSupportFragmentManager(), null);
            }
        });

        fabLayout = (ViewGroup) findViewById(R.id.activity_fab_layout);

        final Button button = new Button(AppActivity.this);
        textView = new TextView(AppActivity.this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textView.getText().toString().contentEquals(getResources().getString(R.string.app_name))){
                    Intent intent = DateActivity.getIntent(AppActivity.this);
                    startActivityForResult(intent, DATE_ACTIVITY_REQUEST);
                }
            }
        });

        linearLayout = (LinearLayout) findViewById(R.id.app_container);

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(8, 0, 8, 0);
        linearLayout.addView(configureTitleTextView(textView, R.string.date), params);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);

        tabLayout = (TabLayout) findViewById(R.id.app_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.summery).setIcon(R.drawable.ic_summery));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.money).setIcon(R.drawable.ic_money));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.setting).setIcon(R.drawable.ic_setting));

        viewPager = (ViewPager) findViewById(R.id.app_view_pager);

        mMonth = java.util.Calendar.getInstance().getTime().toString().substring(4, 7);
        updateView(mMonth);
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        linearLayout.removeAllViews();
                        linearLayout.addView(configureTitleTextView(textView, R.string.date), params);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
                        changeFabVisible(View.VISIBLE);
                        //fab.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        linearLayout.removeAllViews();
                        linearLayout.addView(configureTitleTextView(textView, R.string.date), params);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
                        changeFabVisible(View.VISIBLE);
                        //fab.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        linearLayout.removeAllViews();
                        linearLayout.addView(configureTitleTextView(textView, R.string.app_name), params);
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19);
                        changeFabVisible(View.GONE);
                        //fab.setVisibility(View.GONE);
                        break;
                }
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 1:
                        linearLayout.removeView(button);
                        break;
                    default:
                        linearLayout.removeView(textView);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_summery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private TextView configureTitleTextView(TextView textView, int titleId){
        if(!(titleId == R.string.app_name)) {
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            textView.setText(mMonth);
            textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.dark_blue_bakground));
        } else{
            textView.setText(titleId);
            textView.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            textView.setTextAppearance(R.style.TextAppearance_AppCompat_Widget_PopupMenu_Small);
        }
        else
            textView.setTextAppearance(AppActivity.this ,R.style.TextAppearance_AppCompat_Widget_PopupMenu_Small);

        textView.setTextSize(25);
        textView.setTextColor(Color.WHITE);

        textView.setCompoundDrawables(null, null, getResources().getDrawable(R.drawable.ic_arrow), null);

        return textView;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DATE_ACTIVITY_REQUEST && resultCode == RESULT_OK){
            mMonth = data.getStringExtra(DateActivity.DATE_KEY);
            updateView(mMonth);
        }
    }

    private void updateView(String month){

        adapter = new TabPagerAdapter(
                getSupportFragmentManager(),
                tabLayout.getTabCount(),
                month);

        Consts.month = month;

        textView.setText(month);
    }

    private void changeFabVisible(int vis){
        Transition transition;
        if (vis == View.GONE){
            transition = new Fade(Fade.OUT);
        } else {
            transition = new Fade(Fade.IN);
        }

        transition.setDuration(3000);
        transition.setInterpolator(new AccelerateDecelerateInterpolator());
        transition.addTarget(R.id.fab);
        TransitionManager.beginDelayedTransition(fabLayout, transition);

        fab.setVisibility(vis);
    }


    @Override
    public void updateResources() {
        List<Fragment> allFragments = getSupportFragmentManager().getFragments();
        if(allFragments != null){
            for (int i = 0; i < allFragments.size(); i++) {
                if (allFragments.get(i).getClass() == MoneyFragment.class){
                    MoneyFragment f = (MoneyFragment) allFragments.get(i);
                    if (f.fragmentType == Consts.MONEY_FRAGMENT_TYPE){
                        f.updateView(MoneyFragment.ALL_FOCUS);
                    }
                } else if (allFragments.get(i).getClass() == SummeryFragment.class){
                    SummeryFragment f = (SummeryFragment) allFragments.get(i);
                    if (f.fragmentType == Consts.SUMMERY_FRAGMENT_TYPE){
                        f.calculateNetValues();
                    }
                }
            }
        }
    }

    private void testUp(){
        int i = 1 + 1;
    }
}
