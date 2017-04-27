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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.hieptran.quanlythuvien.R;
import com.hieptran.quanlythuvien.Scan;

import es.dmoral.toasty.Toasty;

/**
 * Created by Hiep Tran on 4/25/2017.
 */

public class TaoDocGia extends Fragment {
    private final int requetCodeAvatar = 0, requetCodeScanMaDG = 1;
    private ImageButton imgScanMaDG;
    private ImageView imgAvatar;
    private EditText edMaDG, edTenDG, edDiaChi, edSdt, edLop, edTenDangNhap, edMatKHau;
    private Button btnDone;

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


        return view;
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
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgAvatar.setImageBitmap(bitmap);
        } else if (requestCode == requetCodeScanMaDG && resultCode == Activity.RESULT_OK && data != null) {
            String maDG = data.getStringExtra(Scan.MA_THE);
            edMaDG.setText(maDG);
        }
    }

    private void anhXa(View view) {
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

    }
}
