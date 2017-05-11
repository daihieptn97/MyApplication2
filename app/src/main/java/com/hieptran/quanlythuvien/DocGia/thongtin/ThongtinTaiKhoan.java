package com.hieptran.quanlythuvien.DocGia.thongtin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.Database.DataBase_KiemTra;
import com.hieptran.quanlythuvien.Database.Database_thongtindocgia;
import com.hieptran.quanlythuvien.Database.Datbase_Account;
import com.hieptran.quanlythuvien.Login;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.TaoDocGia.DocGia;
import com.hieptran.quanlythuvien.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by hieptran on 5/7/2017.
 */

public class ThongtinTaiKhoan extends Fragment {
    private TextView tvMaDG, tvDiachi, tvLop, tvSdt, tvNgaySinh, tvTen;
    private Button btnSuaThongTin, btnDangXuat;
    private CircleImageView imgAvatar;
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

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                Datbase_Account datbase_account = Datbase_Account.getDatbase_account(getContext());
                datbase_account.Open_Database_TaiKhoan();
                datbase_account.Delete_Account();

                DataBase_KiemTra dataBase_kiemTra = DataBase_KiemTra.getDataBase_kiemTra(getContext());
                dataBase_kiemTra.OpenDatabaseKiemTra();
                dataBase_kiemTra.Delete_KiemTra();

                Database_thongtindocgia thongtindocgia = Database_thongtindocgia.getDatbase_thongtindocgia(getContext());
                thongtindocgia.Open_Database_thongtin();
                thongtindocgia.Delete_TTDG();
                getActivity().finish();
            }
        });
        return view;
    }

    private String KEY_khosach = "KhoDocGia", keyTenDangNhap = "tenDangNhap";

    private String mTenDangNhap() {
        return getActivity().getIntent().getStringExtra("email");
    }

    private void setThongTin() {
        DocGia docGia = thongtindocgia.loadThongTinDocGia();
        tvTen.setText(docGia.getTenDG());
        tvNgaySinh.setText(docGia.getNgaySinh());
        tvSdt.setText(docGia.getSdt());
        tvDiachi.setText(docGia.getDiaChi());
        tvMaDG.setText(docGia.getMaDocGia());
        tvLop.setText(docGia.getLop());

        imgAvatar.setImageBitmap(StringToBitMap(docGia.getAvatar()));
    }

    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void LoadData() {
        if (thongtindocgia.isEmpty()) {
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
        } else {
            setThongTin();
        }
    }

    private void anhXa(View view) {

        thongtindocgia = Database_thongtindocgia.getDatbase_thongtindocgia(getContext());
        thongtindocgia.Open_Database_thongtin();
        mDataBase = FirebaseDatabase.getInstance().getReference();
        tvTen = (TextView) view.findViewById(R.id.tv_DG_tenDG);
        imgAvatar = (CircleImageView) view.findViewById(R.id.img_DG_avatar);
        btnSuaThongTin = (Button) view.findViewById(R.id.btn_DG_suathongtin);
        tvMaDG = (TextView) view.findViewById(R.id.tv_DG_maDG);
        tvDiachi = (TextView) view.findViewById(R.id.tv_DG_diachi);
        tvLop = (TextView) view.findViewById(R.id.tv_DG_lop);
        tvSdt = (TextView) view.findViewById(R.id.tv_DG_sdt);
        tvNgaySinh = (TextView) view.findViewById(R.id.tv_DG_NgaySinh);
        btnDangXuat = (Button) view.findViewById(R.id.btn_DG_DangXuat);
    }

}

