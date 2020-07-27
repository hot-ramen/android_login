package hotramen.apps.annotation_login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

@EActivity(R.layout.recylerview)
public class RecyclerViewActivity extends AppCompatActivity {

    Realm realm;


    @ViewById
    RecyclerView rvUsers;

    UserAdapter adapter;





    @AfterViews
    public void init(){
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rvUsers.setLayoutManager(mLayoutManager);

        realm = Realm.getDefaultInstance();

        RealmResults<User> list = realm.where(User.class)
                .findAll()
                .sort("name", Sort.ASCENDING);

        adapter = new UserAdapter(this, list, true);
        rvUsers.setAdapter(adapter);

    }

    public void onDestroy(){
        super.onDestroy();

        realm.close();

    }

    @Click(R.id.btnClear)
    public void clear(){

        RealmResults<User> list = realm.where(User.class).findAll();

        realm.beginTransaction();
        list.deleteAllFromRealm();
        realm.commitTransaction();

    }

    @Click(R.id.btnShow)
    public void show() {

    }

    public void delete(View view)
    {
        User user = (User) view.getTag();

        // once an object is deleted you cant delete it again, check if isValid
        // this is needed since you can double tap a button very easily
        if (user.isValid())
        {
            realm.beginTransaction();
            user.deleteFromRealm();
            realm.commitTransaction();
        }

    }
}