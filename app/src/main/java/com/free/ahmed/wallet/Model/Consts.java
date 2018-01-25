package com.free.ahmed.wallet.Model;

import android.graphics.Color;

/**
 * Created by ahmed on 10/21/2017.
 */

public class Consts {

    public static String month;
    public final static int MONEY_FRAGMENT_TYPE = 1;
    public final static int SUMMERY_FRAGMENT_TYPE = 2;
    public final static int SETTING_FRAGMENT_TYPE = 3;

    public static final int[] mOutcomeSpecificationColors = {
            Color.MAGENTA,
            Color.BLACK,
            Color.BLUE,
            Color.CYAN,
            Color.GRAY,
            Color.RED,
            Color.YELLOW
    };

    public static final int[] mIncomeSpecificationColors = {
            Color.BLUE,
            Color.CYAN,
            Color.GRAY
    };

    public static final String[] mOutcomeSpecificationNames = {
            "food and drinks",
            "entertainment",
            "clothes",
            "transport",
            "health",
            "bills",
            "another"
    };

    public static final String[] mIncomeSpecificationNames = {
            "salary",
            "free lancing",
            "another"
    };
}
