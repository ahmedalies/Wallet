package com.free.ahmed.wallet.Database;

/**
 * Created by ahmed on 10/14/2017.
 */

public class ImageDbSchema {

    public static final class Table {
        public static final String NAME = "image";
        public static final String SQL_CREATION = "create table " +
                ImageDbSchema.Table.NAME +
                "(" +
                "_id integer primary key autoincrement, " +
                ImageDbSchema.Cols.ID +
                ", " +
                ImageDbSchema.Cols.PATH +
                ")";
    }

    public static final class Cols {
        public static final String ID = "uuid";
        public static final String PATH = "path";
    }
}
