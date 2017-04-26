package com.hieptran.quanlythuvien;

import android.content.Intent;
import android.net.ConnectivityManager;
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
import com.hieptran.quanlythuvien.Database.Account;
import com.hieptran.quanlythuvien.Database.Datbase_Account;
import com.hieptran.quanlythuvien.fragment.MainActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        khoiChay();


        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isInternetAvailable()) {
                    if (ed_Email.length() > 0 && ed_password.length() > 0) {
                        if (check_DocGia.isChecked()) {
                            Toasty.info(Login.this, "Đang Cập Nhập", Toast.LENGTH_SHORT).show();
                        } else {

                            progressBar.setVisibility(View.VISIBLE);
                            progressBar.show();
                            layoutLoginContent.setBackgroundResource(R.drawable.background_mo);
                            dangNhap();
                        }
                    } else {
                        Toasty.warning(Login.this, "Bạn Hãy Nhập Đủ Thông Tin", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toasty.error(Login.this, "Không có Kết Nối internet", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void khoiChay() {

        if (!datbase_account.isEmpty()) {
            Account account = datbase_account.LoadAccount();
            ed_password.setText(account.getPassword());
            ed_Email.setText(account.getMail());
            check_NhoMatKhau.setChecked(true);
        }


        if (isInternetAvailable()) {
            if (ed_password.length() > 0 && ed_Email.length() > 0) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.show();
                layoutLoginContent.setBackgroundResource(R.drawable.background_mo);

                auth.signInWithEmailAndPassword(ed_Email.getText().toString().trim(), ed_password.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.putExtra("email", ed_Email.getText().toString());
                            startActivity(intent);
                            progressBar.hide();
                            finish();
                        }
                    }
                });
            }
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
