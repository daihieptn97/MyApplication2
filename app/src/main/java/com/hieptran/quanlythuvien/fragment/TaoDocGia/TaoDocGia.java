package com.hieptran.quanlythuvien.fragment.TaoDocGia;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hieptran.quanlythuvien.R;
import com.hieptran.quanlythuvien.Scan;

import java.io.ByteArrayOutputStream;

import es.dmoral.toasty.Toasty;

/**
 * Created by Hiep Tran on 4/25/2017.
 */

public class TaoDocGia extends Fragment {
    private static final String keyKhoDocGia = "KhoDocGia";
    private final int requetCodeAvatar = 0, requetCodeScanMaDG = 1;
    Bitmap bitmapGetData;
    private ImageButton imgScanMaDG;
    private ImageView imgAvatar;
    private EditText edMaDG, edTenDG, edDiaChi, edSdt, edLop, edTenDangNhap, edMatKHau, edNhapLaiMatKhau;
    private Button btnDone;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tao_taikhoan_dg, container, false);
        getActivity().setTitle("Tạo Tài Khoản Độc Giả");
        anhXa(view);

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
                doneAndupLoad();
            }
        });

        return view;
    }

    // convert from bitmap to byte array
    public byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        return stream.toByteArray();
    }

    private void doneAndupLoad() {
        if (edMatKHau.getText().toString().trim().equals(edNhapLaiMatKhau.getText().toString().trim())) {
            DocGia docGia = new DocGia();
            //docGia.setAvatar(getBytesFromBitmap(bitmapGetData));
            String imgAvatar = Base64.encodeToString(getBytesFromBitmap(bitmapGetData), Base64.NO_WRAP);
            docGia.setAvatar(imgAvatar);
            docGia.setMaDocGia(edMaDG.getText().toString().trim());
            docGia.setDiaChi(edDiaChi.getText().toString());
            docGia.setLop(edLop.getText().toString());
            docGia.setSdt(edSdt.getText().toString());
            docGia.setTenDG(edTenDG.getText().toString());
            docGia.setTenDangNhap(edTenDangNhap.getText().toString());
            docGia.setMatKhau(edMatKHau.getText().toString());

            mDatabase.child(keyKhoDocGia).push().setValue(docGia); // set value bi loi, neu co them anh bitmap

            edNhapLaiMatKhau.setText("");
            edMatKHau.setText("");
            edLop.setText("");
            edMaDG.setText("");
            edSdt.setText("");
            edTenDG.setText("");
            edDiaChi.setText("");
            edTenDangNhap.setText("");
            Toasty.success(getContext(), "Tạo Thành Công", Toast.LENGTH_SHORT).show();
        } else {
            edMatKHau.setError("");
            edNhapLaiMatKhau.setError("");
            Toasty.warning(getContext(), "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();
        }
    }

    private void chonAnh() {
        CharSequence[] mchonAnh = new CharSequence[]{"Chụp ảnh", "Chọn "};
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

        //imgAvatar.setImageDrawable(R.drawable.common_google_signin_btn_icon_light);

    }
}
