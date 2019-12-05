package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.User;

public class RegisterActivity extends AppCompatActivity {
    EditText mUsername, mPassword, mNama, mTelepon;
    Button mRegister;

    AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mUsername = findViewById(R.id.etUsernameRegister);
        mPassword = findViewById(R.id.etPasswordRegister);
        mNama = findViewById(R.id.etNamaRegister);
        mTelepon = findViewById(R.id.etTeleponRegister);
        mRegister = findViewById(R.id.btnRegister);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    public void onSubmit() {
        mDb = AppDatabase.getDatabase(getApplicationContext());
        final User data = new User(
                mUsername.getText().toString(),
                mPassword.getText().toString(),
                mNama.getText().toString(),
                mTelepon.getText().toString()
        );
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.userDao().insertUser(data);
            }
        });
        finish();
    }
}
