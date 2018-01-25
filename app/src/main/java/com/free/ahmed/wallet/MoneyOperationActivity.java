package com.free.ahmed.wallet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.free.ahmed.wallet.Model.PictureUtils;
import com.free.ahmed.wallet.Model.Resources;

import java.io.Serializable;

public class MoneyOperationActivity extends AppCompatActivity {

    private static final String RESOURCE_DETAILS = "com.free.ahmed.wallet.MoneyFragment.resource_details";
    private static final String BUNDLE_EXTRA = "com.free.ahmed.wallet.MoneyFragment.bundle_extra";

    private TextView nameTextView;
    private TextView dateTextView;
    private TextView amountTextView;
    private TextView notesTextView;
    private ImageView colorImageView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_money);

        nameTextView = (TextView) findViewById(R.id.operation_money_name_text_view);
        dateTextView = (TextView) findViewById(R.id.operation_money_date_text_view);
        amountTextView = (TextView) findViewById(R.id.operation_money_amount_text_view);
        notesTextView = (TextView) findViewById(R.id.operation_money_notes_text_view);
        colorImageView = (ImageView) findViewById(R.id.operation_money_color_image_view);
        imageView = (ImageView) findViewById(R.id.operation_money_image_view);

        Bundle bundle = getIntent().getBundleExtra(BUNDLE_EXTRA);
        Resources resources = (Resources) bundle.getSerializable(RESOURCE_DETAILS);

        configureUi(resources);
    }

    public static Intent newIntent(Context context, Resources resources){
        Intent intent = new Intent(context, MoneyOperationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(RESOURCE_DETAILS, resources);
        intent.putExtra(BUNDLE_EXTRA, bundle);

        return intent;
    }

    private void configureUi(Resources resources){
        nameTextView.setText(resources.getSpecDetails().getName());
        dateTextView.setText(resources.getCreated_at());

        if (resources.isIncome()) {
            amountTextView.setText("+" + resources.getAmount());
            amountTextView.setTextColor(getResources().getColor(R.color.green));
        } else {
            amountTextView.setText("-" + resources.getAmount());
            amountTextView.setTextColor(Color.RED);
        }

        if (resources.getNotes() != null){
            notesTextView.setText(resources.getNotes());
        }

        if (resources.getImage() != null){
            Bitmap bitmap = PictureUtils.getScaledBitmap(resources.getImage().getPath(), this);
            imageView.setImageBitmap(bitmap);
        }

        colorImageView.setBackgroundDrawable(new ColorDrawable(resources.getSpecDetails().getColor()));
    }
}
