package com.free.ahmed.wallet;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class DateActivity extends AppCompatActivity {

    public static final String DATE_KEY = "com.free.ahmed.wallet.DateActivity.date_view_month";

    private static final String[] dates = {
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
    };

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        mRecyclerView = (RecyclerView) findViewById(R.id.pick_date_rec_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new DateRecyclerAdapter());
    }

    private class DateViewHolder extends RecyclerView.ViewHolder{

        private TextView dateTextView;

        public DateViewHolder(View itemView) {
            super(itemView);

            dateTextView = (TextView) itemView.findViewById(R.id.date_text_view);

            dateTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra(DATE_KEY, dateTextView.getText());
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }

        public void bind(String date){
            dateTextView.setText(date);
        }
    }

    private class DateRecyclerAdapter extends RecyclerView.Adapter<DateViewHolder>{

        @Override
        public DateViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(DateActivity.this);
            View v = inflater.inflate(R.layout.date_card, parent, false);
            return new DateViewHolder(v);
        }

        @Override
        public void onBindViewHolder(DateViewHolder holder, int position) {
            holder.bind(dates[position]);
        }

        @Override
        public int getItemCount() {
            return dates.length;
        }
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, DateActivity.class);
        return intent;
    }
}
