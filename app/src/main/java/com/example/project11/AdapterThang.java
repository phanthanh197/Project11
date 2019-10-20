package com.example.project11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterThang extends RecyclerView.Adapter<AdapterThang.ViewHorlDer> {
    ArrayList<Thang> arrayList;
    MainActivity context;

    public AdapterThang(ArrayList<Thang> arrayList, MainActivity context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHorlDer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = (View) layoutInflater.inflate(R.layout.thang, viewGroup, false);
        return new ViewHorlDer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHorlDer viewHorlDer, int i) {
        final String thang = arrayList.get(viewHorlDer.getAdapterPosition()).getThang();
        viewHorlDer.textView.setText(thang);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHorlDer extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout linearLayout;
        public ViewHorlDer(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_thang);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.relative_thang);
        }
    }
}

