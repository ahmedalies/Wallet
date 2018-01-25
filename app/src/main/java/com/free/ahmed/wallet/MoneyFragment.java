package com.free.ahmed.wallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.free.ahmed.wallet.Model.Consts;
import com.free.ahmed.wallet.Model.Resources;
import com.free.ahmed.wallet.Model.ResourcesLap;
import com.free.ahmed.wallet.Model.Specification;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ahmed on 03/10/17.
 */

public class MoneyFragment extends Fragment implements View.OnClickListener{

    private static final String MONTH = "com.free.ahmed.wallet.MoneyFragment.month";
    private static final String FRAGMENT_TYPE = "com.free.ahmed.wallet.MoneyFragment.month";
    public static final int ALL_FOCUS = 0;
    public static final int INCOME_FOCUS = 1;
    public static final int OUTCOME_FOCUS = 2;

    public int fragmentType;

    private RecyclerView mRecyclerView;
    private List<Resources> mResources;
    private ResourcesLap mLap;
    private MoneyRecyclerAdapter mAdapter;
    private int focus;
    private String mMonth;

    private Button allButton;
    private Button incomeButton;
    private Button outcomeButton;

    public static MoneyFragment newInstance(String month, int type) {

        Bundle args = new Bundle();
        args.putString(MONTH, month);
        args.putInt(FRAGMENT_TYPE, type);

        MoneyFragment fragment = new MoneyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_money, container, false);

        allButton = (Button) v.findViewById(R.id.all_money_button);
        incomeButton = (Button) v.findViewById(R.id.income_money_button);
        outcomeButton = (Button) v.findViewById(R.id.outcome_money_button);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.money_rec_view);

        allButton.setOnClickListener(this);
        incomeButton.setOnClickListener(this);
        outcomeButton.setOnClickListener(this);

        mMonth = getArguments().getString(MONTH, Calendar.getInstance().getTime().toString().substring(4, 7));
        focus = ALL_FOCUS;
        fragmentType = getArguments().getInt(FRAGMENT_TYPE);
        allButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.dark_left_yellow_button));
        incomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.center_yellow_button));
        outcomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.right_yellow_button));
        mLap = new ResourcesLap(getActivity());
        updateView(focus);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.all_money_button:
                if (focus == ALL_FOCUS)
                    break;
                focus = ALL_FOCUS;
                allButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.dark_left_yellow_button));
                incomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.center_yellow_button));
                outcomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.right_yellow_button));
                updateView(focus);
                break;
            case R.id.income_money_button:
                if (focus == INCOME_FOCUS)
                    break;
                focus = INCOME_FOCUS;
                allButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.left_yellow_button));
                incomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.dark_center_yellow_button));
                outcomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.right_yellow_button));
                updateView(focus);
                break;
            case R.id.outcome_money_button:
                if (focus == OUTCOME_FOCUS)
                    break;
                focus = OUTCOME_FOCUS;
                allButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.left_yellow_button));
                incomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.center_yellow_button));
                outcomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.dark_right_yellow_button));
                updateView(focus);
                break;
        }
    }

    private class MoneyViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView dateTextView;
        TextView amountTextView;
        ImageView colorImageView;
        ImageButton editImageButton;
        ImageButton deleteImageButton;


        public MoneyViewHolder(View itemView) {
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.money_name_text_view);
            dateTextView = (TextView) itemView.findViewById(R.id.money_date_text_view);
            amountTextView = (TextView) itemView.findViewById(R.id.money_amount_text_view);
            colorImageView = (ImageView) itemView.findViewById(R.id.money_color_image_view);
            editImageButton = (ImageButton) itemView.findViewById(R.id.money_edit_image_button);
            deleteImageButton = (ImageButton) itemView.findViewById(R.id.money_delete_image_button);

            editImageButton.setVisibility(View.GONE);
            deleteImageButton.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(MoneyOperationActivity.newIntent(getActivity(), mResources.get(getAdapterPosition())));
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    changeLayout();
                    return true;
                }
            });

            deleteImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mLap.deleteResource(mResources.get(getAdapterPosition()));
                    mResources.remove(mResources.indexOf(mResources.get(getAdapterPosition())));
                    mAdapter.notifyDataSetChanged();

                }
            });

            editImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = MoneyOperationActivity.newIntent(getActivity(), mResources.get(getAdapterPosition()));
                    startActivity(intent);
                }
            });
        }

        private void changeLayout(){

            if(editImageButton.getVisibility() == View.GONE && deleteImageButton.getVisibility() == View.GONE){
                editImageButton.setVisibility(View.VISIBLE);
                deleteImageButton.setVisibility(View.VISIBLE);
            } else {
                editImageButton.setVisibility(View.GONE);
                deleteImageButton.setVisibility(View.GONE);
            }
        }

        public void bind(final Resources resources){
            nameTextView.setText(resources.getSpecDetails().getName());
            dateTextView.setText(resources.getCreated_at());
            if (!resources.isIncome()) {
                amountTextView.setTextColor(Color.RED);
                amountTextView.setText("-" + resources.getAmount());
            } else {
                amountTextView.setTextColor(getActivity().getResources().getColor(R.color.green));
                amountTextView.setText("+" + resources.getAmount());
            }
            colorImageView.setImageDrawable(new ColorDrawable(resources.getSpecDetails().getColor()));
        }
    }
    private class MoneyRecyclerAdapter extends RecyclerView.Adapter<MoneyViewHolder>{

        private List<Resources> mResources;

        public MoneyRecyclerAdapter(List<Resources> resources){
            this.mResources = resources;
        }

        @Override
        public MoneyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View v = inflater.inflate(R.layout.money_card, parent, false);
            return new MoneyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final MoneyViewHolder holder, final int position) {
            holder.bind(mResources.get(position));
        }

        @Override
        public int getItemCount() {
            return mResources.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateMonth();
    }

    public void updateMonth(){
        mMonth = Consts.month;
        updateView(focus);
    }

    public void updateView(int f){
        mMonth = Consts.month;

        if(f == ALL_FOCUS){
            getAllFocus();
        } else if(f == INCOME_FOCUS){
            getIncomeFocus();
        } else if(f == OUTCOME_FOCUS){
            getOutcomeFocus();
        }


        mAdapter = new MoneyRecyclerAdapter(mResources);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void getAllFocus(){
        mResources = mLap.getIncomes(mMonth);
        List<Resources> tempIncomeResources = new ArrayList<>();
        List<Resources> tempOutcomeResources = new ArrayList<>();
        for (Resources resource : mResources) {
            Specification specification = mLap.getIncomeSpecifications(resource.getSpecId());
            resource.setSpecDetails(specification);
            resource.setIncome(true);
            tempIncomeResources.add(resource);
        }
        mResources = mLap.getOutcomes(mMonth);
        for (Resources resource : mResources) {
            Specification specification = mLap.getOutcomeSpecifications(resource.getSpecId());
            resource.setSpecDetails(specification);
            resource.setIncome(false);
            tempOutcomeResources.add(resource);
        }
        mResources = tempIncomeResources;
        mResources.addAll(tempOutcomeResources);
    }

    private void getIncomeFocus(){
        mResources = mLap.getIncomes(mMonth);
        List<Resources> tempResources = new ArrayList<>();
        for (Resources resource : mResources) {
            Specification specification = mLap.getIncomeSpecifications(resource.getSpecId());
            resource.setSpecDetails(specification);
            resource.setIncome(true);
            tempResources.add(resource);
        }
        mResources = tempResources;
    }

    private void getOutcomeFocus(){
        mResources = mLap.getOutcomes(mMonth);
        List<Resources> tempResources = new ArrayList<>();
        for (Resources resource : mResources) {
            Specification specification = mLap.getOutcomeSpecifications(resource.getSpecId());
            resource.setSpecDetails(specification);
            resource.setIncome(false);
            tempResources.add(resource);
        }
        mResources = tempResources;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
