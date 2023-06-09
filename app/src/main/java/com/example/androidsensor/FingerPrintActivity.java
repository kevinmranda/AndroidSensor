package com.example.androidsensor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;
import androidx.core.os.CancellationSignal;

public class FingerPrintActivity extends AppCompatActivity {

    private FingerprintManagerCompat fingerprintManager;
    private TextView textView;
    private Button auth, gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_print);

        textView = findViewById(R.id.text);
        auth = findViewById(R.id.auth);
        gps = findViewById(R.id.gps);

        fingerprintManager = FingerprintManagerCompat.from(this);

        auth.setOnClickListener(view -> authenticate());

        gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FingerPrintActivity.this, GPSActivity.class);
                startActivity(i);
            }
        });
    }

    private void authenticate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Fingerprint authentication permission not granted.", Toast.LENGTH_SHORT).show();
            return;
        }

        fingerprintManager.authenticate(null, 0, new CancellationSignal(),
                new FingerprintManagerCompat.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                        runOnUiThread(() -> textView.setText("Access Granted"));
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        Toast.makeText(FingerPrintActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                }, null);
    }
}
