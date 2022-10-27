package com.example.slotgamelinttt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    ImageView slot1View, slot2View, slot3View;
    Button btnStart;
    boolean isPlay=false;

    SlotTask slottask1,slottask2,slottask3;
    ExecutorService execService1, execService2, execService3, execServicePool;

    private static int[] image = {R.drawable.slot1, R.drawable.slot2, R.drawable.slot3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slot1View = findViewById(R.id.id_Slot1);
        slot2View = findViewById(R.id.id_Slot2);
        slot3View = findViewById(R.id.id_Slot3);

        slot1View.setImageResource(R.drawable.ic_launcher_background);
        slot2View.setImageResource(R.drawable.ic_launcher_background);
        slot3View.setImageResource(R.drawable.ic_launcher_background);

        btnStart = findViewById(R.id.id_BtnPlay);
        btnStart.setOnClickListener(this);

        execService1 = Executors.newSingleThreadExecutor();
        execService2 = Executors.newSingleThreadExecutor();
        execService3 = Executors.newSingleThreadExecutor();
        execServicePool = Executors.newFixedThreadPool(3);

        slottask1 = new SlotTask(slot1View);
        slottask2 = new SlotTask(slot2View);
        slottask3 = new SlotTask(slot3View);
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==btnStart.getId())
        {
            if(!isPlay){

                slottask1.play = true;
                slottask2.play = true;
                slottask3.play = true;

                execServicePool.execute(slottask1);
                execServicePool.execute(slottask2);
                execServicePool.execute(slottask3);

                btnStart.setText("Stop");
                isPlay=!isPlay;
            }
            else {

                slottask1.play = false;
                slottask2.play = false;
                slottask3.play = false;
                btnStart.setText("Play");
                isPlay=!isPlay;

            }

        }

    }

    class SlotTask implements Runnable {
        ImageView slotImage;
        Random random = new Random();
        public  boolean play=true;
        int i;

        public SlotTask(ImageView slotImage) {
            this.slotImage = slotImage;
            i=0;
            play=true;

        }

        @Override
        public void run() {

            while (play) {
                i = random.nextInt(3);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        slotImage.setImageResource(image[i]);
                    }
                });

                try {
                    Thread.sleep(random.nextInt(500));}
                catch (InterruptedException e) {
                    e.printStackTrace(); }
            }

        }
    }


}