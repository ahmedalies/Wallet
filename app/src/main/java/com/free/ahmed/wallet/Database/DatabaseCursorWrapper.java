package com.free.ahmed.wallet.Database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.free.ahmed.wallet.Model.Image;
import com.free.ahmed.wallet.Model.Income;
import com.free.ahmed.wallet.Model.Outcome;
import com.free.ahmed.wallet.Model.Resources;
import com.free.ahmed.wallet.Model.Specification;

import java.util.UUID;

/**
 * Created by ahmed on 10/16/2017.
 */

public class DatabaseCursorWrapper extends CursorWrapper {

    public DatabaseCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Resources getIncome(){
        String uuid = getString(getColumnIndex(IncomeDbSchema.Cols.ID));
        double amount = getDouble(getColumnIndex(IncomeDbSchema.Cols.AMOUNT));
        String name = getString(getColumnIndex(IncomeDbSchema.Cols.NAME));
        String notes = getString(getColumnIndex(IncomeDbSchema.Cols.NOTES));
        int isFixed = getInt(getColumnIndex(IncomeDbSchema.Cols.IS_FIXED));
        String createdAt = getString(getColumnIndex(IncomeDbSchema.Cols.CREATED_AT));
        String imageId = getString(getColumnIndex(IncomeDbSchema.Cols.IMAGE_ID));
        String specId = getString(getColumnIndex(IncomeDbSchema.Cols.INCOME_SPECIFICATION_ID));
        String month = getString(getColumnIndex(IncomeDbSchema.Cols.MONTH));
        int year = getInt(getColumnIndex(IncomeDbSchema.Cols.YEAR));

        Resources resources = new Resources(UUID.fromString(uuid));
        resources.setName(name);
        resources.setAmount(amount);
        resources.setNotes(notes);
        resources.setFixed(isFixed != 0);
        resources.setCreated_at(createdAt);
        if(imageId != null)
            resources.setImageId(UUID.fromString(imageId));
        resources.setSpecId(UUID.fromString(specId));
        resources.setMonth(month);
        resources.setYear(year);
        return resources;
    }

    public Resources getOutcome(){
        String uuid = getString(getColumnIndex(OutcomeDbSchema.Cols.ID));
        double amount = getDouble(getColumnIndex(OutcomeDbSchema.Cols.AMOUNT));
        String name = getString(getColumnIndex(OutcomeDbSchema.Cols.NAME));
        String notes = getString(getColumnIndex(OutcomeDbSchema.Cols.NOTES));
        int isFixed = getInt(getColumnIndex(OutcomeDbSchema.Cols.IS_FIXED));
        String createdAt = getString(getColumnIndex(OutcomeDbSchema.Cols.CREATED_AT));
        String imageId = getString(getColumnIndex(OutcomeDbSchema.Cols.IMAGE_ID));
        String specId = getString(getColumnIndex(OutcomeDbSchema.Cols.OUTCOME_SPECIFICATION_ID));
        String month = getString(getColumnIndex(OutcomeDbSchema.Cols.MONTH));
        int year = getInt(getColumnIndex(OutcomeDbSchema.Cols.YEAR));

        Resources resources = new Resources(UUID.fromString(uuid));
        resources.setName(name);
        resources.setAmount(amount);
        resources.setNotes(notes);
        resources.setFixed(isFixed != 0);
        resources.setCreated_at(createdAt);
        if (imageId != null)
            resources.setImageId(UUID.fromString(imageId));
        resources.setSpecId(UUID.fromString(specId));
        resources.setMonth(month);
        resources.setYear(year);
        return resources;
    }

    public Specification getIncomeSpecification(){
        String uuid = getString(getColumnIndex(IncomeSpecificationDbSchema.Cols.ID));
        String name = getString(getColumnIndex(IncomeSpecificationDbSchema.Cols.SPECIFICATION_NAME));
        int color = getInt(getColumnIndex(IncomeSpecificationDbSchema.Cols.COLOR));

        Specification specifications = new Specification(UUID.fromString(uuid));
        specifications.setName(name);
        specifications.setColor(color);

        return specifications;
    }

    public Specification getOutcomeSpecification(){
        String uuid = getString(getColumnIndex(OutcomeSpecificationDbSchema.Cols.ID));
        String name = getString(getColumnIndex(OutcomeSpecificationDbSchema.Cols.SPECIFICATION_NAME));
        int color = getInt(getColumnIndex(OutcomeSpecificationDbSchema.Cols.COLOR));

        Specification specifications = new Specification(UUID.fromString(uuid));
        specifications.setName(name);
        specifications.setColor(color);

        return specifications;
    }

    public Image getImage(){
        String id = getString(getColumnIndex(ImageDbSchema.Cols.ID));
        String path = getString(getColumnIndex(ImageDbSchema.Cols.PATH));

        Image image = new Image(UUID.fromString(id));
        image.setPath(path);

        return image;
    }
}
