package wat.seth.dev.capstoneproject.utils;

import android.support.design.widget.FloatingActionButton;
import android.view.animation.AlphaAnimation;

import java.util.ArrayList;

/**
 * Created by seth-wat on 12/18/2017.
 */

public class ExpandingFabUtil {
    ArrayList<FloatingActionButton> fabList = new ArrayList<>();
    ArrayList<AlphaAnimation> animations = new ArrayList<>();
    boolean isShown;
    public static final int DURATION = 1000;

    public ExpandingFabUtil(boolean b) {
        isShown = b;
    }

    public void appendFab(FloatingActionButton fab) {
        fabList.add(fab);
    }

    public void animateFabs() {

        if (!isShown) {
            int delay = 100;
            for (int i = 0; i < fabList.size(); i++) {
                AlphaAnimation a = new AlphaAnimation(0, 1);
                a.setDuration(DURATION);
                a.setStartOffset(delay);
                a.setFillAfter(true);
                animations.add(i, a);
                delay += 100;
            }

            for (int i = 0; i < fabList.size(); i++) {
                fabList.get(i).startAnimation(animations.get(i));
            }
            isShown = !isShown;
        } else {
            int delay = 100 * fabList.size();
            for (int i = 0; i < fabList.size(); i++) {
                AlphaAnimation a = new AlphaAnimation(1, 0);
                a.setDuration(DURATION);
                a.setStartOffset(delay);
                a.setFillAfter(true);
                animations.add(i, a);
                delay -= 100;
            }

            for (int i = 0; i < fabList.size(); i++) {
                fabList.get(i).startAnimation(animations.get(i));
            }
            isShown = !isShown;
        }
    }



}
