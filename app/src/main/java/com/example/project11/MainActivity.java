package com.example.project11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ArrayList<Thang> arrayList;
    ArrayList<ThongTin> arrayList2;
    RecyclerView recyclerViewthang,recyclerView;
    TextView textnam;
    ImageView imgtheme,btnthemchude, btntaichude;;
    ThongTinAdapter thongTinBeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        textnam = (TextView) findViewById(R.id.text_nam);
        textnam.setText(thoigianngay());
        btnthemchude = (ImageView) findViewById(R.id.btn_them_chu_de);
        btntaichude = (ImageView) findViewById(R.id.btn_tai_chu_de);
        btntaichude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogtaichude();
            }
        });
        btnthemchude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthemchude();
            }
        });
        addthang();
        recyclerthang();
        addthongtinthangnay();
        recyclerthongtin();

    }
    private void recyclerthang() {
        recyclerViewthang = (RecyclerView) findViewById(R.id.recycler_thoigian);
        recyclerViewthang.setHasFixedSize(true);
        AdapterThang adapterThang = new AdapterThang(arrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewthang.setLayoutManager(linearLayoutManager);
        recyclerViewthang.setAdapter(adapterThang);
    }
    private void addthang() {
        arrayList = new ArrayList<>();
        arrayList.add(new Thang("January"));
        arrayList.add(new Thang("February"));
        arrayList.add(new Thang("March"));
        arrayList.add(new Thang("April"));
        arrayList.add(new Thang("May"));
        arrayList.add(new Thang("June"));
        arrayList.add(new Thang("Juny"));
        arrayList.add(new Thang("August"));
        arrayList.add(new Thang("September"));
        arrayList.add(new Thang("October"));
        arrayList.add(new Thang("November"));
        arrayList.add(new Thang("December"));
    }
    private void recyclerthongtin() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler_thongtin);
        recyclerView.setHasFixedSize(true);
        thongTinBeAdapter = new ThongTinAdapter(arrayList2, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(thongTinBeAdapter);
    }
    private void addthongtinthangnay() {
        arrayList2 = new ArrayList<ThongTin>();
        arrayList2.add(new ThongTin( "chu de","noi dung", thoigian()));
        arrayList2.add(new ThongTin( "chu de","noi dung", thoigian()));
        arrayList2.add(new ThongTin( "chu de","noi dung", thoigian()));
    }
    private void dialogthemchude() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_themchude);
        dialog.setCanceledOnTouchOutside(false);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        final ImageButton imgintentcamera, imgdowloatcamera;
        final EditText edttenchude;
        Button btnyes, btnno;

        imgintentcamera = (ImageButton) dialog.findViewById(R.id.img_btn_camera);
        imgdowloatcamera = (ImageButton) dialog.findViewById(R.id.img_btn_dowloat);
        edttenchude = (EditText) dialog.findViewById(R.id.edt_name_theme);
        btnno = (Button) dialog.findViewById(R.id.btn_no);
        btnyes = (Button) dialog.findViewById(R.id.btn_yes);
        imgtheme = (ImageView) dialog.findViewById(R.id.img_theme);
        imgintentcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        imgdowloatcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
    private void dialogtaichude(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_taianh);
        LinearLayout linearLayout1;
        final ImageView imageView1;
        linearLayout1 = (LinearLayout) dialog.findViewById(R.id.layout1);
        imageView1 = (ImageView) dialog.findViewById(R.id.imageView_box);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        dialog.show();
    }
    public String thoigianngay() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(new Date());
        return date;
    }
    public String thoigian(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm-ss");
        String gio = simpleDateFormat.format(new Date());
        return gio;
    }
}
