package com.free.ahmed.wallet.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.free.ahmed.wallet.Database.DatabaseCursorWrapper;
import com.free.ahmed.wallet.Database.DatabaseHelper;
import com.free.ahmed.wallet.Database.ImageDbSchema;
import com.free.ahmed.wallet.Database.IncomeDbSchema;
import com.free.ahmed.wallet.Database.IncomeSpecificationDbSchema;
import com.free.ahmed.wallet.Database.OutcomeDbSchema;
import com.free.ahmed.wallet.Database.OutcomeSpecificationDbSchema;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ahmed on 10/14/2017.
 */

public class ResourcesLap {

    private Context context;
    private SQLiteDatabase database;

    public ResourcesLap(Context context){
        this.context = context.getApplicationContext();
        this.database = new DatabaseHelper(this.context).getWritableDatabase();
    }

    private static ContentValues getContentValuesIncome(Income income){
        ContentValues values = new ContentValues();
        values.put(IncomeDbSchema.Cols.ID, income.getId().toString());
        values.put(IncomeDbSchema.Cols.NAME, income.getName());
        values.put(IncomeDbSchema.Cols.AMOUNT, income.getAmount());
        values.put(IncomeDbSchema.Cols.IS_FIXED, income.isFixed() ? 1 : 0);
        values.put(IncomeDbSchema.Cols.NOTES, income.getNotes());
        values.put(IncomeDbSchema.Cols.INCOME_SPECIFICATION_ID, income.getSpecDetails().getId().toString());
        if(income.getImage() != null)
            values.put(IncomeDbSchema.Cols.IMAGE_ID, income.getImage().getId().toString());
        values.put(IncomeDbSchema.Cols.CREATED_AT, income.getCreated_at());
        values.put(IncomeDbSchema.Cols.MONTH, income.getMonth());
        values.put(IncomeDbSchema.Cols.YEAR, income.getYear());

        return values;
    }

    private static ContentValues getContentValuesOutcome(Outcome outcome){
        ContentValues values = new ContentValues();
        values.put(OutcomeDbSchema.Cols.ID, outcome.getId().toString());
        values.put(OutcomeDbSchema.Cols.NAME, outcome.getName());
        values.put(OutcomeDbSchema.Cols.AMOUNT, outcome.getAmount());
        values.put(OutcomeDbSchema.Cols.IS_FIXED, outcome.isFixed() ? 1 : 0);
        values.put(OutcomeDbSchema.Cols.NOTES, outcome.getNotes());
        values.put(OutcomeDbSchema.Cols.OUTCOME_SPECIFICATION_ID, outcome.getSpecDetails().getId().toString());
        if(outcome.getImage() != null)
            values.put(OutcomeDbSchema.Cols.IMAGE_ID, outcome.getImage().getId().toString());
        values.put(OutcomeDbSchema.Cols.CREATED_AT, outcome.getCreated_at());
        values.put(OutcomeDbSchema.Cols.MONTH, outcome.getMonth());
        values.put(OutcomeDbSchema.Cols.YEAR, outcome.getYear());

        return values;
    }

    private static ContentValues getContentValuesIncomeSpecification(Specification specification){
        ContentValues values = new ContentValues();
        values.put(IncomeSpecificationDbSchema.Cols.ID, specification.getId().toString());
        values.put(IncomeSpecificationDbSchema.Cols.SPECIFICATION_NAME, specification.getName());
        values.put(IncomeSpecificationDbSchema.Cols.COLOR, specification.getColor());

        return values;
    }

    private static ContentValues getContentValuesOutcomeSpecification(Specification specification){
        ContentValues values = new ContentValues();
        values.put(OutcomeSpecificationDbSchema.Cols.ID, specification.getId().toString());
        values.put(OutcomeSpecificationDbSchema.Cols.SPECIFICATION_NAME, specification.getName());
        values.put(OutcomeSpecificationDbSchema.Cols.COLOR, specification.getColor());

        return values;
    }

    private static ContentValues getContentValuesImage(Image image){
        ContentValues values = new ContentValues();
        values.put(ImageDbSchema.Cols.ID, image.getId().toString());
        values.put(ImageDbSchema.Cols.PATH, image.getPath());

        return values;
    }

    public void addIncome(Income income){
        ContentValues values = getContentValuesIncome(income);
        this.database.insert(IncomeDbSchema.Table.NAME, null, values);

        if(income.getImage() != null)
            addImage(income.getImage());
    }

    public void addImage(Image Image){
        ContentValues values = getContentValuesImage(Image);
        this.database.insert(ImageDbSchema.Table.NAME, null, values);
    }

    public void addOutcome(Outcome outcome){
        ContentValues values = getContentValuesOutcome(outcome);
        this.database.insert(OutcomeDbSchema.Table.NAME, null, values);

        if (outcome.getImage() != null)
            addImage(outcome.getImage());
    }

    public void updateIncome(Income income){
        String uuid = income.getId().toString();
        ContentValues values = getContentValuesIncome(income);
        this.database.update(IncomeDbSchema.Table.NAME, values, IncomeDbSchema.Cols.ID + " = ?", new String[]{uuid});
    }

    public void updateOutcome(Outcome outcome){
        String uuid = outcome.getId().toString();
        ContentValues values = getContentValuesOutcome(outcome);
        this.database.update(OutcomeDbSchema.Table.NAME, values, OutcomeDbSchema.Cols.ID + " = ?", new String[]{uuid});
    }

    public void addIncomeSpecification(Specification specification){
        ContentValues values = getContentValuesIncomeSpecification(specification);
        this.database.insert(IncomeSpecificationDbSchema.Table.NAME, null, values);
    }

    public void addOutcomeSpecification(Specification specification){
        ContentValues values = getContentValuesOutcomeSpecification(specification);
        this.database.insert(OutcomeSpecificationDbSchema.Table.NAME, null, values);
    }

    public void updateIncomeSpecification(Specification specification){
        String uuid = specification.getId().toString();
        ContentValues values = getContentValuesIncomeSpecification(specification);
        this.database.update(
                IncomeSpecificationDbSchema.Table.NAME,
                values,
                IncomeSpecificationDbSchema.Cols.ID + " = ?",
                new String[]{uuid}
        );
    }

    public void updateOutcomeSpecification(Specification specification){
        String uuid = specification.getId().toString();
        ContentValues values = getContentValuesOutcomeSpecification(specification);
        this.database.update(
                OutcomeSpecificationDbSchema.Table.NAME,
                values,
                OutcomeSpecificationDbSchema.Cols.ID + " = ?",
                new String[]{uuid}
        );
    }

    public void deleteResource(Resources resources){
        String id = resources.getId().toString();

        if (resources.isIncome()){
            this.database.delete(IncomeDbSchema.Table.NAME, IncomeDbSchema.Cols.ID + " = ?", new String[]{id});
        } else {
            this.database.delete(OutcomeDbSchema.Table.NAME, OutcomeDbSchema.Cols.ID + " = ?", new String[]{id});
        }

        if(resources.getImage() != null){
            deleteImage(resources.getImage());
        }
    }

    public void deleteImage(Image image){
        String id = image.getId().toString();
        this.database.delete(ImageDbSchema.Table.NAME, ImageDbSchema.Cols.ID + " = ?", new String[]{id});
    }

    private DatabaseCursorWrapper query(String tableName, String whereClause, String[] whereArgs){
        Cursor cursor = this.database.query(tableName, null, whereClause, whereArgs, null, null, null);
        return new DatabaseCursorWrapper(cursor);
    }

    public List<Resources> getIncomes(String month){
        List<Resources> incomes = new ArrayList<>();
        DatabaseCursorWrapper cursorWrapper = query(
                IncomeDbSchema.Table.NAME,
                IncomeDbSchema.Cols.MONTH + " = ?",
                new String[]{month});

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                incomes.add(cursorWrapper.getIncome());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        int c = 0;
        for (Resources income : incomes) {
            if (income.getImageId() != null){
                cursorWrapper = query(
                        ImageDbSchema.Table.NAME,
                        ImageDbSchema.Cols.ID + " = ?",
                        new String[]{income.getImageId().toString()}
                );
                try {
                    cursorWrapper.moveToFirst();
                    income.setImage(cursorWrapper.getImage());
                    incomes.set(c, income);
                    c++;
                } finally {
                    cursorWrapper.close();
                }
            }
        }
        return incomes;
    }

    public List<Resources> getOutcomes(String month){
        List<Resources> outcomes = new ArrayList<>();
        DatabaseCursorWrapper cursorWrapper = query(
                OutcomeDbSchema.Table.NAME,
                IncomeDbSchema.Cols.MONTH + " = ?",
                new String[]{month});

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                outcomes.add(cursorWrapper.getOutcome());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        int c = 0;
        for (Resources outcome : outcomes) {
            if (outcome.getImageId() != null){
                cursorWrapper = query(
                        ImageDbSchema.Table.NAME,
                        ImageDbSchema.Cols.ID + " = ?",
                        new String[]{outcome.getImageId().toString()}
                );
                try {
                    cursorWrapper.moveToFirst();
                    outcome.setImage(cursorWrapper.getImage());
                    outcomes.set(c, outcome);
                    c++;
                } finally {
                    cursorWrapper.close();
                }
            }
        }

        return outcomes;
    }

    public List<Specification> getIncomeSpecifications(){
        List<Specification> specifications = new ArrayList<>();
        DatabaseCursorWrapper cursorWrapper = query(IncomeSpecificationDbSchema.Table.NAME, null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                specifications.add(cursorWrapper.getIncomeSpecification());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return specifications;
    }

    public List<Specification> getOutcomeSpecifications(){
        List<Specification> specifications = new ArrayList<>();
        DatabaseCursorWrapper cursorWrapper = query(OutcomeSpecificationDbSchema.Table.NAME, null, null);

        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()){
                specifications.add(cursorWrapper.getOutcomeSpecification());
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }

        return specifications;
    }

    public Specification getIncomeSpecifications(UUID id){
        Specification specifications = new Specification(id);
        DatabaseCursorWrapper cursorWrapper = query(IncomeSpecificationDbSchema.Table.NAME,
                IncomeSpecificationDbSchema.Cols.ID + " = ?",
                new String[]{id.toString()});

        try {
            if (cursorWrapper.getCount() == 0)
                return null;

            cursorWrapper.moveToFirst();
            specifications = cursorWrapper.getIncomeSpecification();
        } finally {
            cursorWrapper.close();
        }

        return specifications;
    }

    public Specification getOutcomeSpecifications(UUID id){
        Specification specifications = new Specification(id);
        DatabaseCursorWrapper cursorWrapper = query(OutcomeSpecificationDbSchema.Table.NAME,
                OutcomeSpecificationDbSchema.Cols.ID + " = ?",
                new String[]{id.toString()});

        try {
            if (cursorWrapper.getCount() == 0)
                return null;

            cursorWrapper.moveToFirst();
            specifications = cursorWrapper.getOutcomeSpecification();
        } finally {
            cursorWrapper.close();
        }

        return specifications;
    }

    public File getImageFile(Image image) throws IOException {
        File filesDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(image.getImageFileName(), ".jpg", filesDir);
    }
}
