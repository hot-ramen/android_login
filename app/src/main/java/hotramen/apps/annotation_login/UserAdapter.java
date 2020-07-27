package hotramen.apps.annotation_login;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import javax.annotation.Nullable;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;


public class UserAdapter extends RealmRecyclerViewAdapter<User, UserAdapter.ViewHolder> {

    RecyclerViewActivity activity;

    public UserAdapter(RecyclerViewActivity activity, @Nullable OrderedRealmCollection<User> data, boolean autoUpdate){

        super(data, autoUpdate);

        this.activity = activity;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView uuid;
        TextView name;
        ImageButton delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            uuid = itemView.findViewById(R.id.tvUuid);
            name = itemView.findViewById(R.id.tvName);

            delete = itemView.findViewById(R.id.ibtnDelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){

        View v = activity.getLayoutInflater().inflate(R.layout.row_layout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // gives you the data object at the given position
        User u = getItem(position);


        // copy all the values needed to the appropriate views
        holder.uuid.setText(u.getUuid());
        holder.name.setText(u.getName());

        // NOTE: MUST BE A STRING String.valueOf() converts most types to a string


        // do any other initializations here as well,  e.g. Button listeners
        holder.delete.setTag(u);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.delete(view);
            }
        });
    }

}
