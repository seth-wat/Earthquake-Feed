package wat.seth.dev.capstoneproject.utils;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.view.animation.AlphaAnimation;

import java.util.ArrayList;

/**
 * Created by seth-wat on 12/18/2017.
 */

public class ExpandingFabUtil {
    public static final int DURATION = 550;
    public static final int DELAY = 80;

    private ArrayList<FloatingActionButton> fabList = new ArrayList<>();
    private ArrayList<AlphaAnimation> animations = new ArrayList<>();
    private boolean isShown = false;
    private FloatingActionButton mainFab;

    public ExpandingFabUtil(FloatingActionButton mainFab) {
        this.mainFab = mainFab;
    }

    public void appendFab(FloatingActionButton fab) {
        fabList.add(fab);
    }

    public void animateFabs() {

        if (!isShown) {
            int delay = DELAY;
            for (int i = 0; i < fabList.size(); i++) {
                AlphaAnimation a = new AlphaAnimation(0, 1);
                a.setDuration(DURATION);
                a.setStartOffset(delay);
                a.setFillAfter(true);
                animations.add(i, a);
                delay += DELAY;
            }

            for (int i = 0; i < fabList.size(); i++) {
                fabList.get(i).startAnimation(animations.get(i));
            }
            isShown = !isShown;
        } else {
            int delay = DELAY * fabList.size();
            for (int i = 0; i < fabList.size(); i++) {
                AlphaAnimation a = new AlphaAnimation(1, 0);
                a.setDuration(DURATION);
                a.setStartOffset(delay);
                a.setFillAfter(true);
                animations.add(i, a);
                delay -= DELAY;
            }

            for (int i = 0; i < fabList.size(); i++) {
                fabList.get(i).startAnimation(animations.get(i));
            }
            isShown = !isShown;
        }
    }

    public void swapMainDrawable(final Drawable drawable) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainFab.setImageDrawable(drawable);
            }
        }, 700);
    }



}
