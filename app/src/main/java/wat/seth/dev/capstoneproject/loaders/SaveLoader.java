package wat.seth.dev.capstoneproject.loaders;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import wat.seth.dev.capstoneproject.data.Earthquake;
import wat.seth.dev.capstoneproject.db.Contract;
import wat.seth.dev.capstoneproject.db.Database;

/**
 * Created by seth-wat on 12/21/2017.
 */

public class SaveLoader extends AsyncTaskLoader {
    /*
    Check if the earthquake exists in db,
    Save earthquake in db,
    delete earthquake from db
     */
    public static final int CHECK = 5555;
    public static final int SAVE = 5556;
    public static final int DELETE = 5557;

    Earthquake earthquake;
    boolean wasCheck = false;

    public SaveLoader(Context c, Earthquake earthquake) {
        super(c);
        this.earthquake = earthquake;
    }

    @Override
    public Object loadInBackground() {

        switch (getId()) {
            case CHECK:
                wasCheck = true;
                Cursor cu = Database.fetchQuake(getContext(), earthquake.getPlace(),
                        String.valueOf(earthquake.getTime()));
                return Earthquake.fromCursor(cu);
            case SAVE:
                return getContext().getContentResolver().insert(Contract.QuakeEntry.ACCESS_URI,
                        earthquake.toContentValues());
            case DELETE:
                return Database.deleteQuake(getContext(), earthquake.getPlace(), String.valueOf(earthquake.getTime()));
            default:
                return null;
        }
    }

    public boolean wasCheck() {
        return wasCheck;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
