/**
 * 190805 : 기초적 UI 만들기
 */
package ysi.gachon.ddym;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton title;
    ImageButton difficulty;
    ImageButton atmosphere;
    ImageButton voice_color;
    ImageButton settings;


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
}
