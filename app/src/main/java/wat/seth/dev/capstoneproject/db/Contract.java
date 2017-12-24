package wat.seth.dev.capstoneproject.db;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by seth-wat on 12/13/2017.
 */

public class Contract {
    public static final String AUTHORITY = "wat.seth.dev.capstoneproject";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    public static final String QUAKE_TABLE = "quakes";

    public static final class QuakeEntry implements BaseColumns {
        public static final Uri ACCESS_URI = BASE_URI.buildUpon().appendPath(QUAKE_TABLE).build();

        public static final String COLUMN_QUAKE_ID = "quake_id";
        public static final String COLUMN_TIME_ZONE = "time_zone";
        public static final String COLUMN_MAG = "mag";
        public static final String COLUMN_MAG_TYPE = "mag_type";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_FELT = "felt";
        public static final String COLUMN_MMI = "mmi";
        public static final String COLUMN_LONG = "longitude";
        public static final String COLUMN_LAT = "latitude";
        public static final String COLUMN_DEPTH = "depth";

    }
}
