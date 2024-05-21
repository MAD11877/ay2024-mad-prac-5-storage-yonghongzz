package sg.edu.np.mad.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent intent = getIntent();

        User user = (User) intent.getSerializableExtra("user");
        TextView tvName = findViewById(R.id.tvName);
        TextView tvDescription = findViewById(R.id.tvDescription);
        Button btnFollow = findViewById(R.id.btnFollow);
        Button msg = findViewById(R.id.button2);

        tvName.setText(user.getName());
        tvDescription.setText(user.getDescription());
        if(user.getFollowed() == false){
            btnFollow.setText("Follow");
        }else{
            btnFollow.setText("Unfollow");
        }

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnFollow.getText() == "Follow"){
                    btnFollow.setText("Unfollow");
                    user.setFollowed(true);
                    updateUser(user);
                    Toast.makeText(MainActivity.this,"Followed",Toast.LENGTH_SHORT).show();
                }
                else{
                    btnFollow.setText("Follow");
                    user.setFollowed(false);
                    updateUser(user);
                    Toast.makeText(MainActivity.this,"Not Followed",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void updateUser(User user){
        MyDbHandler dbHandler = new MyDbHandler(this,null,null,1);
        dbHandler.updateUser(user);
    }
}