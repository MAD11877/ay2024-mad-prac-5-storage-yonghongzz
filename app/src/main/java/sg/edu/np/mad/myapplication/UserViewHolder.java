package sg.edu.np.mad.myapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {
    ImageView smallImage;
    TextView name;
    TextView description;

    public UserViewHolder(View itemView){
        super(itemView);
        smallImage = itemView.findViewById(R.id.ivSmallImage);
        name = itemView.findViewById(R.id.tvName);
        description = itemView.findViewById(R.id.tvDescription);

    }
}
