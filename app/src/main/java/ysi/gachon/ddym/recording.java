
package ysi.gachon.ddym;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteOrder;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;
import be.tarsos.dsp.writer.WriterProcessor;

import static ysi.gachon.ddym.useThisAllOver.getHigh;
import static ysi.gachon.ddym.useThisAllOver.getLow;
import static ysi.gachon.ddym.useThisAllOver.setHigh;
import static ysi.gachon.ddym.useThisAllOver.setLow;


public class recording extends AppCompatActivity {

    AudioDispatcher dispatcher;
    TarsosDSPAudioFormat tarsosDSPAudioFormat;

    File file;

    TextView highest;
    TextView lowest;
    TextView pitchTextView;
    Button recordButton;

    boolean isRecording = false;
    String filename = "recorded_sound.wav";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        File sdCard = Environment.getExternalStorageDirectory();
        file = new File(sdCard, filename);

        tarsosDSPAudioFormat = new TarsosDSPAudioFormat(TarsosDSPAudioFormat.Encoding.PCM_SIGNED, 22050, 2 * 8, 1, 2 * 1, 22050, ByteOrder.BIG_ENDIAN.equals(ByteOrder.nativeOrder()));

        pitchTextView = findViewById(R.id.pitchTextView);
        recordButton = findViewById(R.id.recordButton);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRecording) {
                    recordAudio();
                    isRecording = true;
                    recordButton.setText("중지");
                } else {
                    stopRecording();
                    isRecording = false;
                    recordButton.setText("녹음");
                }
            }
        });

        highest=findViewById(R.id.highestNote);
        lowest=findViewById(R.id.lowestNote);
    }

    public void recordAudio() {
        releaseDispatcher();
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);

        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"rw");
            AudioProcessor recordProcessor = new WriterProcessor(tarsosDSPAudioFormat, randomAccessFile);
            dispatcher.addAudioProcessor(recordProcessor);

            PitchDetectionHandler pitchDetectionHandler = new PitchDetectionHandler() {
                @Override
                public void handlePitch(PitchDetectionResult res, AudioEvent e){
                    final float pitchInHz = res.getPitch();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pitchTextView.setText(pitchInHz + "");
                            calcHighLow(pitchInHz);
                        }
                    });
                }
            };

            AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, pitchDetectionHandler);
            dispatcher.addAudioProcessor(pitchProcessor);

            Thread audioThread = new Thread(dispatcher, "Audio Thread");
            audioThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        releaseDispatcher();
        highest.setText(pitchToScale(getHigh()));
        lowest.setText(pitchToScale(getLow()));
    }

    public void releaseDispatcher() {
        if(dispatcher != null)
        {
            if(!dispatcher.isStopped())
                dispatcher.stop();
            dispatcher = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        releaseDispatcher();
    }

    // input : pitch
    // function : pitch -> scale
    // output : scale
    private String pitchToScale(float pitch) {
        String scale="?!?!";
        // 뭔가 녹음이 됐을때 일한다.
        if (pitch != -1){
            //피아노 기준 C1
            if(33<=pitch && pitch<65){
                scale="1";
                if(33<=pitch && pitch<35){
                    scale="C"+scale;
                }else if(35<=pitch && pitch<37){
                    scale="C#"+scale;
                }else if(37<=pitch && pitch<39){
                    scale="D"+scale;
                }else if(39<=pitch && pitch<41){
                    scale="D#"+scale;
                }else if(41<=pitch && pitch<44){
                    scale="E"+scale;
                }else if(44<=pitch && pitch<46){
                    scale="F"+scale;
                }else if(46<=pitch && pitch<49){
                    scale="F#"+scale;
                }else if(49<=pitch && pitch<52){
                    scale="G"+scale;
                }else if(52<=pitch && pitch<55){
                    scale="G#"+scale;
                }else if(55<=pitch && pitch<58){
                    scale="A"+scale;
                }else if(58<=pitch && pitch<62){
                    scale="A#"+scale;
                }else if(62<=pitch && pitch<65){
                    scale="B"+scale;
                }
            }
            //C2
            else if(65<=pitch && pitch<131){
                scale="2";
                if(65<=pitch && pitch<69){
                    scale="C"+scale;
                }else if(69<=pitch && pitch<73){
                    scale="C#"+scale;
                }else if(73<=pitch && pitch<78){
                    scale="D"+scale;
                }else if(78<=pitch && pitch<82){
                    scale="D#"+scale;
                }else if(82<=pitch && pitch<87){
                    scale="E"+scale;
                }else if(87<=pitch && pitch<92.5){
                    scale="F"+scale;
                }else if(92.5<=pitch && pitch<98){
                    scale="F#"+scale;
                }else if(98<=pitch && pitch<104){
                    scale="G"+scale;
                }else if(104<=pitch && pitch<110){
                    scale="G#"+scale;
                }else if(110<=pitch && pitch<116.5){
                    scale="A"+scale;
                }else if(116.5<=pitch && pitch<123.5){
                    scale="A#"+scale;
                }else if(123.5<=pitch && pitch<131){
                    scale="B"+scale;
                }
            }
            //C3
            else if(131<=pitch && pitch<262){
                scale="3";
                if(131<=pitch && pitch<139){
                    scale="C"+scale;
                }else if(139<=pitch && pitch<147){
                    scale="C#"+scale;
                }else if(147<=pitch && pitch<156){
                    scale="D"+scale;
                }else if(156<=pitch && pitch<165){
                    scale="D#"+scale;
                }else if(165<=pitch && pitch<175){
                    scale="E"+scale;
                }else if(175<=pitch && pitch<185){
                    scale="F"+scale;
                }else if(185<=pitch && pitch<196){
                    scale="F#"+scale;
                }else if(196<=pitch && pitch<208){
                    scale="G"+scale;
                }else if(208<=pitch && pitch<220){
                    scale="G#"+scale;
                }else if(220<=pitch && pitch<233){
                    scale="A"+scale;
                }else if(233<=pitch && pitch<247){
                    scale="A#"+scale;
                }else if(247<=pitch && pitch<262){
                    scale="B"+scale;
                }
            }
            //C4
            else if(262<=pitch && pitch<523){
                scale="4";
                if(262<=pitch && pitch<139){
                    scale="C"+scale;
                }else if(277<=pitch && pitch<294){
                    scale="C#"+scale;
                }else if(294<=pitch && pitch<311){
                    scale="D"+scale;
                }else if(311<=pitch && pitch<330){
                    scale="D#"+scale;
                }else if(330<=pitch && pitch<349){
                    scale="E"+scale;
                }else if(349<=pitch && pitch<370){
                    scale="F"+scale;
                }else if(370<=pitch && pitch<392){
                    scale="F#"+scale;
                }else if(392<=pitch && pitch<415){
                    scale="G"+scale;
                }else if(415<=pitch && pitch<440){
                    scale="G#"+scale;
                }else if(440<=pitch && pitch<466){
                    scale="A"+scale;
                }else if(466<=pitch && pitch<494){
                    scale="A#"+scale;
                }else if(494<=pitch && pitch<523){
                    scale="B"+scale;
                }
            }
            //C5
            else if(523<=pitch && pitch<1046.5){
                scale="5";
                if(523<=pitch && pitch<554){
                    scale="C"+scale;
                }else if(554<=pitch && pitch<587){
                    scale="C#"+scale;
                }else if(587<=pitch && pitch<622){
                    scale="D"+scale;
                }else if(622<=pitch && pitch<659){
                    scale="D#"+scale;
                }else if(659<=pitch && pitch<698.5){
                    scale="E"+scale;
                }else if(698.5<=pitch && pitch<740){
                    scale="F"+scale;
                }else if(740<=pitch && pitch<784){
                    scale="F#"+scale;
                }else if(784<=pitch && pitch<831){
                    scale="G"+scale;
                }else if(831<=pitch && pitch<880){
                    scale="G#"+scale;
                }else if(880<=pitch && pitch<932){
                    scale="A"+scale;
                }else if(932<=pitch && pitch<988){
                    scale="A#"+scale;
                }else if(988<=pitch && pitch<1046.5){
                    scale="B"+scale;
                }
            }
            //C6
            else if(1046.5<=pitch && pitch<2093){
                scale="6";
                if(1046.5<=pitch && pitch<1109){
                    scale="C"+scale;
                }else if(1109<=pitch && pitch<1175){
                    scale="C#"+scale;
                }else if(1175<=pitch && pitch<1244.5){
                    scale="D"+scale;
                }else if(1244.5<=pitch && pitch<1318.5){
                    scale="D#"+scale;
                }else if(1318.5<=pitch && pitch<1397){
                    scale="E"+scale;
                }else if(1397<=pitch && pitch<1480){
                    scale="F"+scale;
                }else if(1480<=pitch && pitch<1568){
                    scale="F#"+scale;
                }else if(1568<=pitch && pitch<1661){
                    scale="G"+scale;
                }else if(1661<=pitch && pitch<1760){
                    scale="G#"+scale;
                }else if(1760<=pitch && pitch<1865){
                    scale="A"+scale;
                }else if(1865<=pitch && pitch<1975.5){
                    scale="A#"+scale;
                }else if(1975.7<=pitch && pitch<2093){
                    scale="B"+scale;
                }
            }
            //C7
            else if(2093<=pitch && pitch<4186){
                scale="7";
                if(2093<=pitch && pitch<2217.5){
                    scale="C"+scale;
                }else if(2217.5<=pitch && pitch<2349){
                    scale="C#"+scale;
                }else if(2349<=pitch && pitch<2489){
                    scale="D"+scale;
                }else if(2489<=pitch && pitch<2637){
                    scale="D#"+scale;
                }else if(2637<=pitch && pitch<2794){
                    scale="E"+scale;
                }else if(2794<=pitch && pitch<2960){
                    scale="F"+scale;
                }else if(2960<=pitch && pitch<3136){
                    scale="F#"+scale;
                }else if(3136<=pitch && pitch<3322.5){
                    scale="G"+scale;
                }else if(3322.5<=pitch && pitch<3520){
                    scale="G#"+scale;
                }else if(3520<=pitch && pitch<3729){
                    scale="A"+scale;
                }else if(3729<=pitch && pitch<3951){
                    scale="A#"+scale;
                }else if(3951<=pitch && pitch<4186){
                    scale="B"+scale;
                }
            }
        }
        return scale;
    }

    // input : pitch
    // function : modify highest and lowest pitch
    // output : none
    public void calcHighLow(float pitchInHz){
        if(pitchInHz != -1){
            if(pitchInHz > getHigh()){
                setHigh(pitchInHz);
            }
            if(pitchInHz < getLow()){
                setLow(pitchInHz);
            }
        }
    }

}
