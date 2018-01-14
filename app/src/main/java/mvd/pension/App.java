package mvd.pension;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by Dmitry on 14.01.2018.
 */

public class App extends Application {
    @Override
    protected void attachBaseContext(Context context) {
        try {
            super.attachBaseContext(context);
            MultiDex.install(this);
        } catch (RuntimeException ignored) {
            // Multidex support doesn't play well with Robolectric yet
        }
    }
}
