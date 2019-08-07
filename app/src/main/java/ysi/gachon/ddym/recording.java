
package ysi.gachon.ddym;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class recording extends AppCompatActivity {

    ProgressBar progress;
    ImageView graph;
    ImageButton recording;

    TextView pitchText;
    TextView noteText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 1);
        }
        float high=0,low=0;

        progress = findViewById(R.id.progress);
        graph = findViewById(R.id.graph);
        recording = findViewById(R.id.recording);
        pitchText = findViewById(R.id.pitchText);
        noteText = findViewById(R.id.noteText);
        recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "녹음을 시작합니다.", Toast.LENGTH_SHORT).show();
                AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);
                PitchDetectionHandler pdh = new PitchDetectionHandler() {
                    @Override
                    public void handlePitch(PitchDetectionResult res, AudioEvent e){
                        //이거 파이널로 안하고 싶은데.
                        //하이로우도 따보자.
                        //도서관이라서 테스트하기 존나 불편하다.
                        final float pitchInHz = res.getPitch();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                processPitch(pitchInHz);
                                Log.d("ddym",Float.toString(pitchInHz));
                            }
                        });
                    }
                };
                AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pdh);
                dispatcher.addAudioProcessor(pitchProcessor);
                Thread audioThread = new Thread(dispatcher, "Audio Thread");
                audioThread.start();
            }
        });
    }

    public void processPitch(float pitchInHz){
        pitchText.setText(""+pitchInHz);
        if(pitchInHz >= 110 && pitchInHz < 123.47) {
            //A
            noteText.setText("A");
        }
        else if(pitchInHz >= 123.47 && pitchInHz < 130.81) {
            //B
            noteText.setText("B");
        }
        else if(pitchInHz >= 130.81 && pitchInHz < 146.83) {
            //C
            noteText.setText("C");
        }
        else if(pitchInHz >= 146.83 && pitchInHz < 164.81) {
            //D
            noteText.setText("D");
        }
        else if(pitchInHz >= 164.81 && pitchInHz <= 174.61) {
            //E
            noteText.setText("E");
        }
        else if(pitchInHz >= 174.61 && pitchInHz < 185) {
            //F
            noteText.setText("F");
        }
        else if(pitchInHz >= 185 && pitchInHz < 196) {
            //G
            noteText.setText("G");
        }
    }
}
