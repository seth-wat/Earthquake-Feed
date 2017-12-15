package wat.seth.dev.capstoneproject.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by seth-wat on 12/13/2017.
 */

public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "saved.db";
    private static final int VERSION = 1;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createQuakes = "CREATE TABLE " + Contract.QUAKE_TABLE + " (" +
                Contract.QuakeEntry._ID + " INTEGER PRIMARY KEY, " +
                Contract.QuakeEntry.COLUMN_QUAKE_ID + " TEXT NOT NULL, " +
                Contract.QuakeEntry.COLUMN_TIME_ZONE + " INTEGER NOT NULL, " +
                Contract.QuakeEntry.COLUMN_MAG + " REAL NOT NULL, " +
                Contract.QuakeEntry.COLUMN_PLACE + " TEXT NOT NULL, " +
                Contract.QuakeEntry.COLUMN_TIME + " INTEGER NOT NULL, " +
                Contract.QuakeEntry.COLUMN_FELT + " INTEGER NOT NULL, " +
                Contract.QuakeEntry.COLUMN_MMI + " REAL NOT NULL, " +
                Contract.QuakeEntry.COLUMN_LONG + " REAL NOT NULL, " +
                Contract.QuakeEntry.COLUMN_LAT + " REAL NOT NULL, " +
                Contract.QuakeEntry.COLUMN_DEPTH + " REAL NOT NULL);";

        sqLiteDatabase.execSQL(createQuakes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.QUAKE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public static final Cursor fetchQuakes(Context context) {
        return context.getContentResolver().query(
                Contract.QuakeEntry.ACCESS_URI, null, null, null, null);
    }
}
