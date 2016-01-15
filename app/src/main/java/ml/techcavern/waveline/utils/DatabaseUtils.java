package ml.techcavern.waveline.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by jzhou on 1/15/2016.
 */
public class DatabaseUtils {
    public static String getConfig(Context context, String property) {
        SQLiteDatabase db = new Database(context).getReadableDatabase();
        Cursor cursor = db.query("CONFIG", new String[]{"property", "value"}, "property" + "=?",
                new String[]{property}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        return cursor.getString(1);
    }

    public static void addConfig(Context context, String property, String value) {
        SQLiteDatabase db = new Database(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("property", property);
        values.put("value", value);
        db.insert("CONFIG", null, values);
        db.close();
    }

    public static void updateConfig(Context context, String property, String value) {
        SQLiteDatabase db = new Database(context).getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("property", property);
        values.put("value", value);
        db.update("CONFIG", values, "property" + " = ?",
                new String[]{String.valueOf(property)});
    }

    public static void deleteConfig(Context context, String property) {
        SQLiteDatabase db = new Database(context).getWritableDatabase();
        db.delete("CONFIG", "property" + " = ?",
                new String[]{property});
        db.close();
    }

}
