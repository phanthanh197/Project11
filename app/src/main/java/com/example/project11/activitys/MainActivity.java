package com.example.project11.activitys;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project11.adapters.AdapterMonth;
import com.example.project11.adapters.Database;
import com.example.project11.adapters.InformationAdapter;
import com.example.project11.R;
import com.example.project11.data.Month;
import com.example.project11.data.Info;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public Database database;
    ArrayList<Month> arrayList;
    ArrayList<Info> arrayList2;
    RecyclerView recyclerViewthang, recyclerView;
    TextView textnam;
    ImageView imgtheme, btnthemchude, btntaichude,imagesetting,btngiaima;
    public String tenbang1, bangthanghientai1,tenchude="";
    InformationAdapter thongTinBeAdapter;
    String photopath = "";
    final int REQUEST_CODE_CAMERA = 0, REQUEST_CODE_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        textnam = (TextView) findViewById(R.id.text_nam);
        textnam.setText(thoigianngay());
        imagesetting = (ImageView) findViewById(R.id.image_setting);
        btnthemchude = (ImageView) findViewById(R.id.btn_them_chu_de);
        btntaichude = (ImageView) findViewById(R.id.btn_tai_chu_de);
        btngiaima = (ImageView) findViewById(R.id.image_unlock);
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
        imagesetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        btngiaima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UnlockActivity.class));
            }
        });
        DataBaseSQL(thoigianthang());
        addthang();
        recyclerthang();
        recyclerthongtin();
        addthongtinthangnay();
    }

    private void recyclerthang() {
        recyclerViewthang = (RecyclerView) findViewById(R.id.recycler_thoigian);
        recyclerViewthang.setHasFixedSize(true);
        AdapterMonth adapterThang = new AdapterMonth(arrayList, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewthang.setLayoutManager(linearLayoutManager);
        recyclerViewthang.setAdapter(adapterThang);
    }

    private void addthang() {//thêm các tháng vào recyclerview
        arrayList = new ArrayList<>();
        arrayList.add(new Month("January"));
        arrayList.add(new Month("February"));
        arrayList.add(new Month("March"));
        arrayList.add(new Month("April"));
        arrayList.add(new Month("May"));
        arrayList.add(new Month("June"));
        arrayList.add(new Month("Juny"));
        arrayList.add(new Month("August"));
        arrayList.add(new Month("September"));
        arrayList.add(new Month("October"));
        arrayList.add(new Month("November"));
        arrayList.add(new Month("December"));
    }

    private void recyclerthongtin() {
        arrayList2 = new ArrayList<Info>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_thongtin);
        recyclerView.setHasFixedSize(true);
        thongTinBeAdapter = new InformationAdapter(arrayList2, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(thongTinBeAdapter);
    }

    private void addthongtinthangnay() {//lấy dữ liệu từng tháng
        arrayList2.clear();
        Cursor cursor = database.getData("SELECT * FROM '" + bangthanghientai1 + "'");
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String diachianh = cursor.getString(2);
            String noidung = cursor.getString(3);
            arrayList2.add(0, new Info(id, ten, noidung, diachianh, thoigian()));
        }
        thongTinBeAdapter.notifyDataSetChanged();
    }

    public void addthongtinthang() {
        arrayList2.clear();
        Cursor cursor = database.getData("SELECT * FROM '" + tenbang1 + "'");
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String diachianh = cursor.getString(2);
            String noidung = cursor.getString(3);
            arrayList2.add(0, new Info(id, ten, noidung, diachianh, thoigian()));
        }
        thongTinBeAdapter.notifyDataSetChanged();
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
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    File imagefile = null;
                    try {
                        imagefile = fileimage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (null != imagefile) {
                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.example.android.fileprovider", imagefile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, REQUEST_CODE_CAMERA);
                    }
                }
            }
        });

        imgdowloatcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FILE);
            }
        });
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenchude = edttenchude.getText().toString();
                if (!tenchude.equals("")) {
                    String noidung="noi dung";
                    database.QueryData("INSERT INTO '" + bangthanghientai1 + "' VALUES(null, '" + tenchude + "', '" + photopath + "','"+noidung+"')");
                    photopath = "";
                    if (tenbang1.equals(bangthanghientai1)) {
                        Cursor cursor = database.getData("SELECT * FROM '" + tenbang1 + "'");
                        cursor.moveToFirst();
                        while (cursor.moveToNext()) {
                            if (cursor.isLast()) {
                                int id = cursor.getInt(0);
                                String ten = cursor.getString(1);
                                String diachianh = cursor.getString(2);
                                noidung = cursor.getString(3);
                                arrayList2.add(0, new Info(id, ten, noidung, diachianh, thoigian()));
                                thongTinBeAdapter.notifyItemInserted(0);
                                recyclerView.scrollToPosition(0);
                            }
                        }
                    } else {
                        addthongtinthangnay();
                    }
                    dialog.cancel();
                } else {
                    Toast.makeText(MainActivity.this, "bạn chưa nhập tên chủ đề", Toast.LENGTH_SHORT).show();
                }
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == RESULT_OK && data != null) {
            File file = new File(photopath);
            Uri uri = Uri.fromFile(file);
            Glide.with(getApplicationContext())
                    .load(uri)
                    .centerCrop()
                    .into(imgtheme);
        }
        if (requestCode == REQUEST_CODE_FILE && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();
            Glide.with(getApplicationContext())
                    .load(uri)
                    .centerCrop()
                    .into(imgtheme);
            photopath = getRealPathFromURI(MainActivity.this, uri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Context context, Uri uri) {
        //lấy địa chỉ từ file ảnh của bộ nhớ điên thoại
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    private File fileimage() throws IOException {
        // tao anh ten thanh.png trong file save_anh
        String root = Environment.getExternalStorageDirectory().toString();
        File file1 = new File(root + "/save_anh");
        if (!file1.exists()) {
            file1.mkdirs();
        }
        File file = File.createTempFile("image_" + thoigian(), ".jpg", file1);
        photopath = file.getAbsolutePath();
        return file;
    }

    private void dialogtaichude() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_taianh);
        LinearLayout linearLayout1,linearLayout2;
        final ImageView imageView1;
        linearLayout1 = (LinearLayout) dialog.findViewById(R.id.layout1);
        linearLayout2 = (LinearLayout) dialog.findViewById(R.id.layout2);
        imageView1 = (ImageView) dialog.findViewById(R.id.imageView_box);
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppBoxActivity.class);
                startActivity(intent);
            }
        });
        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GmailActivity.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
    public void DataBaseSQL(String z) {
        tenbang1 = "chude" + z;
        bangthanghientai1 = "chude" + thoigianthang();
        database = new Database(this, "Chude.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS '" + tenbang1 + "' (Id INTEGER PRIMARY KEY AUTOINCREMENT, Tenchude VARCHAR(200), Diachianh VARCHAR(100), Noidung VARCHAR(1000))");
        database.QueryData("CREATE TABLE IF NOT EXISTS '" + bangthanghientai1 + "' (Id INTEGER PRIMARY KEY AUTOINCREMENT, Tenchude VARCHAR(200), Diachianh VARCHAR(100), Noidung VARCHAR(1000))");
    }

    public String thoigianngay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    public String thoigianthang() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/yyyy");
        String date = simpleDateFormat.format(new Date());
        return date;
    }

    public String thoigian() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm-ss");
        String gio = simpleDateFormat.format(new Date());
        return gio;
    }
}
