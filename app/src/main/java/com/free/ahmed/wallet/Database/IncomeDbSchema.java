package com.free.ahmed.wallet.Database;

/**
 * Created by ahmed on 10/14/2017.
 */

public class IncomeDbSchema {

    public static final class Table {
        public static final String NAME = "income";
        public static final String SQL_CREATION = "create table " +
                IncomeDbSchema.Table.NAME +
                "(" +
                "_id integer primary key autoincrement, " +
                IncomeDbSchema.Cols.ID +
                ", " +
                IncomeDbSchema.Cols.AMOUNT +
                ", " +
                IncomeDbSchema.Cols.INCOME_SPECIFICATION_ID +
                ", " +
                IncomeDbSchema.Cols.NAME +
                ", " +
                IncomeDbSchema.Cols.NOTES +
                ", " +
                IncomeDbSchema.Cols.IS_FIXED +
                ", " +
                IncomeDbSchema.Cols.IMAGE_ID +
                ", " +
                IncomeDbSchema.Cols.CREATED_AT +
                ", " +
                IncomeDbSchema.Cols.MONTH +
                ", " +
                IncomeDbSchema.Cols.YEAR +
                ")";
    }

    public static final class Cols {
        public static final String ID = "uuid";
        public static final String NAME = "name";
        public static final String AMOUNT = "amount";
        public static final String INCOME_SPECIFICATION_ID = "in_spec_id";
        public static final String NOTES = "note";
        public static final String IMAGE_ID = "image_id";
        public static final String IS_FIXED = "is_fixed";
        public static final String CREATED_AT = "created_at";
        public static final String MONTH = "month";
        public static final String YEAR = "year";
    }
}
