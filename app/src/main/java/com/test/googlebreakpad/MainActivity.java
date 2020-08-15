package com.test.googlebreakpad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;

import com.test.breakpad.BreakpadDumper;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 100;

    static {
        System.loadLibrary("crash-lib");
    }

    private File mExternalReportPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            initExternalReportPath();
        }

        initBreakPad();
        crashDump();
    }

    private void initBreakPad() {
        if (mExternalReportPath == null) {
            mExternalReportPath = new File(getFilesDir(), "crashDump");
            if (!mExternalReportPath.exists()) {
                mExternalReportPath.mkdirs();
            }
        }
        BreakpadDumper.initBreakpad(mExternalReportPath.getAbsolutePath());
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initExternalReportPath();
    }

    private void initExternalReportPath() {
        mExternalReportPath = new File(Environment.getExternalStorageDirectory(), "crashDump");
        if (!mExternalReportPath.exists()) {
            mExternalReportPath.mkdirs();
        }
    }

    public native void crashDump();
}