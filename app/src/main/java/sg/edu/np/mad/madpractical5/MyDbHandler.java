package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class MyDbHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    public static final String USER = "user";
    public static final String COLUMN_NAME = "name";

    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_FOLLOWED = "followed";
    public MyDbHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String CREATE_USER_TABLE = "CREATE TABLE "+ USER + "(" + COLUMN_NAME + " TEXT," + COLUMN_DESCRIPTION + " TEXT," + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_FOLLOWED + " INTEGER" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db , int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + USER);
        onCreate(db);
    }

    public void initUser(ArrayList<User> userList){
        SQLiteDatabase db = this.getWritableDatabase();
        for(User user:userList){
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME,user.getName());
            values.put(COLUMN_DESCRIPTION,user.getDescription());
            values.put(COLUMN_FOLLOWED,user.getFollowed());
            db.insert(USER,null,values);
        }
        db.close();
    }

    public ArrayList<User> getUser(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ USER;
        Cursor cursor = db.rawQuery(query,null);
        while(cursor.moveToNext()){
            int id = cursor.getInt((int)cursor.getColumnIndex("id"));
            String name = cursor.getString((int)cursor.getColumnIndex("name"));
            String description = cursor.getString((int)cursor.getColumnIndex("description"));
            int followed = cursor.getInt((int)cursor.getColumnIndex("followed"));
            boolean isFollowed = followed > 0;
            User user = new User(name,description,id,isFollowed);
            userList.add(user);
        }
        cursor.close();
        return userList;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID,String.valueOf(user.getId()));
        values.put(COLUMN_NAME,String.valueOf(user.getName()));
        values.put(COLUMN_DESCRIPTION,String.valueOf(user.getDescription()));
        int isFollowedInt = user.getFollowed() ? 1 : 0;
        values.put(COLUMN_FOLLOWED,String.valueOf(isFollowedInt));
        String clause = "id=?";
        String[] args = {String.valueOf(user.getId())};
        db.update(USER,values,clause,args);
    }

    public boolean checkDb() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + USER, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }
}
