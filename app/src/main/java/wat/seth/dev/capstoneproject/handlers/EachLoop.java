package wat.seth.dev.capstoneproject.handlers;

import android.app.Activity;
import android.widget.TextView;

/**
 * Created by seth-wat on 12/29/2017.
 */

public abstract class EachLoop {
    /*
    Originally intended to be used with the HandleIt class to provide touch
    and hold functionality.
     */
    public static final String TYPE_INCREMENT = "type_increment";
    public static final String TYPE_DECREMENT = "type_decrement";
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
