package com.twelve.mp3.player.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.twelve.mp3.player.API.APIRequestData;
import com.twelve.mp3.player.API.RetroServer;
import com.twelve.mp3.player.Model.ModelPengguna;
import com.twelve.mp3.player.Model.ModelResponse;
import com.twelve.mp3.player.R;
import com.twelve.mp3.player.Utility.KendaliLogin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private String username, password;
    private List<ModelPengguna> listPengguna = new ArrayList<>();
    private KendaliLogin KL = new KendaliLogin(Login.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (username.trim().isEmpty()){
                    etUsername.setError("Username can't be empt");
                } else if (password.trim().isEmpty()) {
                    etPassword.setError("Password can't be empty");
                } else {
                    loginMusik();
                }
            }
        });
    }

    private void loginMusik(){
        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> call = apiRequestData.ardLogin(username, password);

        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listPengguna = response.body().getDataPengguna();

                if (kode.equals("0")){
                    Toast.makeText(Login.this, "Username or Password incorrect", Toast.LENGTH_SHORT).show();
                } else {
                    KL.setPref(KL.keySP_username, listPengguna.get(0).getUsername());

                    startActivity(new Intent(Login.this, MainActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Login eror! Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}