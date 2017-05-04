package com.hieptran.quanlythuvien.QuanTriVien.NhapSach.Adapter_nhapsach;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.SachNhap;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;

/**
 * Created by Hiep Tran on 5/3/2017.
 */

public class Adapter_DanhSach extends RecyclerView.Adapter<Adapter_DanhSach.viewHolder> {
    private ArrayList<SachNhap> sachNhapList;

    public Adapter_DanhSach(ArrayList<SachNhap> sachNhapList, Context context) {
        this.sachNhapList = sachNhapList;
        this.context = context;
    }

    Context context;

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.adapter_recyclerview, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        holder.tv_tenSach.setText(sachNhapList.get(position).getTenSach());
        holder.tv_Masach.setText(sachNhapList.get(position).getMaSach());
        holder.tv_SoLuong.setText(sachNhapList.get(position).getSoLuong() + "");
    }

    @Override
    public int getItemCount() {

        return sachNhapList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView tv_Masach, tv_tenSach, tv_SoLuong;

        public viewHolder(View itemView) {

            super(itemView);
            tv_Masach = (TextView) itemView.findViewById(R.id.tv_recycler_MaSach);
            tv_SoLuong = (TextView) itemView.findViewById(R.id.tv_recycler_soluong);
            tv_tenSach = (TextView) itemView.findViewById(R.id.tv_recycler_tensach);
        }
    }
}
