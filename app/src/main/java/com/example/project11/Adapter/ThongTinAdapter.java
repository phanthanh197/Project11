package com.example.project11.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project11.Activity.HideCodeActivity;
import com.example.project11.Activity.MainActivity;
import com.example.project11.R;
import com.example.project11.setget.ThongTin;

import java.io.File;
import java.util.ArrayList;

public class ThongTinAdapter extends RecyclerView.Adapter<ThongTinAdapter.ThongTinViewHolder> {
    ArrayList<ThongTin> arrayList;
    MainActivity context;
    Dialog dialog;

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
        final String a = arrayList.get(i).getPhotopath();
        File f = new File(a);
        final Uri uri = Uri.fromFile(f);
        if (a.equals("")) {
            Glide.with(context)
                    .load(R.drawable.anhdep)
                    .centerCrop()
                    .into(thongTinViewHolder.anh);
        } else {
            Glide.with(context)
                    .load(uri)
                    .centerCrop()
                    .into(thongTinViewHolder.anh);
        }
        thongTinViewHolder.thongtin.setText(k);
        thongTinViewHolder.tenchude.setText(s);
        final String ten = context.tenbang1;
        thongTinViewHolder.anh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HideCodeActivity.class);
                intent.putExtra("1",uri.getPath());
                intent.putExtra("2",thongTinViewHolder.tenchude.getText());
                intent.putExtra("3",a);
                intent.putExtra("4",arrayList.get(thongTinViewHolder.getAdapterPosition()).getId());
                intent.putExtra("5",ten);
                context.startActivity(intent);
            }
        });
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
                                dialogsuatieude(arrayList.get(a).getId(), a);
                                break;
                            case R.id.xoa_chu_de:
                                delete(arrayList.get(a).getId(), a);
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
    private void dialogsuatieude(final int id, final int i) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_suatieude);
        final EditText edtText = (EditText) dialog.findViewById(R.id.dialog_edt_noidung_tieude);
        Button buttondongy = (Button) dialog.findViewById(R.id.dilog_btn_doi_tieu_de);
        final Button buttonkodongy = (Button) dialog.findViewById(R.id.dilog_btn_huy_doi_tieu_de);
        buttondongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = edtText.getText().toString();
                if (!t.equals("")) {
                    context.database.QueryData("UPDATE '" + context.tenbang1 + "' SET Tenchude = '" + t + "' WHERE Id = '" + id + "'");
                    if (context.tenbang1.equals(context.bangthanghientai1)) {
                        arrayList.get(i).setTen(t);
                        notifyItemChanged(i, null);
                    }
                    dialog.cancel();
                }
                dialog.cancel();
            }
        });
        buttonkodongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
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
    private void delete(final int id, final int i) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage("ban co dong y xoa chu de nay?");
        alertDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.database.QueryData("DELETE FROM '" + context.tenbang1 + "' WHERE Id='" + id + "'");
                arrayList.remove(i);
                notifyItemRemoved(i);
            }
        });
        alertDialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
}
