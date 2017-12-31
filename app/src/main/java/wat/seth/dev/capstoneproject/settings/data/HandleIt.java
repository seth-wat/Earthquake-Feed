package wat.seth.dev.capstoneproject.settings.data;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by seth-wat on 12/29/2017.
 */

public class HandleIt {

    public String type;
    Handler handler;
    Runnable first;
    Runnable inner;
    EachLoop toDo;
    boolean initialCheck;
    boolean nextCheck;
    int wait;

    /*
    This view

    run() gets called on ACTION_DOWN
    stop() gets called on ACTION_UP
     */
    public HandleIt(EachLoop e, int ms, String type) {
        toDo = e;
        wait = ms;
        this.type = type;

        handler = new Handler();
        inner = new Runnable() {
            @Override
            public void run() {
                /*
                If the view is not being pressed or, the view has just been touched
                exit.
                 */
                if (!initialCheck || !nextCheck) return;
                toDo.eachLoop();
                handler.postDelayed(this, wait);
            }
        };
        first = new Runnable() {
            @Override
            public void run() {
                /*
                If the view is not being pressed don't run
                 */
                if (initialCheck == false) return;
                nextCheck = true; //For every preceding loop while pressed
                toDo.eachLoop();
                handler.postDelayed(inner, wait);
            }
        };

    }

    public void run() {
        /*
        Was just touched so any running recursion should stop,
        we should prepare to start a new recursive loop.
         */
        toDo.setType(type);
        initialCheck = true;
        nextCheck = false;
        toDo.eachLoop();
        handler.removeCallbacks(first);
        handler.postDelayed(first, wait);
    }

    public void stop() {
        initialCheck = false;
        nextCheck = false;
    }

    public View.OnTouchListener getTouchListener() {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    run();

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    stop();
                    toDo.finish();
                }
                return true;
            }
        };
    }

}
