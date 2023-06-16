package com.twelve.mp3.player.Activity;

import androidx.appcompat.app.AppCompatActivity;

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

public class Tambah extends AppCompatActivity {
    private EditText etJudul, etArtis, etAlbum, etGenre, etlirik, etLink;
    private Button btnTambah;
    private String judul, artis, album, genre, lirik, link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etJudul = findViewById(R.id.et_tambah_judul);
        etArtis = findViewById(R.id.et_tambah_artis);
        etAlbum = findViewById(R.id.et_tambah_album);
        etGenre = findViewById(R.id.et_tambah_genre);
        etlirik = findViewById(R.id.et_tambah_lirik);
        etLink = findViewById(R.id.et_tambah_link);

        btnTambah = findViewById(R.id.btn_tambah_tambah);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                judul = etJudul.getText().toString();
                artis = etArtis.getText().toString();
                album = etAlbum.getText().toString();
                genre = etGenre.getText().toString();
                lirik = etlirik.getText().toString();
                link = etLink.getText().toString();

                if (judul.trim().isEmpty()){
                    etJudul.setError("Judul lagu tidak boleh kosong");
                } else if (artis.trim().isEmpty()) {
                    etArtis.setError("Nama Artis/band tidak boleh kosong");
                } else if (genre.trim().isEmpty()) {
                   etGenre.setError("Genre lagu harus diisi");
                } else if (lirik.trim().isEmpty()) {
                    etlirik.setError("Lirik lagu harus diisi");
                } else {
                    tambahMusik();
                }
            }
        });
    }

    private void tambahMusik(){
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardCreate(judul, artis, album, genre, lirik, link);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(Tambah.this, "Berhasil ditambahkan kedalam koleksi", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(Tambah.this, "Failed to connect to server", Toast.LENGTH_SHORT).show();
            }
        });
    }
}