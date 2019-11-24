package com.example.project11;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
    public void onBindViewHolder(@NonNull final ViewHorlDer viewHorlDer, int i) {
        final String thang = arrayList.get(viewHorlDer.getAdapterPosition()).getThang();
        viewHorlDer.textView.setText(thang);
        viewHorlDer.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thang = addrecyclerview(viewHorlDer.getAdapterPosition());
                context.DataBaseSQL(thang);
                context.addthongtinthang();
            }
        });
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
    public String addrecyclerview(int i){
        String thang;
        switch (i){
            case 0:
                thang= "01/"+thoigiannam();
                break;
            case 1:
                thang= "02/"+thoigiannam();
                break;
            case 2:
                thang= "03/"+thoigiannam();
                break;
            case 3:
                thang= "04/"+thoigiannam();
                break;
            case 4:
                thang= "05/"+thoigiannam();
                break;
            case 5:
                thang= "06/"+thoigiannam();
                break;
            case 6:
                thang= "07/"+thoigiannam();
                break;
            case 7:
                thang= "08/"+thoigiannam();
                break;
            case 8:
                thang= "09/"+thoigiannam();
                break;
            case 9:
                thang= "10/"+thoigiannam();
                break;
            case 10:
                thang= "11/"+thoigiannam();
                break;
            default:
                thang= "12/"+thoigiannam();
                break;
        }
        return thang;
    }
    public String thoigiannam() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        String date = simpleDateFormat.format(new Date());
        return date;
    }
}

