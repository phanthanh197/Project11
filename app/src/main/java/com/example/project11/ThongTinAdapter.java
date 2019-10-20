package com.example.project11;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class ThongTinAdapter extends RecyclerView.Adapter<ThongTinAdapter.ThongTinViewHolder> {
    ArrayList<ThongTin> arrayList;
    MainActivity context;

    public ThongTinAdapter(ArrayList<ThongTin> arrayList, MainActivity context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ThongTinViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.recycler_thongtinbe, viewGroup, false);
        return new ThongTinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ThongTinViewHolder thongTinViewHolder, final int i) {
        String k = arrayList.get(i).getThongtin();
        String s = arrayList.get(i).getTen();
        Glide.with(context)
                .load(R.drawable.anhdep)
                .centerCrop()
                .into(thongTinViewHolder.anh);
        thongTinViewHolder.thongtin.setText(k);
        thongTinViewHolder.tenchude.setText(s);
        thongTinViewHolder.menuchude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, thongTinViewHolder.menuchude);
                popupMenu.getMenuInflater().inflate(R.menu.menu_chude, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int a = thongTinViewHolder.getAdapterPosition();
                        switch (item.getItemId()) {
                            case R.id.sua_tieu_de:
                                break;
                           case R.id.doi_anh_chinh:
                                break;
                            case R.id.xoa_chu_de:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ThongTinViewHolder extends RecyclerView.ViewHolder {
        ImageView anh, menuchude;
        TextView thongtin, tenchude;

        public ThongTinViewHolder(@NonNull View itemView) {
            super(itemView);
            anh = (ImageView) itemView.findViewById(R.id.anh_be);
            menuchude = (ImageView) itemView.findViewById(R.id.menu_chude);
            thongtin = (TextView) itemView.findViewById(R.id.noi_dung_chinh);
            tenchude = (TextView) itemView.findViewById(R.id.ten_chu_de);
        }
    }
}
