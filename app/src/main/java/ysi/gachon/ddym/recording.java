package ysi.gachon.ddym;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class recording extends AppCompatActivity {

    ProgressBar progress;
    ImageView graph;
    ImageButton recording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        progress = findViewById(R.id.progress);
        graph = findViewById(R.id.graph);
        recording = findViewById(R.id.recording);

        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"녹음을 시작합니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
