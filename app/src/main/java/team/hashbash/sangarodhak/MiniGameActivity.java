package team.hashbash.sangarodhak;

import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import processing.android.CompatUtils;
import processing.android.PFragment;
import processing.core.PApplet;

public class MiniGameActivity extends AppCompatActivity {
    private PApplet sketch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = new FrameLayout(this);
        frame.setId(CompatUtils.getUniqueViewId());
        setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        String gameToLoad = getPackageName() + ".MiniGames." + getIntent().getStringExtra("extra");

        try {
            Class<?> clazz = Class.forName(gameToLoad);
            sketch = (PApplet) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        try {
            Class<?> clazz = Class.forName(gameToLoad);
            sketch = (PApplet) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        PFragment fragment = new PFragment(sketch);
        fragment.setView(frame, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (sketch != null) {
            sketch.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (sketch != null) {
            sketch.onNewIntent(intent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (sketch != null) {
            sketch.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onBackPressed() {
        if (sketch != null) {
            sketch.onBackPressed();
        }
    }
}
