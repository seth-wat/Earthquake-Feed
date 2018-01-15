package wat.seth.dev.capstoneproject.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.FileProvider;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.io.FileOutputStream;

import wat.seth.dev.capstoneproject.DetailActivity;
import wat.seth.dev.capstoneproject.R;
import wat.seth.dev.capstoneproject.data.Earthquake;

/**
 * Created by seth-wat on 12/18/2017.
 */

public class SocialHelper {
    /*
    Save image of the map and compose Tweet
     */
    public static final String TWITTER = "twitter";

    private SocialHelper() {

    }

    public static void twitterShare(Bitmap b, Context c, Earthquake earthquake) {
        File path = new File(c.getFilesDir(), "images");
        File file = new File(path + "/" + earthquake.getPlace() + ".png");
        if (!file.getParentFile().exists())
            file.getParentFile().mkdirs();
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        TweetComposer.Builder builder = new TweetComposer.Builder(c)
                .text(earthquake.getReadableMag() + " " + c.getResources().getString(R.string.twitter_share) + " " + earthquake.getReadablePlace())
                .image(FileProvider.getUriForFile(c, "wat.seth.dev.capstoneproject.FileProvider", file));
        builder.show();
    }

}
