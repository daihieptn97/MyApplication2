package com.hieptran.quanlythuvien;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hieptran.quanlythuvien.Database.Account;
import com.hieptran.quanlythuvien.Database.DataBase_KiemTra;
import com.hieptran.quanlythuvien.Database.Datbase_Account;
import com.hieptran.quanlythuvien.Database.KiemTra;
import com.hieptran.quanlythuvien.DocGia.MainDocGia;
import com.hieptran.quanlythuvien.DocGia.ThongTinMuonSachDG;
import com.hieptran.quanlythuvien.QuanTriVien.MainActivity;
import com.hieptran.quanlythuvien.QuanTriVien.fragment.TaoDocGia.DocGia;
import com.wang.avi.AVLoadingIndicatorView;

import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {
    private Button btnDangNhap;
    private EditText ed_Email, ed_password;
    private FirebaseAuth auth;
    private CheckBox check_NhoMatKhau, check_DocGia;
    private Datbase_Account datbase_account;
    private AVLoadingIndicatorView progressBar;

    private ViewGroup layoutLoginContent;
    private DataBase_KiemTra dataBase_kiemTra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        khoiChay();
        initPermission();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.show();
                    if (ed_Email.length() > 0 && ed_password.length() > 0) {
                        if (check_DocGia.isChecked()) {
                            dangNhapDocGia();
                        } else {
                            //layoutLoginContent.setBackgroundResource(R.drawable.background_mo);
                            dangNhap(); // dang nhap cua quan tri vien
                        }
                    } else {
                        Toasty.warning(Login.this, "Bạn Hãy Nhập Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                        progressBar.hide();
                    }
                } else {
                    Toasty.error(Login.this, "Không có Kết Nối internet", Toast.LENGTH_SHORT).show();
                    progressBar.hide();
                }
            }
        });


    }

    private DatabaseReference mDatabase;
    private boolean ok = true;

    private void dangNhapDocGia() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("KhoDocGia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    DocGia docGia = snapshot.getValue(DocGia.class);
                    if (docGia.getTenDangNhap().equals(ed_Email.getText().toString().trim()) && docGia.getMatKhau().equals(ed_password.getText().toString().trim())) {
                        if (check_NhoMatKhau.isChecked()) { // neu muon luu mat khua thi moi luu
                            if (!datbase_account.isExits(ed_Email.getText().toString())) { // kiem tra email da ton tai trong databse chua, chua thi luu lai, con co roi thi thoi
                                Account account = new Account();
                                account.setMail(ed_Email.getText().toString());
                                account.setPassword(ed_password.getText().toString());
                                datbase_account.Insert_Datbase_Account(account);
                            }
                        }
                        ok = false;

                        if (check_NhoMatKhau.isChecked()) {
                            KiemTra kiemTra = new KiemTra();
                            kiemTra.setDocGia(1);
                            kiemTra.setNhoMatKhau(1);
                            dataBase_kiemTra.InsertDatbaseKiemTra(kiemTra);
                        }
                        Bundle bundle = new Bundle();

//                        bundle.putString("email", ed_Email.getText().toString().trim());
//                        ThongTinMuonSachDG sachDG = new ThongTinMuonSachDG();
//                        sachDG.setArguments(bundle);
                        Intent intent = new Intent(Login.this, MainDocGia.class);
                        intent.putExtra("email", ed_Email.getText().toString().trim());
                        startActivity(intent);
                        progressBar.hide();
                        finish();
                    }
                }

                if (ok) {
                    Toasty.error(Login.this, "sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    progressBar.hide();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Login.this, "Permision ok", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Login.this, "Permision chua duoc cap", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) &&
                        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_WIFI_STATE) &&
                        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_NETWORK_STATE)) {
                    Toasty.error(Login.this, "Quyền chưa được cấp ", Toast.LENGTH_SHORT).show();
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE}, 3);
                }
            }
        }
    }

    private void khoiChay() {

        if (!datbase_account.isEmpty()) {
            Account account = datbase_account.LoadAccount();
            ed_password.setText(account.getPassword());
            ed_Email.setText(account.getMail());
        }

        dataBase_kiemTra = DataBase_KiemTra.getDataBase_kiemTra(this);
        dataBase_kiemTra.OpenDatabaseKiemTra();

      //  try {

            if (!dataBase_kiemTra.isEmpty()) {
                KiemTra kiemTra = dataBase_kiemTra.loadTTKiemTra();
                if (kiemTra.getDocGia() == 1) check_DocGia.setChecked(true);
                if (kiemTra.getNhoMatKhau() == 1) check_NhoMatKhau.setChecked(true);
                if (kiemTra.getDocGia() == 0) check_NhoMatKhau.setChecked(false);
                if (kiemTra.getNhoMatKhau() == 0) check_DocGia.setChecked(false);
            }
//        }catch (Exception e){
//
//        }

        if (isInternetAvailable() && !check_DocGia.isChecked()) {
            if (ed_password.length() > 0 && ed_Email.length() > 0) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.show();
                //layoutLoginContent.setBackgroundResource(R.drawable.background_mo);


                auth.signInWithEmailAndPassword(ed_Email.getText().toString().trim(), ed_password.getText().toString().trim()).
                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    intent.putExtra("email", ed_Email.getText().toString());
                                    startActivity(intent);
                                    progressBar.hide();
                                    finish();
                                } else {
                                    Toasty.error(Login.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                                    progressBar.hide();
                                }
                            }
                        });
            }
        } else if (isInternetAvailable() && check_DocGia.isChecked()) {
            dangNhapDocGia();
        } else {
            Toasty.error(Login.this, "Không có Kết Nối internet", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean isInternetAvailable() { // kiem tra ket noi internet
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Login.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void dangNhap() {
        auth.signInWithEmailAndPassword(ed_Email.getText().toString().trim(), ed_password.getText().toString().trim()).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (check_NhoMatKhau.isChecked()) { // neu muon luu mat khua thi moi luu
                                if (!datbase_account.isExits(ed_Email.getText().toString())) { // kiem tra email da ton tai trong databse chua, chua thi luu lai, con co roi thi thoi
                                    Account account = new Account();
                                    account.setMail(ed_Email.getText().toString());
                                    account.setPassword(ed_password.getText().toString());
                                    datbase_account.Insert_Datbase_Account(account);

                                }
                            }

                            if (check_NhoMatKhau.isChecked()) {
                                KiemTra kiemTra = new KiemTra();
                                kiemTra.setDocGia(0);
                                kiemTra.setNhoMatKhau(1);
                                dataBase_kiemTra.InsertDatbaseKiemTra(kiemTra);
                            }
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("email", ed_Email.getText().toString());
                            startActivity(intent);
                            progressBar.hide();
                            finish();

                        } else {
                            Toasty.warning(Login.this, "Lỗi Đăng Nhập", Toast.LENGTH_SHORT).show();
                            progressBar.hide();
                            layoutLoginContent.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
        finish();
    }

    private void anhXa() {

        datbase_account = Datbase_Account.getDatbase_account(this);
        datbase_account.Open_Database_TaiKhoan();
        auth = FirebaseAuth.getInstance();
        progressBar = (AVLoadingIndicatorView) findViewById(R.id.process);
        check_DocGia = (CheckBox) findViewById(R.id.checkBox_sinhvien);
        check_NhoMatKhau = (CheckBox) findViewById(R.id.check_nhoMatKhau);
        btnDangNhap = (Button) findViewById(R.id.btn_login_DangNhap);
        ed_Email = (EditText) findViewById(R.id.ed_Login_mail);
        ed_password = (EditText) findViewById(R.id.ed_Login_pass);

        layoutLoginContent = (ViewGroup) findViewById(R.id.layoutLoginContent);
    }
}
