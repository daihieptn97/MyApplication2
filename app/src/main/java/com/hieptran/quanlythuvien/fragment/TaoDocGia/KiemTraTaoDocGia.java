package com.hieptran.quanlythuvien.fragment.TaoDocGia;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by daihieptn97 on 28/04/2017.
 */

public class KiemTraTaoDocGia {
    private void KiemTraMaDocGia(final String maSoSanh, DatabaseReference databaseReference) {
        databaseReference.child("tenDangNhap").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot
                        maDG((Map<String, Object>) dataSnapshot.getValue(), maSoSanh);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });
    }

    private boolean check;

    private boolean maDG(Map<String, Object> users, String maSoSanh) {
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()) {

            //Get user map
            Map singleUser = (Map) entry.getValue();
            //Get phone field and append to list
            // phoneNumbers.add((Long) singleUser.get("maDocGia"));
            String temp = (String) singleUser.get("maDocGia");
            if (maSoSanh.equals(temp)){
                Log.d("ok", temp);
                check = false;
                return false;
            }
        }
        check = true;
        return true;

    }

    protected  boolean kiemTra(String maSoSanh, DatabaseReference databaseReference){
        KiemTraMaDocGia(maSoSanh, databaseReference);
        if (check) return true;
        else  return  false;
    }
}
