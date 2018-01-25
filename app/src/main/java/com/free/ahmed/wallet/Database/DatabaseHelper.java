package com.free.ahmed.wallet.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;

import com.free.ahmed.wallet.Model.Consts;
import com.free.ahmed.wallet.Model.Income;
import com.free.ahmed.wallet.Model.ResourcesLap;
import com.free.ahmed.wallet.Model.Specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 10/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "walletFLosBase.db";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ExchangeIncomeDbSchema.Table.SQL_CREATION);
        db.execSQL(ExchangeOutcomeDbSchema.Table.SQL_CREATION);
        db.execSQL(ImageDbSchema.Table.SQL_CREATION);
        db.execSQL(IncomeDbSchema.Table.SQL_CREATION);
        db.execSQL(IncomeSpecificationDbSchema.Table.SQL_CREATION);
        db.execSQL(OutcomeDbSchema.Table.SQL_CREATION);
        db.execSQL(OutcomeSpecificationDbSchema.Table.SQL_CREATION);

        for (int i = 0; i < Consts.mIncomeSpecificationColors.length; i++) {
            ContentValues values = new ContentValues();
            Specification specification = new Specification();
            specification.setColor(Consts.mIncomeSpecificationColors[i]);
            specification.setName(Consts.mIncomeSpecificationNames[i]);
            values.put(IncomeSpecificationDbSchema.Cols.COLOR, specification.getColor());
            values.put(IncomeSpecificationDbSchema.Cols.SPECIFICATION_NAME, specification.getName());
            values.put(IncomeSpecificationDbSchema.Cols.ID, specification.getId().toString());
            db.insert(IncomeSpecificationDbSchema.Table.NAME, null, values);
        }

        for (int i = 0; i < Consts.mOutcomeSpecificationColors.length; i++) {
            ContentValues values = new ContentValues();
            Specification specification = new Specification();
            specification.setColor(Consts.mOutcomeSpecificationColors[i]);
            specification.setName(Consts.mOutcomeSpecificationNames[i]);
            values.put(OutcomeSpecificationDbSchema.Cols.COLOR, specification.getColor());
            values.put(OutcomeSpecificationDbSchema.Cols.SPECIFICATION_NAME, specification.getName());
            values.put(OutcomeSpecificationDbSchema.Cols.ID, specification.getId().toString());
            db.insert(OutcomeSpecificationDbSchema.Table.NAME, null, values);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
