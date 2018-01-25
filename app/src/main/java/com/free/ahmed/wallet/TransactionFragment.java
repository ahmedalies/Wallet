package com.free.ahmed.wallet;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.free.ahmed.wallet.Model.Image;
import com.free.ahmed.wallet.Model.Income;
import com.free.ahmed.wallet.Model.Outcome;
import com.free.ahmed.wallet.Model.PictureUtils;
import com.free.ahmed.wallet.Model.ResourcesLap;
import com.free.ahmed.wallet.Model.Specification;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ahmed on 10/10/17.
 */

public class TransactionFragment extends DialogFragment implements View.OnClickListener {

    private static final int INCOME_FOCUS = 0;
    private static final int OUTCOME_FOCUS = 1;
    private static final int PICK_IMAGE = 2;
    private static final int CAMERA_IMAGE = 5;
    private static final int REQUEST_PERMISSION_READ_EXTERNAL_DATA = 3;

    private Button incomeButton;
    private Button outcomeButton;
    private Button saveButton;
    private ImageView calenderImageView;
    private ImageView specificationImageView;
    private ImageView notesImageView;
    private ImageView cameraImageView;
    private ImageView dismissImageView;
    private LinearLayout linearLayout;
    private Spinner spinner;
    private EditText amountEditText;

    private List<Specification> spinnerList;
    private String[] spinnerArray;
    private ResourcesLap lap;
    private String imageDecode;
    private File imageFile;
    private String imagePath = null;
    private String notes = null;
    private TransactionCallback mCallback;

    private LinearLayout.LayoutParams paramsHorizontal;
    private LinearLayout.LayoutParams paramsVerticalImage;
    private LinearLayout.LayoutParams paramsVertical;

    private int focus = INCOME_FOCUS;

    public interface TransactionCallback{
        void updateResources();
    }


    public static TransactionFragment newInstance() {

        Bundle args = new Bundle();

        TransactionFragment fragment = new TransactionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_transaction, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        incomeButton = (Button) v.findViewById(R.id.bt_income);
        outcomeButton = (Button) v.findViewById(R.id.bt_outcome);
        saveButton = (Button) v.findViewById(R.id.saveButton);

        incomeButton.setOnClickListener(this);
        outcomeButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);


        calenderImageView = (ImageView) v.findViewById(R.id.image_calender);
        specificationImageView = (ImageView) v.findViewById(R.id.image_spec);
        notesImageView = (ImageView) v.findViewById(R.id.image_notes);
        cameraImageView = (ImageView) v.findViewById(R.id.image_camera);

        calenderImageView.setOnClickListener(this);
        specificationImageView.setOnClickListener(this);
        notesImageView.setOnClickListener(this);
        cameraImageView.setOnClickListener(this);

        linearLayout = (LinearLayout) v.findViewById(R.id.transaction_layout);

        spinner = new Spinner(getActivity());

        incomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.pressed_button));
        configureSpecification();

        dismissImageView = (ImageView) v.findViewById(R.id.dismiss);
        dismissImageView.setOnClickListener(this);

        amountEditText = (EditText) v.findViewById(R.id.money);

        paramsHorizontal = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f);

        paramsVerticalImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsVerticalImage.setMargins(5, 5, 5, 5);
        paramsVerticalImage.gravity = Gravity.CENTER;
        paramsVerticalImage.width = 500;
        paramsVerticalImage.height = 500;

        paramsVertical = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsVertical.setMargins(5, 5, 5, 5);

        return v;
    }

    @Override
    public void onClick(View v) {
        Calendar calendar = Calendar.getInstance();

        switch (v.getId()){
            case R.id.bt_income:
                incomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.pressed_button));
                outcomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_button));
                focus = INCOME_FOCUS;
                configureSpinner(focus);
                break;
            case R.id.bt_outcome:
                outcomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.pressed_button));
                incomeButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.circle_button));
                focus = OUTCOME_FOCUS;
                configureSpinner(focus);
                break;
            case R.id.image_calender:
                calenderImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_calender_yellow));
                cameraImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_camera));
                notesImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_comment));
                specificationImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_specification_grey));

                linearLayout.removeAllViews();
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setWeightSum(2.0f);
                Button dateButton = new Button(getActivity());
                dateButton.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                Button timeButton = new Button(getActivity());
                timeButton.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dateButton.setText(calendar.getTime().toString().substring(0, 10));
                timeButton.setText(calendar.getTime().toString().substring(10, 16));

                linearLayout.addView(dateButton, paramsHorizontal);
                linearLayout.addView(timeButton, paramsHorizontal);
                break;
            case R.id.image_spec:
                configureSpecification();
                break;
            case R.id.image_notes:
                notesImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_comment_yellow));
                cameraImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_camera));
                specificationImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_specification_grey));
                calenderImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_calender));

                linearLayout.removeAllViews();
                EditText editText = new EditText(getActivity());
                editText.setHint(R.string.notes_hint);
                editText.setMaxLines(1);
                editText.setMaxEms(30);
                editText.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.addView(editText, paramsVertical);

                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        notes = s.toString();
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                break;
            case R.id.image_camera:
                cameraImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_camera_yellwo));
                specificationImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_specification_grey));
                notesImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_comment));
                calenderImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_calender));

                linearLayout.removeAllViews();
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setWeightSum(2.0f);
                final Button cameraButton = new Button(getActivity());
                cameraButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.transparent_button));
                Button galleryButton = new Button(getActivity());
                galleryButton.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.transparent_button));
                cameraButton.setText(R.string.camera_hint);
                galleryButton.setText(R.string.gallery_hint);

                linearLayout.addView(cameraButton, paramsHorizontal);
                linearLayout.addView(galleryButton, paramsHorizontal);

                cameraButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Image image = new Image();
                        try {
                            imageFile = lap.getImageFile(image);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        Uri uri = FileProvider.getUriForFile(getActivity(), "com.free.ahmed.wallet.fileprovider", imageFile);
                        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        List<ResolveInfo> cameraActivities = getActivity().getPackageManager().
                                queryIntentActivities(captureImage, PackageManager.MATCH_DEFAULT_ONLY);

                        for (ResolveInfo info : cameraActivities) {
                            getActivity().grantUriPermission(info.activityInfo.toString(),
                                    uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        }

                        startActivityForResult(captureImage, CAMERA_IMAGE);
                    }
                });

                galleryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(isPermissionGranted(android.Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_READ_EXTERNAL_DATA)){
                            requestImage();
                        }
                    }
                });

                break;
            case R.id.saveButton:
                String allTime = calendar.getTime().toString().substring(0, 10) + calendar.getTime().toString().substring(10, 16);
                String name = (String) spinner.getSelectedItem();
                for (Specification specification:spinnerList) {
                    if (specification.getName().contentEquals(name)){
                        if(!(amountEditText.getText().toString().length() < 1)){
                            if(imagePath == null){
                                save(focus, Double.parseDouble(amountEditText.getText().toString()),
                                        notes, allTime, specification, null);
                            } else {
                                Image image = new Image();
                                image.setPath(imagePath);
                                save(focus, Double.parseDouble(amountEditText.getText().toString()),
                                        notes, allTime, specification, image);
                            }
                        } else {
                            Toast.makeText(getActivity(), R.string.save_error, Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
                break;
            case R.id.dismiss:
                dismiss();
                break;

        }
    }

    private void configureSpinner(int f){
        if(f == INCOME_FOCUS) {
            spinnerList = lap.getIncomeSpecifications();
        } else{
            spinnerList = lap.getOutcomeSpecifications();
        }

        spinnerArray = new String[spinnerList.size()];
        int c = 0;
        for (Specification specification : spinnerList) {
            String name = specification.getName();
            spinnerArray[c] = name;
            c++;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, spinnerArray);

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_spinner_item, spinnerArray);
        //adapter.setDropDownViewResource(R.layout.simple_spinner_drop_down_item);

        if (spinner.getAdapter() != null){
            spinner.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } else {
            spinner.setAdapter(adapter);
        }
    }

    private void configureSpecification(){
        specificationImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_summery_yellow));
        cameraImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_camera));
        notesImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_comment));
        calenderImageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_calender));

        linearLayout.removeAllViews();
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(spinner);

        lap = new ResourcesLap(getActivity());

        configureSpinner(focus);
    }

    private void save(int f, double amount, String notes, String time, Specification spec, Image image){
        if(f == INCOME_FOCUS){
            Income income = new Income();
            income.setAmount(amount);
            income.setName("temp");
            income.setCreated_at(time);
            income.setMonth(time.substring(4, 7));
            income.setYear(new Date().getYear());
            income.setSpecDetails(spec);
            income.setSpecId(spec.getId());

            if(image != null) {
                income.setImageId(image.getId());
                income.setImage(image);
                lap.addImage(image);
            }

            income.setFixed(false);
            income.setNotes(notes != null ? notes:"");
            lap.addIncome(income);
            mCallback.updateResources();
        } else if (f == OUTCOME_FOCUS){
            Outcome outcome = new Outcome();
            outcome.setAmount(amount);
            outcome.setName("temp");
            outcome.setCreated_at(time);
            outcome.setMonth(time.substring(4, 7));
            outcome.setYear(new Date().getYear());
            outcome.setSpecDetails(spec);
            outcome.setSpecId(spec.getId());

            if(image != null) {
                outcome.setImageId(image.getId());
                outcome.setImage(image);
                lap.addImage(image);
            }

            outcome.setFixed(false);
            outcome.setNotes(notes != null ? notes:"");
            lap.addOutcome(outcome);
            mCallback.updateResources();
        }
        Toast.makeText(getActivity(), R.string.saved, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
                Uri uri = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(uri, filePath, null, null, null);

                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePath[0]);
                imagePath = filePath[0];
                imageDecode = cursor.getString(columnIndex);
                showImage(BitmapFactory.decodeFile(imageDecode));
                cursor.close();

            } else if(requestCode ==  CAMERA_IMAGE && resultCode == RESULT_OK){
                Uri uri = FileProvider.getUriForFile(getActivity(), "com.free.ahmed.wallet.fileprovider", imageFile);
                getActivity().revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                Bitmap bitmap = PictureUtils.getScaledBitmap(imageFile.getPath(), getActivity());
                imagePath = imageFile.getPath();
                showImage(bitmap);
            }
        } catch (Exception e){
            Toast.makeText(getActivity(), "please try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void showImage(Bitmap bitmap){
        linearLayout.removeAllViews();
        ImageView imageView = new ImageView(getActivity());
        imageView.setImageBitmap(bitmap);

        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.addView(imageView, paramsVerticalImage);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_PERMISSION_READ_EXTERNAL_DATA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //requestImage();
                    break;
                } else{
                    Toast.makeText(getActivity(), R.string.image_permission_denied, Toast.LENGTH_SHORT).show();
                    break;
                }
        }
    }

    private void requestImage(){
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImageIntent, PICK_IMAGE);
    }

    private boolean isPermissionGranted(String permission, int requestCode){
        if(Build.VERSION.SDK_INT >= 23){
            if(getActivity().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED){
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
                return false;
            }
        } else {
            return true;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (TransactionCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }
}
