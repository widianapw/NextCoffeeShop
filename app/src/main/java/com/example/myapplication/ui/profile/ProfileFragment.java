package com.example.myapplication.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.example.myapplication.LoginActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.AppDatabase;
import com.example.myapplication.database.AppExecutors;
import com.example.myapplication.model.User;

public class ProfileFragment extends Fragment {
    TextView mUsername, mName;
    Button btnLogout, btnEditProfil;

    SharedPreferences sharedPreferences;
    boolean session = false;
    int id_user;
    AppDatabase mDb;
    User user;

    final String SHARED_PREFERENCES_NAME = "shared_preferences";
    final String SESSION_STATUS = "session";
    public final static int ID_USER = 0;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        mDb = AppDatabase.getDatabase(getContext());
        sharedPreferences = requireContext().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        id_user = sharedPreferences.getInt(String.valueOf(ID_USER),0);
        mUsername = root.findViewById(R.id.tvUsername);
        mName = root.findViewById(R.id.tvNama);
        btnEditProfil = root.findViewById(R.id.btnEditProfil);
        btnLogout = root.findViewById(R.id.btnLogout);

        user = mDb.userDao().getUser(id_user);
        setUiData(user);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(SESSION_STATUS);
                editor.apply();

                startActivity(new Intent(getContext(), LoginActivity.class));

            }
        });
        btnEditProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileEdit.class));
            }
        });
        return root;
    }

    private void setUiData(User user){
        mUsername.setText(user.getUsername());
        mName.setText(user.getNama());
    }
}