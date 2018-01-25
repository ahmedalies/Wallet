package com.free.ahmed.wallet.Database;

/**
 * Created by ahmed on 10/14/2017.
 */

public class OutcomeSpecificationDbSchema {

    public static final class Table {
        public static final String NAME = "outcome_specification";
        public static final String SQL_CREATION = "create table " +
                OutcomeSpecificationDbSchema.Table.NAME +
                "(" +
                "_id integer primary key autoincrement, " +
                OutcomeSpecificationDbSchema.Cols.ID +
                ", " +
                OutcomeSpecificationDbSchema.Cols.SPECIFICATION_NAME +
                ", " +
                OutcomeSpecificationDbSchema.Cols.COLOR +
                ")";
    }

    public static final class Cols {
        public static final String ID = "uuid";
        public static final String SPECIFICATION_NAME = "spec_name";
        public static final String COLOR = "color";
    }
}
