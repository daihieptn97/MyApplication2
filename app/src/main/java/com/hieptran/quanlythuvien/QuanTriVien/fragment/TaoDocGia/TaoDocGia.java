package com.hieptran.quanlythuvien.QuanTriVien.fragment.TaoDocGia;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.R;
import com.hieptran.quanlythuvien.QuanTriVien.Scan;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * Created by Hiep Tran on 4/25/2017.
 */

public class TaoDocGia extends Fragment {
    public static final String keyKhoDocGia = "KhoDocGia";


    private final int requetCodeAvatar = 0, requetCodeScanMaDG = 1;
    private Bitmap bitmapGetData;
    private ImageButton imgScanMaDG;
    private ImageView imgAvatar;
    private EditText edMaDG, edTenDG, edDiaChi, edSdt, edLop, edTenDangNhap, edMatKHau, edNhapLaiMatKhau, edNgaysinh, edThangSinh, edNamSinh;
    private Button btnDone;
    private DatabaseReference mDatabase;
    private List<String> listMaDG, lisTenDangNhap;
    private String tempTenDangNhap, tempMaSV;
    private AVLoadingIndicatorView progressbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tao_taikhoan_dg, container, false);
        getActivity().setTitle("Tạo Tài Khoản Độc Giả");
        anhXa(view);
        lisTenDangNhap = new ArrayList<>();
        listMaDG = new ArrayList<>();


        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chonAnh();
            }
        });

        imgScanMaDG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Scan.class);
                startActivityForResult(intent, requetCodeScanMaDG);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                tempTenDangNhap = edTenDangNhap.getText().toString().trim();
                tempMaSV = edMaDG.getText().toString().trim();

                kiemTraMaDG();
            }
        });


        return view;
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        return stream.toByteArray();
    }

    private void doneAndupLoad() {


        if (checkDoDai() && checkMaKhau() && checkSoDienThoai()) {
            if (edMatKHau.getText().toString().trim().equals(edNhapLaiMatKhau.getText().toString().trim())) {

                DocGia docGia = new DocGia();

                String imgAvatar = Base64.encodeToString(getBytesFromBitmap(bitmapGetData), Base64.NO_WRAP);
                docGia.setAvatar(imgAvatar);

                docGia.setNgaySinh(edNgaysinh.getText().toString().trim() + "/" + edThangSinh.getText().toString().trim() + "/" + edNamSinh.getText().toString().trim());
                docGia.setMaDocGia(edMaDG.getText().toString().trim());
                docGia.setDiaChi(edDiaChi.getText().toString());
                docGia.setLop(edLop.getText().toString());
                docGia.setSdt(edSdt.getText().toString());
                docGia.setTenDG(edTenDG.getText().toString());
                docGia.setTenDangNhap(edTenDangNhap.getText().toString());
                docGia.setMatKhau(edMatKHau.getText().toString());

                mDatabase.child(keyKhoDocGia).push().setValue(docGia);

                edNhapLaiMatKhau.setText("");
                edMatKHau.setText("");
                edLop.setText("");
                edMaDG.setText("");
                edSdt.setText("");
                edTenDG.setText("");
                edDiaChi.setText("");
                edTenDangNhap.setText("");
                edNamSinh.setText("");
                edThangSinh.setText("");
                edNgaysinh.setText("");
                progressbar.setVisibility(View.GONE);
                Toasty.success(getContext(), "Tạo Thành Công", Toast.LENGTH_SHORT).show();
            } else { // kiem tra quan ly nhao lai lan 2 co dung mat khau hay khong, hay noi cach khac la can chac chan nhap mat khau cho sinh vien la dung
                edMatKHau.setError("");
                edNhapLaiMatKhau.setError("");
                progressbar.setVisibility(View.GONE);
                Toasty.warning(getContext(), "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean kiemTraNgaySinh() {
        if (edNamSinh.length() > 0 && edNgaysinh.length() > 0 && edThangSinh.length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void kiemTraMaDG() {
        mDatabase.child(keyKhoDocGia).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                DocGia docGia = dataSnapshot.getValue(DocGia.class);
                lisTenDangNhap.add(docGia.getTenDangNhap());
                listMaDG.add(docGia.getMaDocGia());
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

        mDatabase.child(keyKhoDocGia).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int count = lisTenDangNhap.size();

                for (int i = 0; i < lisTenDangNhap.size(); i++) {
                    Log.d("AAAA", lisTenDangNhap.get(i) + " : " + tempMaSV);
                    if (lisTenDangNhap.get(i).equals(tempTenDangNhap) || listMaDG.get(i).equals(tempMaSV)) {
                        count--;
                    }
                }
                if (count == lisTenDangNhap.size()) {
                    doneAndupLoad();

                } else {
                    progressbar.setVisibility(View.GONE);
                    Toasty.warning(getContext(), getString(R.string.wTrungMaDG_tenDangNhap), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private boolean checkDoDai() {
        if (edTenDangNhap.length() > 0 && edSdt.length() > 0 && edMatKHau.length() > 0 && edLop.length() > 0
                && edNhapLaiMatKhau.length() > 0 && edDiaChi.length() > 0 && edTenDG.length() > 0
                && edMaDG.length() > 0 && bitmapGetData != null && kiemTraNgaySinh()) {
            return true;
        } else {
            Toasty.warning(getContext(), getString(R.string.wChuaDuThongTin), Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private boolean checkSoDienThoai() {
        if (edSdt.length() >= 10) {
            return true;
        } else {
            Toasty.warning(getContext(), "số điện thoại chưa đúng", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    private boolean checkMaKhau() {
        if (edMatKHau.length() >= 5) {
            return true;
        } else {
            Toasty.warning(getContext(), "Mật khẩu cần hơn 5 ký tư", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void chonAnh() {
        CharSequence[] mchonAnh = new CharSequence[]{"Chụp ảnh", "Chọn ảnh"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Thay đổi ảnh");

        builder.setItems(mchonAnh, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, requetCodeAvatar);
                }
                if (which == 1) {
                    Toasty.info(getContext(), "Dang cap nhap", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requetCodeAvatar && resultCode == Activity.RESULT_OK && data != null) {
            bitmapGetData = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmapGetData);
        } else if (requestCode == requetCodeScanMaDG && resultCode == Activity.RESULT_OK && data != null) {
            String maDG = data.getStringExtra(Scan.MA_THE);
            edMaDG.setText(maDG);
        }
    }

    private void anhXa(View view) {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        progressbar = (AVLoadingIndicatorView) view.findViewById(R.id.processTaoTaiKHoan);
        btnDone = (Button) view.findViewById(R.id.btnTaoDone);
        imgAvatar = (ImageView) view.findViewById(R.id.imgTaoAvatar);
        imgScanMaDG = (ImageButton) view.findViewById(R.id.imgTaoScanMaDG);
        edMaDG = (EditText) view.findViewById(R.id.ed_DG_madocgia);
        edTenDG = (EditText) view.findViewById(R.id.ed_DG_TenDG);
        edDiaChi = (EditText) view.findViewById(R.id.ed_DG_Diachi);
        edSdt = (EditText) view.findViewById(R.id.ed_DG_SDT);
        edLop = (EditText) view.findViewById(R.id.ed_DG_Lop);
        edTenDangNhap = (EditText) view.findViewById(R.id.ed_DG_tenDangNhap);
        edMatKHau = (EditText) view.findViewById(R.id.ed_DG_MatKhau);
        edNhapLaiMatKhau = (EditText) view.findViewById(R.id.ed_DG_NhapLaiMatKhau);
        edNgaysinh = (EditText) view.findViewById(R.id.ed_DG_NgaySinh_ngay);
        edThangSinh = (EditText) view.findViewById(R.id.ed_DG_NgaySinh_thang);
        edNamSinh = (EditText) view.findViewById(R.id.ed_DG_NgaySinh_nam);
    }
}
