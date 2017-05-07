package com.hieptran.quanlythuvien.QuanTriVien.fragment.TraSach;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.QuanTriVien.NhapSach.SachNhap;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.MuonSach.SachMuon;
import com.hieptran.quanlythuvien.R;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Hiep Tran on 4/25/2017.
 */

public class TraSach extends Fragment {
    private String key_KhoMuonSach = "KhoMuonSach", key_MaMuonSach = "maMuonSach", key_hienTrangMuon = "hienTrangMuon", key_soLuong = "soLuong";
    private AutoCompleteTextView autoMaDG, autoMaSach;
    private ImageButton imgMaDG, imgMaSach;
    private Button btn_Done;
    private DatabaseReference mDatabse;
    private List<String> listMaSach, listMaDG;
    private String maSach, maDG;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.trasach, container, false);
        AnhXa(view);

        KiemTraTrung();
        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kiemTraDoDaiMa()) {
                    maSach = autoMaSach.getText().toString().trim();
                    maDG = autoMaDG.getText().toString().trim();

                    for (int positon = 0; positon < listMaSach.size(); positon++) {
                        //  Log.d("AAAA", listMaSach.get(positon) + " : " + (maSach));
                        if (listMaSach.get(positon).equals(maSach) && listMaDG.get(positon).equals(maDG)) {

                            mDone(maSach, maDG);
                            // Toasty.warning(getContext(), "Mã này  tồn tại trong kho", Toast.LENGTH_SHORT).show();
                        }else {
                            Toasty.error(getContext(), "Mã sách hoặc mã đọc giả chưa đúng", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
        return view;
    }


    private void KiemTraTrung() {
        mDatabse.child(key_KhoMuonSach).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                SachMuon sachMuon = dataSnapshot.getValue(SachMuon.class);
                listMaSach.add(sachMuon.getMaSach());
                listMaDG.add(sachMuon.getMaDocGia());

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabse.child(key_KhoMuonSach).addListenerForSingleValueEvent(new ValueEventListener() { // kiểm tra xem mã có trong kho hay không nếu có thì xóa
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toasty.success(getContext(), "Tải xong dữ liệu", Toast.LENGTH_SHORT).show();
                autoMaSach.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, listMaSach));
                autoMaDG.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, listMaDG));
                autoMaDG.setThreshold(1);
                autoMaSach.setThreshold(1);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String keyKhoSach = "KhoSach", keyMaSach = "maSach";

    private void mDone(final String maSach, final String maDG) {

        Query query = mDatabse.child(key_KhoMuonSach).orderByChild(key_MaMuonSach).equalTo(maDG + maSach);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) { // gan hien trang la da tra
                    snapshot.getRef().child(key_hienTrangMuon).setValue(false);
                }

                Query query1 = mDatabse.child(keyKhoSach).orderByChild(keyMaSach).equalTo(maSach); //tra lai sach vao kho
                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            SachNhap sachNhap = snapshot.getValue(SachNhap.class);
                            //Log.d("aaaa", sachNhap.getSoLuong() + 1 + "");
                            snapshot.getRef().child(key_soLuong).setValue(sachNhap.getSoLuong() + 1);
                            Toasty.success(getContext(), "Hoàn Thành trả sách", Toast.LENGTH_SHORT).show();
                            autoMaSach.setText("");
                            autoMaDG.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private boolean kiemTraDoDaiMa() {
        if (autoMaDG.length() > 0 && autoMaSach.length() > 0) return true;
        else {
            Toasty.warning(getContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void AnhXa(View view) {
        listMaDG = new ArrayList();
        listMaSach = new ArrayList();
        mDatabse = FirebaseDatabase.getInstance().getReference();
        autoMaDG = (AutoCompleteTextView) view.findViewById(R.id.auto_Tra_MaDG);
        autoMaSach = (AutoCompleteTextView) view.findViewById(R.id.auto_Tra_Masach);
        imgMaDG = (ImageButton) view.findViewById(R.id.img_Tra_MaDG);
        imgMaSach = (ImageButton) view.findViewById(R.id.img_Tra_MaSach);
        btn_Done = (Button) view.findViewById(R.id.btn_Tra_Done);
    }
}
