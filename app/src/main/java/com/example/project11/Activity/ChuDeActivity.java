package com.example.project11.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.project11.R;

public class ChuDeActivity extends AppCompatActivity {
    MainActivity context;
    TextView textView;
    EditText editText;
    String noidung,tenbang1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chu_de);
        ImageView imageView = (ImageView) findViewById(R.id.imageView3);
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("1");
        String value2 = intent.getStringExtra("2");
        String value3 = intent.getStringExtra("3");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(null);
        textView = findViewById(R.id.textView6);
        editText = findViewById(R.id.editText2);
        int value4 = intent.getIntExtra("4",0);
        tenbang1 = intent.getStringExtra("5");
        /*Cursor cursor = context.database.getData("SELECT * FROM '" + tenbang1 + "' WHERE Id = '"+value4+"'");
        noidung = cursor.getString(3);
        textView.setText(noidung);
        Toast.makeText(this,""+value4 , Toast.LENGTH_LONG).show();*/
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.menu2:
                textView.setVisibility(View.GONE);
                editText.setVisibility(View.VISIBLE);
                break;
            case R.id.menu3:
                AlertDialog.Builder a = new AlertDialog.Builder(this);
                a.setMessage("bạn muốn lưu");
                a.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = getIntent();
                        int value4 = intent.getIntExtra("4",0);
                        String value5 = intent.getStringExtra("5");
                        //context.database.QueryData("UPDATE '" + value5 + "' SET Noidung = '" + editText.getText().toString() + "' WHERE Id = '"+ value4 +"'");
                        textView.setText(editText.getText().toString());
                        editText.setInputType(InputType.TYPE_NULL);
                        textView.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);
                    }
                });
                a.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                a.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
