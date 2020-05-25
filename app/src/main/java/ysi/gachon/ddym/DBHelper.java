package ysi.gachon.ddym;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @brief Database manager
 * @detail Create a Database and manage version of the Database
 */
class DBHelper extends SQLiteOpenHelper {

    /*
    public DBHelper(Context context){
        super(context, "MyDB", null, 1);
    }*/

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * @brief Create database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE closet (_id integer primary key autoincrement, category TEXT, thickness TEXT, length TEXT, image BLOB);");
    }

    /**
     * @brief Manage version of database
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS closet");
        onCreate(db);
    }

}