package com.twelve.mp3.player.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twelve.mp3.player.API.APIRequestData;
import com.twelve.mp3.player.API.RetroServer;
import com.twelve.mp3.player.Adapter.AdapterMusik;
import com.twelve.mp3.player.Model.ModelMusik;
import com.twelve.mp3.player.Model.ModelResponse;
import com.twelve.mp3.player.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
private RecyclerView rvMusik;
private FloatingActionButton fabTambah;
private ProgressBar pb12mp3;
private RecyclerView.Adapter adMusik;
private RecyclerView.LayoutManager lmMusik;
private List<ModelMusik> listMusik = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMusik = findViewById(R.id.rv_12mp3);
        fabTambah = findViewById(R.id.fab_tambah);
        pb12mp3 = findViewById(R.id.pb_12mp3);

        lmMusik = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMusik.setLayoutManager(lmMusik);

        fabTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Tambah.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveMusik();
    }

    public void retrieveMusik() {
        pb12mp3.setVisibility(View.VISIBLE);

        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardRetrieve();

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listMusik = response.body().getData();

                adMusik = new AdapterMusik(MainActivity.this, listMusik);
                rvMusik.setAdapter(adMusik);
                adMusik.notifyDataSetChanged();

                pb12mp3.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
                pb12mp3.setVisibility(View.GONE);
            }
        });
    }
}