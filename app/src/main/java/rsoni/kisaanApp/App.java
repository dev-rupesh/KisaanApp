package rsoni.kisaanApp;

import android.app.Application;
import rsoni.modal.User;

/**
 * Created by DS1 on 03/08/16.
 */
public class App extends Application{

    public static boolean is_loged = false;
    public static User user;
    public static String base_url = "";

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
