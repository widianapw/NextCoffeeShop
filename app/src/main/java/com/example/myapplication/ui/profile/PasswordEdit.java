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

public class PasswordEdit extends AppCompatActivity {
    EditText etPassLama, etPassBaru;
    Button btnSave;

    SharedPreferences sharedPreferences;
    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    public final static int ID_USER = 0;
    int id_user;
    User user, userValidate;
    AppDatabase mDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_edit);

        sharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        id_user = sharedPreferences.getInt(String.valueOf(ID_USER),0);

        mDb = AppDatabase.getDatabase(getApplicationContext());
        etPassBaru = findViewById(R.id.etPasswordBaru);
        etPassLama = findViewById(R.id.etPasswordLama);
        btnSave = findViewById(R.id.btnSavePassword);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    public void onSubmit(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                user = mDb.userDao().getUser(id_user);
                userValidate = mDb.userDao().loginUser(user.getUsername(), etPassLama.getText().toString());
                if (userValidate.getId() > 0){
                    final User data = new User(
                            etPassBaru.getText().toString()
                    );
                    data.setId(user.getId());
                    data.setNama(user.getNama());
                    data.setUsername(user.getUsername());
                    data.setTelepon(user.getTelepon());
                    mDb.userDao().updateUser(data);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
    }
}
