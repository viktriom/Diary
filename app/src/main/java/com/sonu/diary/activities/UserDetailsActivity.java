package com.sonu.diary.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.sonu.diary.R;
import com.sonu.diary.database.DatabaseManager;
import com.sonu.diary.domain.User;
import com.sonu.diary.domain.enums.SyncStatus;
import com.sonu.diary.remote.SyncService;
import com.sonu.diary.util.AppConstants;
import com.sonu.diary.util.DateUtils;

import java.sql.SQLException;
import java.sql.Timestamp;

public class UserDetailsActivity extends AbstractActivity {

    private EditText txtUserId;
    private EditText txtFirstName;
    private EditText txtLastName;
    private TextView lblDob;
    private Button btnSave;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        init();
    }

    private void init(){
        txtUserId = findViewById(R.id.txtUserId);
        txtFirstName = findViewById(R.id.txtFirstName);
        txtLastName = findViewById(R.id.txtLastName);
        lblDob = findViewById(R.id.lblDob);
        btnSave = findViewById(R.id.btnNext);
        user = new User();
    }

    public void btnNextTouched(View view) {
        user.setUserId(txtUserId.getText().toString());
        user.setFirstName(txtFirstName.getText().toString());
        user.setLastName(txtLastName.getText().toString());
        user.setRole(AppConstants.OWNER_ROLE);
        user.setSyncStatus(SyncStatus.P.name());
        user.setGroup("viktri_moksh");
        user.setInitials("");
        if(validateInput()){
            try {
                Dao<User,String> userDao = DatabaseManager.getInstance().getHelper().getDao(User.class);
                userDao.create(user);
                SyncService.createUserOnServer(this, user);
            } catch (SQLException e) {
                Log.e("USER_REGISTRATION","Error encountered while creating a new user.");
            }
            finish();
        }
    }

    private boolean validateInput(){
        Toast toast =  Toast.makeText(this, "", Toast.LENGTH_SHORT);
        if(null == user.getUserId() || user.getUserId().isEmpty()){
            toast.setText("Please enter a valid user name.");
            toast.show();
            return false;
        }

        if(null == user.getFirstName() || user.getFirstName().isEmpty()){
            toast.setText("Please enter a valid First Name.");
            toast.show();
            return false;
        }

        if(null == user.getLastName() || user.getLastName().isEmpty()){
            toast.setText("Please enter a valid Last Name.");
            toast.show();
            return false;
        }

        if(null == user.getDateOfBirth()){
            toast.setText("Please select valid date of birth.");
            toast.show();
            return true;
        }
        return true;
    }

    @Override
    public void performActionAfterDateTimeUpdate(Timestamp ts) {
        super.performActionAfterDateTimeUpdate(ts);
        user.setDateOfBirth(ts);
    }

    public void onToDateTouched(View view) {
        super.showDatePicker(view, DateUtils.getCurrentTimestamp());
    }
}
