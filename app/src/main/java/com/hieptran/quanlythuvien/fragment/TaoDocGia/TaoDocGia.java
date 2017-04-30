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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    public static final String keyKhoDocGia = "KhoDocGia";


    private final int requetCodeAvatar = 0, requetCodeScanMaDG = 1;
    private Bitmap bitmapGetData;
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
        //listTenDangNhap = new ArrayList<>();

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

        checkMaDG();
        if (checkDoDai() && checkMaKhau() && checkSoDienThoai() && mCheckMaDG) {
            if (edMatKHau.getText().toString().trim().equals(edNhapLaiMatKhau.getText().toString().trim())) {
                mCheckMaDG = false;
                DocGia docGia = new DocGia();

                String imgAvatar = Base64.encodeToString(getBytesFromBitmap(bitmapGetData), Base64.NO_WRAP);
                docGia.setAvatar(imgAvatar);
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
                Toasty.success(getContext(), "Tạo Thành Công", Toast.LENGTH_SHORT).show();
            } else {
                edMatKHau.setError("");
                edNhapLaiMatKhau.setError("");
                Toasty.warning(getContext(), "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();
            }
        }
    }

    boolean mCheckMaDG = false;

    private void checkMaDG() {
        mDatabase.child(keyKhoDocGia).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                DocGia docGia = dataSnapshot.getValue(DocGia.class);
                String maDGtemp = edMaDG.getText().toString().trim();
                if (docGia.getMaDocGia().equals(maDGtemp)) {
                    mCheckMaDG = true;
                    Toasty.warning(getContext(), "Mã đã tồn tại", Toast.LENGTH_SHORT).show();
                    return;
                }
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

    }


    private boolean checkDoDai() {
        if (edTenDangNhap.length() > 0 && edSdt.length() > 0 && edMatKHau.length() > 0 && edLop.length() > 0
                && edNhapLaiMatKhau.length() > 0 && edDiaChi.length() > 0 && edTenDG.length() > 0 && edMaDG.length() > 0 && bitmapGetData != null) {
            return true;
        } else {
            Toasty.warning(getContext(), "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
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
