package wat.seth.dev.capstoneproject.settings.data;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by seth-wat on 12/29/2017.
 */

public abstract class EachLoop {
    public static final String TYPE_INCREMENT = "type_increment";
    public static final String TYPE_DECREMENT = "type_decrement";
    //Determines what action to take in eachLoop()
    private String type;

    //Method to execute while the button is pressed/touched
    abstract void eachLoop();
    //Method to execute while the button is released
    abstract void finish();

    //Determines what action to take in eachLoop()
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
