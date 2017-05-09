package com.hieptran.quanlythuvien.QuanTriVien;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import es.dmoral.toasty.Toasty;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public static final String MA_THE = "mathe";
    private ZXingScannerView mScannerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(Scan.this);
        setContentView(mScannerView);
        setTitle("Quét Mã");
        mScannerView.setResultHandler(Scan.this);
        mScannerView.startCamera();


    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        mScannerView.setAutoFocus(true);
        mScannerView.setAutoFocus(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mScannerView.stopCamera();
        finish();
    }


    @Override
    public void handleResult(Result result) {
        Toasty.success(this, result.getText(), Toast.LENGTH_SHORT).show();
        String mScan = result.getText().toString();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(MA_THE, mScan);
        setResult(RESULT_OK, returnIntent);
        mScannerView.stopCamera();
        finish();
    }
}
