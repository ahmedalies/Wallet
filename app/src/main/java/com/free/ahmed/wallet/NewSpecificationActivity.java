package com.free.ahmed.wallet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.free.ahmed.wallet.Model.ResourcesLap;
import com.free.ahmed.wallet.Model.Specification;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NewSpecificationActivity extends AppCompatActivity implements View.OnClickListener{

    private RadioGroup radioGroup;
    private RadioButton incomeRadioButton;
    private RadioButton outcomeRadioButton;
    private ImageView imageView;
    private EditText editText;
    private Button saveButton;
    private Button cancelButton;

    private Specification mSpecification;
    private ResourcesLap mLap;

    private int defaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_specification);

        radioGroup = (RadioGroup) findViewById(R.id.spec_radio_group);
        incomeRadioButton = (RadioButton) findViewById(R.id.spec_income_radio_button);
        outcomeRadioButton = (RadioButton) findViewById(R.id.spec_outcome_radio_button);
        imageView = (ImageView) findViewById(R.id.pick_color);
        saveButton = (Button) findViewById(R.id.spec_save_button);
        cancelButton = (Button) findViewById(R.id.spec_cancel_button);
        editText = (EditText) findViewById(R.id.spec_name_edit_view);

        defaultColor = ContextCompat.getColor(NewSpecificationActivity.this, R.color.colorAccent);

        imageView.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pick_color:
                openColorPickerDialog(false);
                break;
            case R.id.spec_save_button:
                if((incomeRadioButton.isChecked() || outcomeRadioButton.isChecked())
                        && mSpecification != null && mLap != null && editText.getText().length() > 0){
                    if(incomeRadioButton.isChecked()){
                        mSpecification.setName(editText.getText().toString());
                        mLap.addIncomeSpecification(mSpecification);
                    } else if(outcomeRadioButton.isChecked()){
                        mSpecification.setName(editText.getText().toString());
                        mLap.addOutcomeSpecification(mSpecification);
                    }
                    clean();
                    Toast.makeText(NewSpecificationActivity.this, "done", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewSpecificationActivity.this, "complete all fields", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.spec_cancel_button:
                clean();
                break;
        }
    }

    private void openColorPickerDialog(boolean alphaSupport){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(NewSpecificationActivity.this, defaultColor, alphaSupport, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                defaultColor = color;
                imageView.setImageDrawable(new ColorDrawable(color));
                mLap = new ResourcesLap(NewSpecificationActivity.this);
                mSpecification = new Specification();
                mSpecification.setColor(color);
            }
        });
        dialog.show();
    }

    private void clean(){
        imageView.setImageDrawable(new ColorDrawable(Color.BLACK));
        editText.setText(null);
        incomeRadioButton.setChecked(false);
        outcomeRadioButton.setChecked(false);
    }

    public static Intent newIntent(Context context){
        Intent intent = new Intent(context, NewSpecificationActivity.class);
        return intent;
    }
}
