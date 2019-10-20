package com.example.project11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ArrayList<Thang> arrayList;
    ArrayList<ThongTin> arrayList2;
    RecyclerView recyclerViewthang,recyclerView;
    TextView textnam;
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
