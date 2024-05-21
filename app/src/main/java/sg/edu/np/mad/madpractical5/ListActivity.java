package sg.edu.np.mad.madpractical5;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class ListActivity extends AppCompatActivity implements clickListener  {
    private static ArrayList<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        if(checkDb()){
            for(int i = 0; i < 20;i += 1){
                int name = new Random().nextInt(99999999);
                int description = new Random().nextInt(999999999);
                boolean followed = new Random().nextBoolean();
                User user = new User("John Doe","MAD Developer",1,false);
                user.setName("Name"+String.valueOf(name));
                user.setDescription("Description"+String.valueOf(description));
                user.setFollowed(followed);
                userList.add(user);
            }

            initUser(userList);
        }
        getAllUser();


        UserAdapter userAdapter = new UserAdapter(getAllUser(),this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(userAdapter);



    }

    @Override
    public void onImageClick(int position) {
        ArrayList<User> userList = getAllUser();
        AlertDialog.Builder builder = new AlertDialog.Builder(ListActivity.this);
        User currentUser = userList.get(position);
        builder.setTitle("Profile");
        builder.setMessage(currentUser.getName());
        builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent activity = new Intent(ListActivity.this,MainActivity.class);
                activity.putExtra("user",currentUser);
                startActivity(activity);

            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void initUser(ArrayList<User> userList){
        MyDbHandler dbhandler = new MyDbHandler(this,null,null,1);
        dbhandler.initUser(userList);
    }

    public ArrayList<User> getAllUser(){
        MyDbHandler dbHandler = new MyDbHandler(this,null,null,1);
        return dbHandler.getUser();
    }

    public boolean checkDb(){
        MyDbHandler dbHandler = new MyDbHandler(this,null,null,1);
        return dbHandler.checkDb();

    }
}