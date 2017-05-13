package com.hieptran.quanlythuvien.QuanTriVien.fragment.TongQuan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.SachNhap;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.MuonSach.SachMuon;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.TaoDocGia.DocGia;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;

/**
 * Created by hieptran on 5/12/2017.
 */

public class Adapter_SachHetHan extends BaseAdapter {

    public Adapter_SachHetHan(ArrayList<SachMuon> dsSachMuon, Context context, int layout) {
        this.dsSachMuon = dsSachMuon;
        this.context = context;
        this.layout = layout;
    }

    private ArrayList<SachMuon> dsSachMuon;

    Context context;
    int layout;

    @Override
    public int getCount() {
        return dsSachMuon.size();
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
        convertView = inflater.inflate(R.layout.item_row_sachhethan, null);
        anhXa(convertView);


        tv_MaDG.setText(dsSachMuon.get(position).getMaDocGia());
        tv_maSach.setText(dsSachMuon.get(position).getMaSach());
        tv_ngaymuon.setText(dsSachMuon.get(position).getNgayMuon());
        tv_ngaytra.setText(dsSachMuon.get(position).getNgayTra());
        return convertView;
    }


    private TextView tv_MaDG, tv_maSach, tv_ngaymuon, tv_ngaytra;


    private void anhXa(View convertView) {
        tv_MaDG = (TextView) convertView.findViewById(R.id.tv_madg_TQ);
        tv_maSach = (TextView) convertView.findViewById(R.id.tv_masacg_TQ);
        tv_ngaymuon = (TextView) convertView.findViewById(R.id.tv_ngaymuon_TQ);
        tv_ngaytra = (TextView) convertView.findViewById(R.id.tv_ngaytra_TQ);
    }
}
