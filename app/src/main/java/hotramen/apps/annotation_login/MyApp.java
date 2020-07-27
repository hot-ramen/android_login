package hotramen.apps.annotation_login;

import android.app.Application;

import org.androidannotations.annotations.EApplication;

import io.realm.Realm;


@EApplication
public class MyApp extends Application {
    public void onCreate()
    {
        super.onCreate();

        Realm.init(this);
    }
}
