package com.example.project11;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ChuDeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_de);
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        TextView textView = (TextView) findViewById(R.id.textView6);
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("1");
        String value2 = intent.getStringExtra("2");
        String value3 = intent.getStringExtra("3");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
        EditText editText = findViewById(R.id.editText2);
        textView.setText(value2);
        if(value3.equals("")) {
            Glide.with(getApplicationContext())
                    .load(R.drawable.anhdep)
                    .centerCrop()
                    .into(imageView);
        }
        else{
            Glide.with(getApplicationContext())
                    .load(value1)
                    .centerCrop()
                    .into(imageView);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
