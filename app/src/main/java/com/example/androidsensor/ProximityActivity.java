package com.example.androidsensor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProximityActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor proximitySensor;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proximity);

        TextView textViewProximity = findViewById(R.id.text_view_proximity);
        Button fingerPrint = findViewById(R.id.finger_print);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        }

        fingerPrint.setOnClickListener(v -> {
            Intent i = new Intent(ProximityActivity.this, FingerPrintActivity.class);
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (proximitySensor != null) {
            sensorManager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];

        if (distance < proximitySensor.getMaximumRange()) {
            // Proximity sensor is triggered (object detected nearby)
            showToast("Hello World!");
        } else {
            // Proximity sensor is not triggered (object is far)
            showToast("No object detected.");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used in this example
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
