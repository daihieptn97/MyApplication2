package com.hieptran.quanlythuvien.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hieptran.quanlythuvien.Database.Datbase_Account;
import com.hieptran.quanlythuvien.Login;
import com.hieptran.quanlythuvien.R;
import com.hieptran.quanlythuvien.TraSach.TraSach;
import com.hieptran.quanlythuvien.fragment.NhapSach.NhapSach;
import com.hieptran.quanlythuvien.fragment.MuonSach.MuonSach;
import com.hieptran.quanlythuvien.fragment.TaoDocGia.TaoDocGia;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        Anhxa_Naviga();
        changeFragment(new TongQuan_NV());
    }


    private String mail;

    private void Anhxa_Naviga() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.getHeaderView(0);
        TextView tv_mail = (TextView) view.findViewById(R.id.tv_naviga_email);

        Intent intent = getIntent();
        mail = intent.getStringExtra("email");
        tv_mail.setText(mail);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_TongQuan) {
            changeFragment(new TongQuan_NV());
        } else if (id == R.id.menu_ThemSach) {
            changeFragment(new NhapSach());
        } else if (id == R.id.menu_Muonsach) {
            changeFragment(new MuonSach());
        } else if (id == R.id.menu_trasach) {
            changeFragment(new TraSach());
        } else if (id == R.id.menu_thongke) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.menu_ThongTin) {
            changeFragment(new ThongTin());
        } else if (id == R.id.menu_DangXuat) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, Login.class);
            startActivity(intent);
            Datbase_Account datbase_account = Datbase_Account.getDatbase_account(this);
            datbase_account.Open_Database_TaiKhoan();
            datbase_account.Delete_Account();
            finish();
        } else if (id == R.id.menu_taoDgia) {
            changeFragment(new TaoDocGia());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        transaction.commit();
    }
}
