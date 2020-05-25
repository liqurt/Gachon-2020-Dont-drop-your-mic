/**
 * 190805 : 기초적 UI 만들기
 */
package ysi.gachon.ddym;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView title;
    ImageButton difficulty;
    ImageButton atmosphere;
    ImageButton voice_color;
    ImageButton settings;

    private Button btnMenu;
    private LinearLayout slideMenu;
    private Animation showMenu;
    private Animation non_showMenu;
    private boolean isSlideOpen = false;

    //SQLite
    private DBHelper dbHelper;

    private java.util.List<String> search_list;
    private ArrayList<String> song_list, singer_list, octave_list;
    private java.util.List<String> List;
    private ArrayList<String> arraylist;
    private SearchAdapter adapter;
    private EditText editSearch;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        difficulty = findViewById(R.id.difficulty);
        atmosphere = findViewById(R.id.atmosphere);
        voice_color = findViewById(R.id.voice_color);
        settings = findViewById(R.id.settings);

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.cd_title, Toast.LENGTH_SHORT).show();
            }
        });
        difficulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.cd_difficulty, Toast.LENGTH_SHORT).show();
            }
        });
        atmosphere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.cd_atmosphere, Toast.LENGTH_SHORT).show();
            }
        });
        voice_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.cd_voice_color, Toast.LENGTH_SHORT).show();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), settings.class);
                startActivity(intent);
            }
        });
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener{
        @Override
        public void onAnimationEnd(Animation animation){
            if(isSlideOpen){
                slideMenu.setVisibility(View.INVISIBLE);
                isSlideOpen = false;
            }
            else{
                isSlideOpen = true;
            }
        }

        @Override
        public void onAnimationRepeat(Animation animation){

        }
        @Override
        public void onAnimationStart(Animation animation){

        }
    }


    private void readData(){
        dbHelper = new DBHelper(MainActivity.this, "KaraokeDB.db", null, 1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM Karaoke";
        String song = "null", singer= "null", octave= "null";
        Cursor cursor = db.rawQuery(sql, null);
        byte[] blob;

        Bitmap bitmap;
        while(cursor.moveToNext()){
            song = cursor.getString(1);
            singer = cursor.getString(2);
            octave = cursor.getString(3);
            song_list.add(song);
            singer_list.add(singer);
            octave_list.add(octave);
        }

        cursor.close();
    }


    // 검색을 수행하는 메소드
    public void search(String charText) {
        search_list.clear();

        if (charText.length() == 0) {
            search_list.addAll(arraylist);
        }
        else
        {
            for(int i = 0;i < arraylist.size(); i++)
            {
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    search_list.add(arraylist.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}
