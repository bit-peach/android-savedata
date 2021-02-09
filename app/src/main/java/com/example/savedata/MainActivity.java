package com.example.savedata;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.example.savedata.databinding.ActivityMainBinding;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private final String filename = "test.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLoad.setOnClickListener(v -> load(new File(getFilesDir(), filename)));
        binding.btnSave.setOnClickListener(v -> save(new File(getFilesDir(), filename)));

        if(isExternalStorageMounted()) {
            binding.btnLoad.setOnClickListener(v -> load(new File(getExternalFilesDir(null), filename)));
            binding.btnSave.setOnClickListener(v -> save(new File(getExternalFilesDir(null), filename)));
        } else {
            binding.btnLoad.setOnClickListener(v -> Toast.makeText(this, "External Stroage is not mounted.", Toast.LENGTH_LONG).show());
            binding.btnSave.setOnClickListener(v -> Toast.makeText(this, "External Stroage is not mounted.", Toast.LENGTH_LONG).show());
        }
    }

    void load(File file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            binding.editText.setText(new String(data));
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void save(File file) {
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(binding.editText.getText().toString().getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isExternalStorageMounted() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
}
