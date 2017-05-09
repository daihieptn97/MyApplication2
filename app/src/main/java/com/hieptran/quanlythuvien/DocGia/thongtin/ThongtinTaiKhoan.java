package com.hieptran.quanlythuvien.DocGia.thongtin;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.Database.Database_thongtindocgia;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.TaoDocGia.DocGia;
import com.hieptran.quanlythuvien.R;

/**
 * Created by hieptran on 5/7/2017.
 */

public class ThongtinTaiKhoan extends Fragment {
    private TextView tvMaDG, tvDiachi, tvLop, tvSdt, tvNgaySinh, tvTen;
    private Button btnSuaThongTin;
    private ImageButton imgAvatar;
    private DatabaseReference mDataBase;
    private Database_thongtindocgia thongtindocgia;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.thongtintaikhoan_docgia, container, false);
        anhXa(view);
        LoadData();

        btnSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SuaThongTin.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private String KEY_khosach = "KhoDocGia", keyTenDangNhap = "tenDangNhap";
    private String mTenDangNhap() {
        return getActivity().getIntent().getStringExtra("email");
    }

    private void setThongTin(){
        DocGia docGia = thongtindocgia.loadThongTinDocGia();
        tvTen.setText(docGia.getTenDG());
        tvNgaySinh.setText(docGia.getNgaySinh());
        tvSdt.setText(docGia.getSdt());
        tvDiachi.setText(docGia.getDiaChi());
        tvMaDG.setText(docGia.getMaDocGia());
        tvLop.setText(docGia.getLop());
    }

    private void LoadData() {
       if (thongtindocgia.isEmpty()){
           Query query = mDataBase.child(KEY_khosach).orderByChild(keyTenDangNhap).equalTo(mTenDangNhap() + "");
           query.addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                       DocGia docGia = snapshot.getValue(DocGia.class);
                       thongtindocgia.Insert_Datbase_thongtin(docGia);
                       setThongTin();
                   }
               }

               @Override
               public void onCancelled(DatabaseError databaseError) {

               }
           });
       }else {
           setThongTin();
       }
    }

    private void anhXa(View view) {
        thongtindocgia = Database_thongtindocgia.getDatbase_account(getContext());
        thongtindocgia.Open_Database_thongtin();
        mDataBase = FirebaseDatabase.getInstance().getReference();
        tvTen = (TextView) view.findViewById(R.id.tv_DG_tenDG);
        imgAvatar = (ImageButton) view.findViewById(R.id.img_DG_avatar);
        btnSuaThongTin = (Button) view.findViewById(R.id.btn_DG_suathongtin);
        tvMaDG = (TextView) view.findViewById(R.id.tv_DG_maDG);
        tvDiachi = (TextView) view.findViewById(R.id.tv_DG_diachi);
        tvLop = (TextView) view.findViewById(R.id.tv_DG_lop);
        tvSdt = (TextView) view.findViewById(R.id.tv_DG_sdt);
        tvNgaySinh = (TextView) view.findViewById(R.id.tv_DG_NgaySinh);
    }

}

