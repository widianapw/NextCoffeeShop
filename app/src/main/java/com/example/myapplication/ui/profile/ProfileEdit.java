package com.example.myapplication.ui.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.User;

import static com.example.myapplication.ui.profile.ProfileFragment.ID_USER;

public class ProfileEdit extends AppCompatActivity {
    EditText etUsername, etName, etTelepon;
    Button btnSave, btnEditPassword;
    AppDatabase mDb;
    SharedPreferences sharedPreferences;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    public final static int ID_USER = 0;
    int id_user;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);
        sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        id_user = sharedPreferences.getInt(String.valueOf(ID_USER),0);

        etUsername = findViewById(R.id.etUsernameEdit);
        etName = findViewById(R.id.etNamaEdit);
        etTelepon = findViewById(R.id.etTeleponEdit);
        btnSave = findViewById(R.id.btnSaveProfil);
        btnEditPassword = findViewById(R.id.btnEditPassword);

        btnEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),PasswordEdit.class));
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveData();
    }

    public void retrieveData(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {

                        mDb = AppDatabase.getDatabase(getApplicationContext());
                        user = mDb.userDao().getUser(id_user);
                        etUsername.setText(user.getUsername());
                        etName.setText(user.getNama());
                        etTelepon.setText(user.getTelepon());


            }
        });
    }

    public void onSubmit(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                user = mDb.userDao().getUser(id_user);
                final User data = new User(
                        etUsername.getText().toString(),
                        etName.getText().toString(),
                        etTelepon.getText().toString()
                );
                data.setId(id_user);
                data.setPassword(user.getPassword());
                mDb.userDao().updateUser(data);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}
