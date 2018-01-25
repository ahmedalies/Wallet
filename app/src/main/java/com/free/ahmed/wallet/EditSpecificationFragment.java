package com.free.ahmed.wallet;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.free.ahmed.wallet.Model.Outcome;
import com.free.ahmed.wallet.Model.ResourcesLap;
import com.free.ahmed.wallet.Model.Specification;

import java.util.List;
import java.util.UUID;

import yuku.ambilwarna.AmbilWarnaDialog;

/**
 * Created by ahmed on 10/24/2017.
 */

public class EditSpecificationFragment extends DialogFragment implements View.OnClickListener{

    private static final String ID = "com.free.ahmed.wallet.EditSpecificationFragment.id";
    private static final String COLOR = "com.free.ahmed.wallet.EditSpecificationFragment.color";
    private static final String NAME = "com.free.ahmed.wallet.EditSpecificationFragment.name";
    private static final String IS_INCOME = "com.free.ahmed.wallet.EditSpecificationFragment.income";
    private static final String IS_OUTCOME = "com.free.ahmed.wallet.EditSpecificationFragment.outcome";

    private ImageView colorImageView;
    private EditText nameEditText;
    private RadioButton incomeRadioButton;
    private RadioButton outcomeRadioButton;
    private Button saveButton;
    private Button cancelButton;
    private EditSpecificationCallback mCallback;

    private int defaultColor;
    private ResourcesLap mLap;

    public interface EditSpecificationCallback{
        void updateResources();
    }

    public static EditSpecificationFragment newInstance(String uuid, int color, String name, boolean income, boolean outcome) {

        Bundle args = new Bundle();
        args.putString(ID, uuid);
        args.putInt(COLOR, color);
        args.putString(NAME, name);
        args.putBoolean(IS_INCOME, income);
        args.putBoolean(IS_OUTCOME, outcome);

        EditSpecificationFragment fragment = new EditSpecificationFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_new_specification, container, false);

        colorImageView = (ImageView) v.findViewById(R.id.pick_color);
        colorImageView.setImageDrawable(new ColorDrawable(getArguments().getInt(COLOR)));
        defaultColor = getArguments().getInt(COLOR);
        colorImageView.setOnClickListener(this);

        nameEditText = (EditText) v.findViewById(R.id.spec_name_edit_view);
        nameEditText.setText(getArguments().getString(NAME));

        incomeRadioButton = (RadioButton) v.findViewById(R.id.spec_income_radio_button);
        incomeRadioButton.setChecked(getArguments().getBoolean(IS_INCOME));

        outcomeRadioButton = (RadioButton) v.findViewById(R.id.spec_outcome_radio_button);
        outcomeRadioButton.setChecked(getArguments().getBoolean(IS_OUTCOME));

        saveButton = (Button) v.findViewById(R.id.spec_save_button);
        saveButton.setOnClickListener(this);

        cancelButton = (Button) v.findViewById(R.id.spec_cancel_button);
        cancelButton.setOnClickListener(this);


        return v;
    }

    private void openColorPickerDialog(boolean alphaSupport){
        AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
                defaultColor,
                alphaSupport,
                new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
               colorImageView.setImageDrawable(new ColorDrawable(color));
                defaultColor = color;
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.pick_color:
                openColorPickerDialog(false);
                break;
            case R.id.spec_save_button:
                save();
                break;
            case R.id.spec_cancel_button:
                dismiss();
                break;
        }
    }

    private void save(){
        if (mLap == null){
            mLap = new ResourcesLap(getActivity());
        }

        Specification specification = new Specification(UUID.fromString(getArguments().getString(ID)));
        specification.setColor(defaultColor);
        specification.setName(nameEditText.getText().toString());

        if(getArguments().getBoolean(IS_INCOME)){
            mLap.updateIncomeSpecification(specification);
        } else if(getArguments().getBoolean(IS_OUTCOME)){
            mLap.updateOutcomeSpecification(specification);
        }


        mCallback.updateResources();
        dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (EditSpecificationCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}


