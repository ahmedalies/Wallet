package com.free.ahmed.wallet.Database;

/**
 * Created by ahmed on 10/14/2017.
 */

public class IncomeSpecificationDbSchema {

    public static final class Table {
        public static final String NAME = "income_specification";
        public static final String SQL_CREATION = "create table " +
                IncomeSpecificationDbSchema.Table.NAME +
                "(" +
                "_id integer primary key autoincrement, " +
                IncomeSpecificationDbSchema.Cols.ID +
                ", " +
                IncomeSpecificationDbSchema.Cols.SPECIFICATION_NAME +
                ", " +
                IncomeSpecificationDbSchema.Cols.COLOR +
                ")";
    }

    public static final class Cols {
        public static final String ID = "uuid";
        public static final String SPECIFICATION_NAME = "spec_name";
        public static final String COLOR = "color";
    }
}
