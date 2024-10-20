package ma.ensa.mobile.devkit;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SplashActivity extends AppCompatActivity {

    private TextView nameApp;
    private CircleImageView photo1 ,photo2, photo3, photo4, photo5, photo6, photo7, photo8, photo9, photo10, photo11, photo12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);

        nameApp = findViewById(R.id.nameApp);
        addGlowEffect(nameApp);



        photo1 = findViewById(R.id.photo1);
        photo2 = findViewById(R.id.photo2);
        photo3 = findViewById(R.id.photo3);
        photo4 = findViewById(R.id.photo4);
        photo5 = findViewById(R.id.photo5);
        photo6 = findViewById(R.id.photo6);
        photo7 = findViewById(R.id.photo7);
        photo8 = findViewById(R.id.photo8);
        photo9 = findViewById(R.id.photo9);
        photo10 = findViewById(R.id.photo10);
        photo11 = findViewById(R.id.photo11);
        photo12 = findViewById(R.id.photo12);

        // Add animations to the photos
        animatePhoto(photo1);
        animatePhoto(photo2);
        animatePhoto(photo3);
        animatePhoto(photo4);
        animatePhoto(photo5);
        animatePhoto(photo6);
        animatePhoto(photo7);
        animatePhoto(photo8);
        animatePhoto(photo9);
        animatePhoto(photo10);
        animatePhoto(photo11);
        animatePhoto(photo12);





        Thread t = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent intent = new Intent(SplashActivity.this , MainActivity.class);
                    startActivity(intent);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        t.start();


    }
    private void addGlowEffect(View view) {
        ObjectAnimator glowAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1f, 0.5f);
        glowAnimator.setDuration(1500);
        glowAnimator.setRepeatCount(ValueAnimator.INFINITE);
        glowAnimator.setRepeatMode(ValueAnimator.REVERSE);
        glowAnimator.start();
    }

    private void animatePhoto(View photo) {
        // Rotation Animation: Small rotations back and forth
        ObjectAnimator rotateAnimator = ObjectAnimator.ofFloat(photo, "rotation", -10f, 10f);
        rotateAnimator.setDuration(800);
        rotateAnimator.setRepeatCount(ValueAnimator.INFINITE);
        rotateAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Translation Animation: Moves slightly up and down
        ObjectAnimator translateYAnimator = ObjectAnimator.ofFloat(photo, "translationY", -10f, 10f);
        translateYAnimator.setDuration(1000);
        translateYAnimator.setRepeatCount(ValueAnimator.INFINITE);
        translateYAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Scale Animation: Slightly scales the photo up and down
        ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(photo, "scaleX", 0.95f, 1.05f);
        scaleXAnimator.setDuration(1000);
        scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleXAnimator.setRepeatMode(ValueAnimator.REVERSE);

        ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(photo, "scaleY", 0.95f, 1.05f);
        scaleYAnimator.setDuration(1000);
        scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
        scaleYAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Start all animations together
        rotateAnimator.start();
        translateYAnimator.start();
        scaleXAnimator.start();
        scaleYAnimator.start();
    }

}