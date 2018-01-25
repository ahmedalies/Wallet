package com.free.ahmed.wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.free.ahmed.wallet.Model.Consts;
import com.free.ahmed.wallet.Model.Resources;
import com.free.ahmed.wallet.Model.ResourcesLap;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by ahmed on 03/10/17.
 */

public class SummeryFragment extends Fragment {

    private static final String MONTH = "com.free.ahmed.wallet.SummeryFragment.month";
    private static final String FRAGMENT_TYPE = "com.free.ahmed.wallet.SummeryFragment.fragment_type";

    private TextView incomeTextView;
    private TextView outcomeTextView;
    private TextView netCreditTextView;

    private ResourcesLap mLap;
    private String mMonth;
    public int fragmentType;

    public static SummeryFragment newInstance(String month, int type) {

        Bundle args = new Bundle();
        args.putString(MONTH, month);
        args.putInt(FRAGMENT_TYPE, type);

        SummeryFragment fragment = new SummeryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public  int x;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_summery, container, false);

        mLap = new ResourcesLap(getActivity());

        incomeTextView = (TextView) v.findViewById(R.id.net_income_value);
        outcomeTextView = (TextView) v.findViewById(R.id.net_outcome_value);
        netCreditTextView = (TextView) v.findViewById(R.id.net_credit);

        fragmentType = getArguments().getInt(FRAGMENT_TYPE);

        mMonth = getArguments().getString(MONTH, Calendar.getInstance().getTime().toString().substring(4, 7));

        calculateNetValues();

        return v;
    }

    public void calculateNetValues(){
        List<Resources> incomeResources = mLap.getIncomes(mMonth);
        float incomeNetValue = 0;
        float outComeNetValues = 0;

        for (Resources resources : incomeResources) {
            incomeNetValue += resources.getAmount();
        }

        List<Resources> outcomeResources = mLap.getOutcomes(mMonth);
        for (Resources resources : outcomeResources) {
            outComeNetValues += resources.getAmount();
        }

        incomeTextView.setText(incomeNetValue + "");
        outcomeTextView.setText(outComeNetValues + "");
        netCreditTextView.setText((incomeNetValue - outComeNetValues) + "");
    }

    @Override
    public void onResume() {
        super.onResume();
        mMonth = Consts.month;
        calculateNetValues();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMonth = Consts.month;
        calculateNetValues();
    }
}
