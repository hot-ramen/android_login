package hotramen.apps.annotation_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById
    EditText etUsername;

    @ViewById
    EditText etPassword;

    @ViewById
    CheckBox cbRemember;

    @ViewById
    Button btnSignUp;

    Context context;
    Realm realm = Realm.getDefaultInstance();



    @AfterViews
    public void init() {
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        boolean isRemembered = sharedPreferences.getBoolean("isRemembered", false);
        String userID = sharedPreferences.getString("uuid", null);

        User userRemembered = realm.where(User.class).equalTo("uuid", userID).findFirst();

        if (isRemembered){
            assert userRemembered != null;
            etUsername.setText(userRemembered.getName());
            etPassword.setText(userRemembered.getPassword());
            cbRemember.setChecked(true);
        }



    }

    @Click(R.id.btnSignUp)
    public void register(){
       RegisterActivity_.intent(this)
               .start();
    }

    @Click(R.id.btnSignIn)
    public void login(){
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String u = sharedPreferences.getString("username", null);
        String p = sharedPreferences.getString("password", null);

        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();

        long result = realm.where(User.class)
                .equalTo("name", username)
                .count();
//        if(u == null && TextUtils.isEmpty(user) && TextUtils.isEmpty(pass)){

        User userSignIn = realm.where(User.class)
                .equalTo("name", username)
                .findFirst();


        if(result == 0){
            Toast.makeText(getApplicationContext(), "User does not exist", Toast.LENGTH_LONG).show() ;
        }
        else {
            assert userSignIn != null;

            if(!userSignIn.getPassword().equals(password)){
                Toast.makeText(getApplicationContext(), "Invalid Password", Toast.LENGTH_LONG).show() ;
            }

            else{
                WelcomeActivity_.intent(this)
                        .start();
                editor.putBoolean("isRemembered", cbRemember.isChecked());
                editor.putString("uuid", userSignIn.getUuid());
                editor.apply();

            }
        }
    }


    @Click(R.id.btnClear)
    public void clear(){
        SharedPreferences sharedPreferences = getSharedPreferences("login_info", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        etUsername.setText("");
        etPassword.setText("");
        cbRemember.setChecked(false);

        Toast.makeText(getApplicationContext(), "Preferences Cleared", Toast.LENGTH_LONG).show();
    }


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }




}

