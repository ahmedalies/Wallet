package com.free.ahmed.wallet.Database;

/**
 * Created by ahmed on 10/14/2017.
 */

public class ExchangeIncomeDbSchema {

    public static final class Table {
        public static final String NAME = "exchange_income";
        public static final String SQL_CREATION = "create table " +
                ExchangeIncomeDbSchema.Table.NAME +
                "(" +
                "_id integer primary key autoincrement, " +
                ExchangeIncomeDbSchema.Cols.ID +
                ", " +
                ExchangeIncomeDbSchema.Cols.AMOUNT +
                ", " +
                ExchangeIncomeDbSchema.Cols.FROM_INCOME_ID+
                ", " +
                ExchangeIncomeDbSchema.Cols.TO_INCOME_ID+
                ", " +
                ExchangeIncomeDbSchema.Cols.NOTES +
                ", " +
                ExchangeIncomeDbSchema.Cols.IMAGE_ID +
                ", " +
                ExchangeIncomeDbSchema.Cols.CREATED_AT +
                ")";
    }

    public static final class Cols {
        public static final String ID = "uuid";
        public static final String FROM_INCOME_ID = "from_in_id";
        public static final String TO_INCOME_ID = "to_in_id";
        public static final String AMOUNT = "amount";
        public static final String NOTES = "note";
        public static final String IMAGE_ID = "image_id";
        public static final String CREATED_AT = "created_at";
    }
}
