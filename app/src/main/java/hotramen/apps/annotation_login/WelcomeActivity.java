package hotramen.apps.annotation_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmQuery;

@EActivity(R.layout.welcome)
public class WelcomeActivity extends AppCompatActivity {

    @ViewById
    TextView tvWelcome;

    Realm realm = Realm.getDefaultInstance();



    @AfterViews
    public void init(){
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        String userID = sharedPreferences.getString("uuid", "");

        User userSignedIn = realm.where(User.class).equalTo("uuid", userID).findFirst();

        boolean isRemembered = sharedPreferences.getBoolean("isRemembered", false);

        if (!isRemembered){
            tvWelcome.setText("Welcome "+userSignedIn.getName()+"!!!");
        }
        else {
            tvWelcome.setText("Welcome "+userSignedIn.getName()+"!!! You will be remembered.");
        }
    }

    @Click(R.id.btnSignOut)
    public void signout(){
        finish();
    }


    @Click(R.id.btnList)
    public void list(){
        RecyclerViewActivity_.intent(this)
                .start();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.welcome);
//    }
}