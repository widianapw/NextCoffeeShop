package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.User;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity {
    EditText mUsername, mPassword;
    Button mLogin, mRegister;
    AppDatabase mDb;
    User user;

    SharedPreferences sharedPreferences;
    boolean session = false;
    int id_user;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    final String SESSION_STATUS = "session";
    final int ID_USER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        session = sharedPreferences.getBoolean(SESSION_STATUS, false);
        id_user = sharedPreferences.getInt(String.valueOf(ID_USER), 0);

        if (session) {
            Log.e("as", "" + id_user);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra(String.valueOf(ID_USER), id_user);
            finish();
            startActivity(intent);
        }

        mUsername = findViewById(R.id.etUsernameLogin);
        mPassword = findViewById(R.id.etPasswordLogin);
        mLogin = findViewById(R.id.btnLogin);
        mRegister = findViewById(R.id.btnLoginToRegister);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    public void onSubmit() {
        mDb = AppDatabase.getDatabase(getApplicationContext());
        user = mDb.userDao().loginUser(mUsername.getText().toString(), mPassword.getText().toString());
        if (user == null){
            new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Oops...")
                    .setContentText("Username dan Password Salah!")
                    .show();
        }
        else{
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    user = mDb.userDao().loginUser(mUsername.getText().toString(), mPassword.getText().toString());
                    int id_user = user.getId();
                    if (id_user > 0) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean(SESSION_STATUS, true);
                        editor.putInt(String.valueOf(ID_USER), user.getId());
                        editor.commit();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    }


                }
            });
        }

    }
}
