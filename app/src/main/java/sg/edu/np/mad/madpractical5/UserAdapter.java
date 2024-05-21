package sg.edu.np.mad.madpractical5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    private ArrayList<User> list_object;
    private clickListener listener;
    //private ListActivity activity
    public UserAdapter(ArrayList<User> list_object,clickListener listener){
        this.list_object = list_object;
        this.listener = listener;
        //this.activity = activity;
    }
    public UserViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list,parent,false);
        UserViewHolder holder = new UserViewHolder(view);

        return holder;
    }
    public void onBindViewHolder(UserViewHolder holder, int position){

        User list_items = list_object.get(position);
        char digit = list_items.getName().charAt(list_items.getName().length()-1);
        if(digit == '7'){
            holder.name.setVisibility(View.GONE);
            holder.description.setVisibility(View.GONE);
            holder.smallImage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        }
        else{
            holder.name.setText(list_items.getName());
            holder.description.setText(list_items.getDescription());
        }
        final int itemPosition = position;
        holder.smallImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onImageClick(itemPosition);
                }
            }
        });
    }
    public int getItemCount(){
        return list_object.size();
    }

}

