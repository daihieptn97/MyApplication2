package com.hieptran.quanlythuvien.fragment.MuonSach;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hieptran.quanlythuvien.R;
import com.hieptran.quanlythuvien.Scan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import es.dmoral.toasty.Toasty;

/**
 * Created by Hiep Tran on 4/25/2017.
 */

public class MuonSach extends Fragment {
    public static final String key_KhoMuonSach = "KhoMuonSach";
    private ImageButton imgMaSach, imgMaDocGia;
    private MultiAutoCompleteTextView autoMaSach, autoMaDocGia;
    private TextView tvNgayMuon, tvNgayTra;
    private Button btn_Done;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.muonsach, container, false);
        getActivity().setTitle(R.string.Title_MuonSach);
        anhXa(view);

        imgMaSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Scan.class);
                startActivityForResult(intent, 1);
            }
        });

        imgMaDocGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Scan.class);
                startActivityForResult(intent, 2);
            }
        });

        tvNgayMuon.setText(getDateMuon());
        tvNgayTra.setText(getDateTra());
        btn_Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doneUpLoad();
            }
        });
        return view;
    }


    private void doneUpLoad() {
        if (autoMaDocGia.length() > 0 && autoMaSach.length() > 0) {
            SachMuon sachMuon = new SachMuon();
            sachMuon.setMaDocGia(autoMaDocGia.getText().toString());
            sachMuon.setMaSach(autoMaSach.getText().toString());
            sachMuon.setNgayMuon(getDateMuon());
            sachMuon.setNgayTra(getDateTra());
            databaseReference.child(key_KhoMuonSach).push().setValue(sachMuon);

            autoMaSach.setText("");
            autoMaDocGia.setText("");
            Toasty.success(getContext(), "Muợn Sách thành Công", Toast.LENGTH_SHORT).show();
        } else {
            Toasty.warning(getContext(), "Bạn Cần Nhập Đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private String getDateTra() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(calendar.DAY_OF_WEEK, 7);
        Date date = calendar.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }

    private String getDateMuon() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return simpleDateFormat.format(date);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == getActivity().RESULT_OK) {
            String maSach = data.getStringExtra(Scan.MA_THE);
            autoMaSach.setText(maSach);
        } else if (requestCode == 2 && resultCode == getActivity().RESULT_OK) {
            String maSach = data.getStringExtra(Scan.MA_THE);
            autoMaDocGia.setText(maSach);
        }
    }

    private void anhXa(View view) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        imgMaDocGia = (ImageButton) view.findViewById(R.id.img_Muon_MaDG);
        imgMaSach = (ImageButton) view.findViewById(R.id.img_muon_MaSach);
        autoMaDocGia = (MultiAutoCompleteTextView) view.findViewById(R.id.auto_Muon_MaDG);
        autoMaSach = (MultiAutoCompleteTextView) view.findViewById(R.id.auto_Muon_MaSach);
        tvNgayMuon = (TextView) view.findViewById(R.id.tv_Muon_Ngaymuon);
        tvNgayTra = (TextView) view.findViewById(R.id.tv_Muon_NgayTra);
        btn_Done = (Button) view.findViewById(R.id.btn_muon_Done);
    }
}
