package com.example.project11.activitys;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.project11.R;
import com.example.project11.text.AsyncTaskCallback.TextEncodingCallback;
import com.example.project11.text.ImageSteganography;
import com.example.project11.text.TextEncoding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HideCodeActivity extends AppCompatActivity implements TextEncodingCallback {
    MainActivity context;
    private ImageSteganography imageSteganography;
    private EditText message;
    private EditText secret_key;
    private TextEncoding textEncoding;
    private TextView whether_encoded;
    private ProgressDialog save;
    private ImageView imageViewCode;
    private String tenanh, imgcode;
    private Bitmap original_image;
    private Bitmap encoded_image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hide_code);
        whether_encoded = findViewById(R.id.whether_encoded);
        imageViewCode = (ImageView) findViewById(R.id.imageViewCode);
        message = findViewById(R.id.message_hide_code);
        secret_key = findViewById(R.id.secret_key);
        Button encode_button = findViewById(R.id.encode_button);
        Button save_image_button = findViewById(R.id.save_image_button);
        final Intent intent = getIntent();
        imgcode = intent.getStringExtra("1");
        String value2 = intent.getStringExtra("2");
        String value3 = intent.getStringExtra("3");
        int value4 = intent.getIntExtra("4", 0);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
        /*Cursor cursor = context.database.getData("SELECT * FROM '" + tenbang1 + "' WHERE Id = '"+value4+"'");
        noidung = cursor.getString(3);
        textView.setText(noidung);
        Toast.makeText(this,""+value4 , Toast.LENGTH_LONG).show();*/
        if (value3.equals("")) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.anhdep)
                    .centerCrop()
                    .into(imageViewCode);
        } else {
            Glide.with(getApplicationContext())
                    .load(imgcode)
                    .centerCrop()
                    .into(imageViewCode);
        }

        checkAndRequestPermissions();
        //Encode Button
        encode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().toString().equals("")) {
                    Toast.makeText(HideCodeActivity.this, "bạn chưa nhập tin nhắn", Toast.LENGTH_SHORT).show();
                } else {

                    if (imgcode != null) {
                        if (message.getText() != null) {

                            //tạo đối tượng ImageSteganography
                            //mã hóa tin nhắn và tạo khung bitmap
                            imageSteganography = new ImageSteganography(message.getText().toString(),
                                    secret_key.getText().toString(), (((BitmapDrawable) imageViewCode.getDrawable()).getBitmap()));
                            //tạo đối tượng TextEncoding
                            textEncoding = new TextEncoding(HideCodeActivity.this, HideCodeActivity.this);
                            //Executing the encoding
                            textEncoding.execute(imageSteganography);
                        }
                    } else {
                        Toast.makeText(context, "bạn chưa chọn ảnh", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Save image button
        save_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Bitmap imgToSave = encoded_image;
                Thread PerformEncoding = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveToInternalStorage(imgToSave);
                    }
                });
                save = new ProgressDialog(HideCodeActivity.this);
                save.setMessage("Saving, Please Wait...");
                save.setTitle("Saving Image");
                save.setIndeterminate(false);
                save.setCancelable(false);
                save.show();
                PerformEncoding.start();
            }
        });
    }

    @Override
    public void onCompleteTextEncoding(ImageSteganography result) {
        if (result != null && result.isEncoded()) {
            encoded_image = result.getEncoded_image();
            whether_encoded.setText("");
            imageViewCode.setImageBitmap(encoded_image);
        }
    }

    private void saveToInternalStorage(Bitmap bitmapImage) {
        tenanh = thoigian();
        OutputStream fOut;
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOWNLOADS), "Encoded" + tenanh + ".PNG"); // tạo file lưu ,
        try {
            fOut = new FileOutputStream(file);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fOut); // saving the Bitmap to a file
            fOut.flush();
            fOut.close();
            whether_encoded.post(new Runnable() {
                @Override
                public void run() {
                    save.dismiss();
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu2:
                break;
            case R.id.menu3:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkAndRequestPermissions() {
        int permissionWriteStorage = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int ReadPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        List<String> listPermissionsNeeded = new ArrayList<>();
        if (ReadPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissionWriteStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[0]), 1);
        }
    }

    public String thoigian() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm-ss");
        String gio = simpleDateFormat.format(new Date());
        return gio;
    }
}
