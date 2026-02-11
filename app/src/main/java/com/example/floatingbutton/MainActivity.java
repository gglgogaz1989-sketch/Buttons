package com.example.floatingbutton;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);

        // Проверка разрешения SYSTEM_ALERT_WINDOW
        if(!Settings.canDrawOverlays(this)){
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivity(intent);
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, FloatingButtonService.class);
                intent.putExtra("buttonText", "Jump");
                intent.putExtra("keyCode", "SPACE");
                intent.putExtra("holdEnabled", true);
                intent.putExtra("repeatInterval", 500);
                startService(intent);
            }
        });
    }
}
