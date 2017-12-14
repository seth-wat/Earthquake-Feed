package wat.seth.dev.capstoneproject.data;

import android.content.ContentValues;
import android.database.Cursor;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.ArrayList;

import wat.seth.dev.capstoneproject.db.Contract;

/**
 * Created by seth-wat on 12/12/2017.
 */
@Parcel
public class Earthquake {


    @ParcelConstructor
    public Earthquake(String id, int timeZone, double mag, String place, long time, int felt, double mmi, double longitude, double latitude, double depth) {
        this.id = id;
        this.timeZone = timeZone;
        this.mag = mag;
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
            String place = c.getString(c.getColumnIndex(Contract.QuakeEntry.COLUMN_PLACE));
            long time = c.getLong(c.getColumnIndex(Contract.QuakeEntry.COLUMN_TIME));
            int felt = c.getInt(c.getColumnIndex(Contract.QuakeEntry.COLUMN_FELT));
            double mmi = c.getDouble(c.getColumnIndex(Contract.QuakeEntry.COLUMN_MMI));
            double longtidue = c.getDouble(c.getColumnIndex(Contract.QuakeEntry.COLUMN_LONG));
            double latitude = c.getDouble(c.getColumnIndex(Contract.QuakeEntry.COLUMN_LAT));
            double depth = c.getDouble(c.getColumnIndex(Contract.QuakeEntry.COLUMN_DEPTH));

            Earthquake eq = new Earthquake(id, timeZone, mag, place, time, felt, mmi, longtidue, latitude, depth);
            earthquakes.add(eq);
        }

        return  earthquakes;
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


}
