package com.example.project11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ArrayList<Thang> arrayList;
    ArrayList<ThongTin> arrayList2;
    RecyclerView recyclerViewthang, recyclerView;
    TextView textnam;
    ImageView imgtheme, btnthemchude, btntaichude;
    String tenchude="";
    ThongTinAdapter thongTinBeAdapter;
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
        recyclerthongtin();
        addthongtinthangnay();
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
        arrayList2 = new ArrayList<ThongTin>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_thongtin);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_thongtin);
        recyclerView.setHasFixedSize(true);
        thongTinBeAdapter = new ThongTinAdapter(arrayList2, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(thongTinBeAdapter);
    }

    private void addthongtinthangnay() {
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
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_FILE);
            }
        });
        btnyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tenchude = edttenchude.getText().toString();
                if (!tenchude.equals("")) {
                    thongTinBeAdapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);
                    arrayList2.add(0, new ThongTin(1, "", "noi dung", photopath, thoigian()));
                    thongTinBeAdapter.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);
                    addthongtinthangnay();
                    dialog.cancel();
                } else {
                    Toast.makeText(MainActivity.this, "ban chua nhap ten chu de", Toast.LENGTH_SHORT).show();
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
            //Toast.makeText(MainActivity.this, "" + photopath, Toast.LENGTH_SHORT).show();

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Context context, Uri uri) {
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

    public String thoigian() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh-mm-ss");
        String gio = simpleDateFormat.format(new Date());
        return gio;
    }
}
