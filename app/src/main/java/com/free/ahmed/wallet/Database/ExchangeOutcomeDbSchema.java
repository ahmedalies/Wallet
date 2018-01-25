package com.free.ahmed.wallet.Database;

/**
 * Created by ahmed on 10/14/2017.
 */

public class ExchangeOutcomeDbSchema {

    public static final class Table {
        public static final String NAME = "exchange_outcome";
        public static final String SQL_CREATION = "create table " +
                ExchangeOutcomeDbSchema.Table.NAME +
                "(" +
                "_id integer primary key autoincrement, " +
                ExchangeOutcomeDbSchema.Cols.ID +
                ", " +
                ExchangeOutcomeDbSchema.Cols.AMOUNT +
                ", " +
                ExchangeOutcomeDbSchema.Cols.FROM_OUTCOME_ID +
                ", " +
                ExchangeOutcomeDbSchema.Cols.TO_OUTCOME_ID +
                ", " +
                ExchangeOutcomeDbSchema.Cols.NOTES +
                ", " +
                ExchangeOutcomeDbSchema.Cols.IMAGE_ID +
                ", " +
                ExchangeOutcomeDbSchema.Cols.CREATED_AT +
                ")";
    }

    public static final class Cols {
        public static final String ID = "uuid";
        public static final String FROM_OUTCOME_ID = "from_out_id";
        public static final String TO_OUTCOME_ID = "to_out_id";
        public static final String AMOUNT = "amount";
        public static final String NOTES = "note";
        public static final String IMAGE_ID = "image_id";
        public static final String CREATED_AT = "created_at";
    }
}
