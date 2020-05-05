package com.example.project11.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project11.R;
import com.example.project11.Text.AsyncTaskCallback.TextDecodingCallback;
import com.example.project11.Text.ImageSteganography;
import com.example.project11.Text.TextDecoding;

import org.w3c.dom.Text;

import java.io.IOException;


public class UnlockActivity extends AppCompatActivity implements TextDecodingCallback {

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "Decode Class";
    //Initializing the UI components

    private ImageView imageView;
    private TextView message;
    private EditText secret_key;
    private Uri filepath;
    //Bitmap
    private Bitmap original_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock);



        imageView = findViewById(R.id.imageview);

        message = findViewById(R.id.message);
        secret_key = findViewById(R.id.secret_key);

        Button choose_image_button = findViewById(R.id.choose_image_button);
        Button decode_button = findViewById(R.id.decode_button);

        //Choose Image Button
        choose_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageChooser();
            }
        });

        //Decode Button
        decode_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (filepath != null) {

                    //Making the ImageSteganography object
                    ImageSteganography imageSteganography = new ImageSteganography(secret_key.getText().toString(),
                            original_image);

                    //Making the TextDecoding object
                    TextDecoding textDecoding = new TextDecoding(UnlockActivity.this,  UnlockActivity.this);

                    //Execute Task
                    textDecoding.execute(imageSteganography);
                }
            }
        });


    }

    private void ImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Image set to imageView
        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filepath = data.getData();
            try {
                original_image = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);

                imageView.setImageBitmap(original_image);
            } catch (IOException e) {
                Log.d(TAG, "Error : " + e);
            }
        }

    }



    @Override
    public void onCompleteTextEncoding(ImageSteganography result) {
        //By the end of textDecoding

        if (result != null) {
            if (!result.isDecoded())
                Toast.makeText(this, "không tìm thấy tin nhắn", Toast.LENGTH_SHORT).show();
            else {
                if (!result.isSecretKeyWrong()) {
                    Toast.makeText(this, "đã giải mã", Toast.LENGTH_SHORT).show();
                    message.setText("" + result.getMessage());
                } else {
                    Toast.makeText(this, "khóa sai", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(this, "bạn chưa chọn đúng hình ảnh", Toast.LENGTH_SHORT).show();
        }
    }
}
