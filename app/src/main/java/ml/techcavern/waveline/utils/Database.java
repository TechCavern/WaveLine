package ml.techcavern.waveline.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jzhou on 1/15/2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    Database(Context context) {
        super(context, "WLDatabase", null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE CONFIG( property Text, value Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
