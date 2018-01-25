package com.free.ahmed.wallet.Database;

/**
 * Created by ahmed on 10/14/2017.
 */

public class OutcomeDbSchema {

    public static final class Table {
        public static final String NAME = "outcome";

        public static final String SQL_CREATION = "create table " +
                OutcomeDbSchema.Table.NAME +
                "(" +
                "_id integer primary key autoincrement, " +
                OutcomeDbSchema.Cols.ID +
                ", " +
                OutcomeDbSchema.Cols.AMOUNT +
                ", " +
                OutcomeDbSchema.Cols.OUTCOME_SPECIFICATION_ID +
                ", " +
                OutcomeDbSchema.Cols.NAME +
                ", " +
                OutcomeDbSchema.Cols.NOTES +
                ", " +
                OutcomeDbSchema.Cols.IS_FIXED +
                ", " +
                OutcomeDbSchema.Cols.IMAGE_ID +
                ", " +
                OutcomeDbSchema.Cols.CREATED_AT +
                ", " +
                OutcomeDbSchema.Cols.MONTH +
                ", " +
                OutcomeDbSchema.Cols.YEAR +
                ")";
    }

    public static final class Cols {
        public static final String ID = "uuid";
        public static final String NAME = "name";
        public static final String AMOUNT = "amount";
        public static final String OUTCOME_SPECIFICATION_ID = "out_spec_id";
        public static final String NOTES = "note";
        public static final String IMAGE_ID = "image_id";
        public static final String IS_FIXED = "is_fixed";
        public static final String CREATED_AT = "created_at";
        public static final String MONTH = "month";
        public static final String YEAR = "year";
    }
}
