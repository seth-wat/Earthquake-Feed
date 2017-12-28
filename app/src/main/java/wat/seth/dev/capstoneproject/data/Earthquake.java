package wat.seth.dev.capstoneproject.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import wat.seth.dev.capstoneproject.db.Contract;

/**
 * Created by seth-wat on 12/12/2017.
 */
@Parcel
public class Earthquake {


    @ParcelConstructor
    public Earthquake(String id, int timeZone, double mag, String magType, String place, long time, int felt, double mmi, double longitude, double latitude, double depth) {
        this.id = id;
        this.timeZone = timeZone;
        this.mag = mag;
        this.magType = magType;
        this.place = place;
        this.time = time;
        this.felt = felt;
        this.mmi = mmi;
        this.longitude = longitude;
        this.latitude = latitude;
        this.depth = depth;
    }

    String id;
    int timeZone;
    double mag;
    String magType;
    String place;
    long time;
    int felt;
    double mmi; //intensity or shake
    double longitude;
    double latitude;
    double depth;


    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put(Contract.QuakeEntry.COLUMN_QUAKE_ID, id);
        cv.put(Contract.QuakeEntry.COLUMN_TIME_ZONE, timeZone);
        cv.put(Contract.QuakeEntry.COLUMN_MAG, mag);
        cv.put(Contract.QuakeEntry.COLUMN_MAG_TYPE, magType);
        cv.put(Contract.QuakeEntry.COLUMN_PLACE, place);
        cv.put(Contract.QuakeEntry.COLUMN_TIME, time);
        cv.put(Contract.QuakeEntry.COLUMN_FELT, felt);
        cv.put(Contract.QuakeEntry.COLUMN_MMI, mmi);
        cv.put(Contract.QuakeEntry.COLUMN_LONG, longitude);
        cv.put(Contract.QuakeEntry.COLUMN_LAT, latitude);
        cv.put(Contract.QuakeEntry.COLUMN_DEPTH, depth);
        return cv;
    }

    public static ArrayList<Earthquake> fromCursor(Cursor c) {
        ArrayList<Earthquake> earthquakes = new ArrayList<>();
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex(Contract.QuakeEntry.COLUMN_QUAKE_ID));
            int timeZone = c.getInt(c.getColumnIndex(Contract.QuakeEntry.COLUMN_TIME_ZONE));
            double mag = c.getDouble(c.getColumnIndex(Contract.QuakeEntry.COLUMN_MAG));
            String magType = c.getString(c.getColumnIndex(Contract.QuakeEntry.COLUMN_MAG_TYPE));
            String place = c.getString(c.getColumnIndex(Contract.QuakeEntry.COLUMN_PLACE));
            long time = c.getLong(c.getColumnIndex(Contract.QuakeEntry.COLUMN_TIME));
            int felt = c.getInt(c.getColumnIndex(Contract.QuakeEntry.COLUMN_FELT));
            double mmi = c.getDouble(c.getColumnIndex(Contract.QuakeEntry.COLUMN_MMI));
            double longtidue = c.getDouble(c.getColumnIndex(Contract.QuakeEntry.COLUMN_LONG));
            double latitude = c.getDouble(c.getColumnIndex(Contract.QuakeEntry.COLUMN_LAT));
            double depth = c.getDouble(c.getColumnIndex(Contract.QuakeEntry.COLUMN_DEPTH));

            Earthquake eq = new Earthquake(id, timeZone, mag, magType, place, time, felt, mmi, longtidue, latitude, depth);
            earthquakes.add(eq);
        }

        return earthquakes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public double getMag() {
        return mag;
    }

    public void setMag(double mag) {
        this.mag = mag;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getFelt() {
        return felt;
    }

    public void setFelt(int felt) {
        this.felt = felt;
    }

    public double getMmi() {
        return mmi;
    }

    public void setMmi(double mmi) {
        this.mmi = mmi;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDepth() {
        return depth;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public String getMagType() {
        return magType;
    }

    public void setMagType(String magType) {
        this.magType = magType;
    }


    /*
    Below methods called to display values to UI.

    Methods manipulate raw place which contains string like:
        "83km NNE of Sutton-Alpine, Alaska"
     */


    public String getReadableMag() {
        return String.valueOf(mag) + " " + magType;
    }

    public String getReadableMmi() {
        if (mmi == 0.0) {
            return String.valueOf(mmi).substring(0, 1) + " mmi";
        }

        return String.valueOf(mmi) + " mmi";
    }

    public String getReadableDepth(boolean imperial) {
        if (!imperial) {
            return String.valueOf(depth) + " km";
        }
        double kiloToMi = 0.62137119;
        double depthMi = depth * kiloToMi;
        String depthString = String.valueOf(depthMi);
        /*
        String should only contain 2 places after decimal max.
         */
        int grabLastIndex = depthString.indexOf(".") + 3 > depthString.length() ?
                depthString.length() : depthString.indexOf(".") + 3;
        String formattedDepth = depthString.substring(0, grabLastIndex);
        return formattedDepth + " mi";
    }

    public String getReadableFelt() {
        if (felt == 0) {
            return "No people";
        }
        if (felt == 1) {
            return String.valueOf(felt) + " person";
        }

        return String.valueOf(felt + " people");
    }

    public String getReadablePlace() {
        if (!place.contains("km")) {
            /*
            Central Mid-Atlantic Ridge
            fully qualified place edge case
            */
            return place;
        }
        Log.v("adfasd", "places : " + place);
        String placeName = place.substring(place.indexOf("f") + 2, place.length());
        return placeName;
    }

    public String getReadablePlaceDistance(boolean imperial) {

        if (!place.contains("km")) {
            /*
            Central Mid-Atlantic Ridge
            fully qualified place edge case
            */
            return "";
        }

        String suffix = imperial ? "miles" : "km";


        int distanceEndIndex = place.indexOf("k") - 1 == 0 ? 1 : place.indexOf("k");
        String distanceString = place.substring(0, distanceEndIndex) + ".0";
        String direction = place.substring(place.indexOf("m") + 2, place.indexOf("o") - 1);

        if (!imperial) {
            StringBuilder sB = new StringBuilder();
            sB.append(distanceString);
            sB.append(" ");
            sB.append(suffix);
            sB.append(" ");
            sB.append(direction);
            return new String(sB);
        }

        double kiloToMi = 0.62137119;
        double miles = Double.valueOf(distanceString) * kiloToMi;

        String stringMiles = String.valueOf(miles);
        String displayMiles = stringMiles.substring(0, stringMiles.indexOf(".") + 2);

        if (miles == 0.0) {
            displayMiles = "";
            suffix = "";
        }

        StringBuilder sB = new StringBuilder();
        sB.append(displayMiles);
        sB.append(" ");
        sB.append(suffix);
        sB.append(" ");
        sB.append(direction);

        return new String(sB);
    }

    public String getReadableTime() {
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);
        /*
        Corrects time display to two digits.
        Corrects month display
         */
        int displayHour = date.get(Calendar.HOUR) == 0 ? 12 : date.get(Calendar.HOUR);
        String displayMinute = date.get(Calendar.MINUTE) < 10 ?
                "0" + String.valueOf(date.get(Calendar.MINUTE)) : String.valueOf(date.get(Calendar.MINUTE));
        String displaySecond = date.get(Calendar.SECOND) < 10 ?
                "0" + String.valueOf(date.get(Calendar.SECOND)) : String.valueOf(date.get(Calendar.SECOND));


        StringBuilder sB = new StringBuilder();

        sB.append(displayHour);
        sB.append(":");
        sB.append(displayMinute);
        sB.append(" ");
        sB.append(date.getDisplayName(Calendar.AM_PM, Calendar.SHORT, Locale.US));

        return new String(sB);
    }

    public String getReadableDate() {

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(time);
        String displayMonth = String.valueOf(date.get(Calendar.MONTH) + 1);
        StringBuilder sB = new StringBuilder();
        sB.append(displayMonth);
        sB.append("-");
        sB.append(date.get(Calendar.DAY_OF_MONTH));
        sB.append("-");
        sB.append(date.get(Calendar.YEAR));

        return new String(sB);
    }

}
