package wat.seth.dev.capstoneproject.db;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by seth-wat on 12/13/2017.
 */

public class QuakeContentProvider extends ContentProvider{
    public static final int QUAKE_TABLE_CODE = 1;
    Database db;

    public UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(Contract.AUTHORITY, Contract.QUAKE_TABLE, QUAKE_TABLE_CODE);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        db = new Database(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase mDb = db.getReadableDatabase();

        if (buildUriMatcher().match(uri) == QUAKE_TABLE_CODE) {
            Cursor c = mDb.query(Contract.QUAKE_TABLE, projection, selection, selectionArgs,
                    null, null, sortOrder);
            c.setNotificationUri(getContext().getContentResolver(), uri);
            return c;
        }

        throw new UnsupportedOperationException("Invalid URI: " + uri);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase mDb = db.getWritableDatabase();

        if (buildUriMatcher().match(uri) == QUAKE_TABLE_CODE) {
            long id = mDb.insert(Contract.QUAKE_TABLE, null, contentValues);
            if (id != -1) {
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(Contract.QuakeEntry.ACCESS_URI, id);
            }
            throw new SQLException("Row was not inserted :" + uri);
        }

        throw new UnsupportedOperationException("Invalid URI: " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase mDb = db.getWritableDatabase();

        if (buildUriMatcher().match(uri) == QUAKE_TABLE_CODE) {
            int deleted = mDb.delete(Contract.QUAKE_TABLE, selection, selectionArgs);
            getContext().getContentResolver().notifyChange(uri, null);
            return deleted;
        }

        throw new UnsupportedOperationException("Invalid URI: " + uri);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
