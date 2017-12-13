package wat.seth.dev.capstoneproject.data;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * Created by seth-wat on 12/12/2017.
 */
@Parcel
public class Earthquake {


    @ParcelConstructor
    public Earthquake(String id, int timeZone, double mag, String place, long time, int felt, double mni, double longitude, double latitude, double depth) {
        this.id = id;
        this.timeZone = timeZone;
        this.mag = mag;
        this.place = place;
        this.time = time;
        this.felt = felt;
        this.mni = mni;
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
    double mni; //intensity or shake
    double longitude;
    double latitude;
    double depth;

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

    public double getMni() {
        return mni;
    }

    public void setMni(double mni) {
        this.mni = mni;
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
