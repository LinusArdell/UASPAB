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
import com.twelve.mp3.player.Model.ModelResponse;
import com.twelve.mp3.player.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ubah extends AppCompatActivity {
    private String yid, yjudul, yartis, yalbum, ygenre, ylirik, ylink;
    private EditText etJudul, etArtis, etAlbum, etGenre, etlirik, etlink;
    private Button btnUbah;
    private String judul, artis, album, genre, lirik, link;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent ambil = getIntent();
        yid = ambil.getStringExtra("xId");
        yjudul = ambil.getStringExtra("xjudul");
        yartis = ambil.getStringExtra("xartis");
        yalbum = ambil.getStringExtra("xalbum");
        ygenre = ambil.getStringExtra("xgenre");
        ylirik = ambil.getStringExtra("xlirik");
        ylink = ambil.getStringExtra("xlink");

        etJudul = findViewById(R.id.et_ubah_judul);
        etArtis = findViewById(R.id.et_ubah_artis);
        etAlbum = findViewById(R.id.et_ubah_album);
        etGenre = findViewById(R.id.et_ubah_genre);
        etlirik = findViewById(R.id.et_ubah_lirik);
        etlink = findViewById(R.id.et_ubah_link);

        btnUbah = findViewById(R.id.btn_ubah_ubah);

        etJudul.setText(yjudul);
        etArtis.setText(yartis);
        etAlbum.setText(yalbum);
        etGenre.setText(ygenre);
        etlirik.setText(ylirik);
        etlink.setText(ylink);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                judul = etJudul.getText().toString();
                artis = etArtis.getText().toString();
                album = etAlbum.getText().toString();
                genre = etGenre.getText().toString();
                lirik = etlirik.getText().toString();
                link = etlink.getText().toString();

                if (judul.trim().isEmpty()){
                    etJudul.setError("Judul lagu tidak boleh kosong");
                } else if (artis.trim().isEmpty()) {
                    etArtis.setError("Nama Artis/band tidak boleh kosong");
                } else if (genre.trim().isEmpty()) {
                    etGenre.setError("Genre lagu harus diisi");
                } else if (lirik.trim().isEmpty()) {
                    etlirik.setError("Lirik lagu harus diisi");
                } else {
                    ubahMusik();
                }
            }
        });
    }

    private void ubahMusik(){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardUpdate(yid, judul, artis, album, genre, lirik, link);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(Ubah.this, "Data berhasil diubah", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(Ubah.this, "Gagal melakukan perubahan", Toast.LENGTH_SHORT).show();
            }
        });
    }
}