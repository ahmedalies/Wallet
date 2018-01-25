package com.free.ahmed.wallet;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.ahmed.wallet.Model.ResourcesLap;
import com.free.ahmed.wallet.Model.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpecificationActivity extends AppCompatActivity implements EditSpecificationFragment.EditSpecificationCallback{

    private static final int INCOME = 0;
    private static final int OUTCOME = 1;


    private SpecificationRecyclerAdapter incomeAdapter;
    private SpecificationRecyclerAdapter outcomeAdapter;
    private ResourcesLap mLap;
    private RecyclerView incomeRecyclerView;
    private RecyclerView outcomeRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specification);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent intent = NewSpecificationActivity.newIntent(SpecificationActivity.this);
                startActivity(intent);
            }
        });

        incomeRecyclerView = (RecyclerView) findViewById(R.id.income_rec_view);
        outcomeRecyclerView = (RecyclerView) findViewById(R.id.outcome_rec_view);

        mLap = new ResourcesLap(SpecificationActivity.this);

        updateView();
    }

    @Override
    public void updateResources() {
        updateView();
    }


    private class SpecificationHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView mTextView;
        private boolean isIncome;
        private boolean isOutcome;
        private int color;
        private String uuid;

        public SpecificationHolder(View itemView) {
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.spec_color_image_view);
            mTextView = (TextView) itemView.findViewById(R.id.spec_name_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EditSpecificationFragment fragment = EditSpecificationFragment.newInstance(
                            uuid,
                            color,
                            mTextView.getText().toString(),
                            isIncome,
                            isOutcome
                    );

                    fragment.show(getSupportFragmentManager(), null);
                }
            });
        }

        public void bind(String id, String name, int color, boolean isIncome, boolean isOutcome){
            mImageView.setImageDrawable(new ColorDrawable(color));
            mTextView.setText(name);
            this.isIncome = isIncome;
            this.isOutcome = isOutcome;
            this.color = color;
            this.uuid = id;
        }
    }


    private class SpecificationRecyclerAdapter extends RecyclerView.Adapter<SpecificationHolder>{

        private List<Specification> spec_s;
        private int specType;

        public SpecificationRecyclerAdapter(List<Specification> spec_s, int specType){
            this.spec_s = spec_s;
            this.specType = specType;
        }

        @Override
        public SpecificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(SpecificationActivity.this);
            View v = inflater.inflate(R.layout.specification_card, parent, false);
            return new SpecificationHolder(v);
        }

        @Override
        public void onBindViewHolder(SpecificationHolder holder, int position) {
            if (this.specType == INCOME){
                holder.bind(
                        spec_s.get(position).getId().toString(),
                        spec_s.get(position).getName(),
                        spec_s.get(position).getColor(),
                        true,
                        false
                );
            } else if (specType == OUTCOME){
                holder.bind(
                        spec_s.get(position).getId().toString(),
                        spec_s.get(position).getName(),
                        spec_s.get(position).getColor(),
                        false,
                        true
                );
            }
        }

        @Override
        public int getItemCount() {
            return spec_s.size();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    private void updateView(){
        incomeAdapter = new SpecificationRecyclerAdapter(mLap.getIncomeSpecifications(), INCOME);
        incomeRecyclerView.setAdapter(incomeAdapter);
        incomeRecyclerView.setLayoutManager(new LinearLayoutManager(SpecificationActivity.this));

        outcomeAdapter = new SpecificationRecyclerAdapter(mLap.getOutcomeSpecifications(), OUTCOME);
        outcomeRecyclerView.setAdapter(outcomeAdapter);
        outcomeRecyclerView.setLayoutManager(new LinearLayoutManager(SpecificationActivity.this));
    }
}
