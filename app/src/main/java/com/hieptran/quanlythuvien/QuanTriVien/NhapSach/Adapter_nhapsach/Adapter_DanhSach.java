package com.hieptran.quanlythuvien.QuanTriVien.NhapSach.Adapter_nhapsach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.SachNhap;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;

/**
 * Created by Hiep Tran on 5/3/2017.
 */

public class Adapter_DanhSach extends BaseAdapter {
    private ArrayList<SachNhap> sachNhapList;
    Context context;
    int layout;

    public Adapter_DanhSach(ArrayList<SachNhap> sachNhapList, Context context, int layout) {
        this.sachNhapList = sachNhapList;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return sachNhapList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.adapter_recyclerview, null);
        AnhXa(convertView);
        tv_Masach.setText(sachNhapList.get(position).getMaSach());
        tv_SoLuong.setText(sachNhapList.get(position).getSoLuong() + "");
        tv_tenSach.setText(sachNhapList.get(position).getTenSach());

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getRandomColor();
        TextDrawable textDrawable = TextDrawable.builder().buildRound(tv_tenSach.getText().toString().substring(0, 1).toLowerCase(), color);
        imgTitle.setImageDrawable(textDrawable);
        return convertView;
    }

    TextView tv_Masach, tv_tenSach, tv_SoLuong;
    ImageView imgTitle;

    private void AnhXa(View itemView) {
        tv_Masach = (TextView) itemView.findViewById(R.id.tv_recycler_MaSach);
        tv_SoLuong = (TextView) itemView.findViewById(R.id.tv_recycler_soluong);
        tv_tenSach = (TextView) itemView.findViewById(R.id.tv_recycler_tensach);
        imgTitle = (ImageView) itemView.findViewById(R.id.img_adpater_Title);
    }


}
