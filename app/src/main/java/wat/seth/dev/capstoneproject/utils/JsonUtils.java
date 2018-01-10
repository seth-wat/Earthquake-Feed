package wat.seth.dev.capstoneproject.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.data.Earthquake;

/**
 * Created by seth-wat on 12/12/2017.
 */

public class JsonUtils {
    public static final String TAG = JsonUtils.class.getSimpleName();
    public static final String METADATA = "metadata";
    public static final String STATUS = "status";
    public static final String FEATURES = "features";
    public static final String GEOMETRY = "geometry";
    public static final String COORDINATES = "coordinates";
    public static final String PROPERTIES = "properties";
    public static final String ID = "id";
    public static final String TIME_ZONE = "tz";
    public static final String MAG = "mag";
    public static final String MAG_TYPE = "magType";
    public static final String PLACE = "place";
    public static final String TIME = "time";
    public static final String FELT = "felt";
    public static final String MMI = "mmi";


    public static ArrayList<Earthquake> parseJson(String rawData) {
        if (rawData.isEmpty()) {
            return null;
        }

        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(rawData);
            JSONObject metadata = root.getJSONObject(METADATA);
            if (metadata.getInt(STATUS) != 200) {
                Log.v(TAG, "Unsuccessful ... status: " + metadata.getInt(STATUS));
                return null;
            }
            JSONArray quakes = root.getJSONArray(FEATURES);
            for (int i = 0; i < quakes.length(); i++) {
                JSONObject quake = quakes.getJSONObject(i);
                JSONObject properties = quake.getJSONObject(PROPERTIES);
                JSONArray coordinates = quake.getJSONObject(GEOMETRY).getJSONArray(COORDINATES);

                String id = "";
                if (!properties.isNull(ID)) {
                    id = properties.getString(ID);
                }

                int timeZone = 0;
                if (!properties.isNull(TIME_ZONE)) {
                    timeZone = properties.getInt(TIME_ZONE);
                }

                double mag = 0;
                if (!properties.isNull(MAG)) {
                    mag = properties.getDouble(MAG);
                }

                String magType = "";
                if (!properties.isNull(MAG_TYPE)) {
                    magType = properties.getString(MAG_TYPE);
                }

                String place = "";
                if (!properties.isNull(PLACE)) {
                    place = properties.getString(PLACE);
                }

                long time = 0;
                if (!properties.isNull(TIME)) {
                    time = properties.getLong(TIME);
                }

                int felt = 0;
                if (!properties.isNull(FELT)) {
                    felt = properties.getInt(FELT);
                }

                double mni = 0;
                if (!properties.isNull(MMI)) {
                    mni = properties.getDouble(MMI);
                }

                double latitude = 0;
                double longitude = 0;
                double depth = 0;
                for (int j = 0; j < coordinates.length(); j++) {
                    longitude = coordinates.getDouble(0);
                    latitude = coordinates.getDouble(1);
                    depth = coordinates.getDouble(2);
                }
                Earthquake singleQuake = new Earthquake(
                        id, timeZone, mag, magType, place, time, felt, mni, longitude, latitude, depth
                );


                earthquakes.add(singleQuake);
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to parse JSON: " + e.getMessage());
        }

        return earthquakes;
    }
}
