package dev.adi.testapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class QRActivity extends AppCompatActivity {

    private TextView tvQrOutput;
    private SurfaceView cameraView;
    private QREader qrReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        getSupportActionBar().setTitle("QR Scanner");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvQrOutput = (TextView) findViewById(R.id.tv_qr_output);
        cameraView = (SurfaceView) findViewById(R.id.camera_view);

        qrReader = new QREader.Builder(this, cameraView, new QRDataListener() {
            @Override
            public void onDetected(final String data) {
                tvQrOutput.post(new Runnable() {
                    @Override
                    public void run() {
                        tvQrOutput.setText(data);

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(data));
                        startActivity(i);
                    }
                });
            }
        }).facing(QREader.BACK_CAM)
          .enableAutofocus(true)
          .height(cameraView.getHeight())
          .width(cameraView.getWidth())
          .build();
    }

    @Override
    protected void onPause() {
        super.onPause();
        qrReader.releaseAndCleanup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        qrReader.initAndStart(cameraView);
    }
}
