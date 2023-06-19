package com.twelve.mp3.player.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.twelve.mp3.player.R;

public class Detail extends AppCompatActivity {
    private FrameLayout lirik;
    private BottomSheetBehavior bsbLirik;
//    private ImageView ivFoto;
    private YouTubePlayerView ypMusik;
    private TextView tvArtis, tvJudul, tvAlbum, tvLirik, tvLink;
    private String tArtis, tJudul, tAlbum, tLirik, tLink;
    private Button btnStop;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

//        ivFoto = findViewById(R.id.iv_foto);
        ypMusik = findViewById(R.id.yp_musik);
        tvArtis = findViewById(R.id.tv_artis);
        tvJudul = findViewById(R.id.tv_judul);
        tvAlbum = findViewById(R.id.tv_album);
        tvLirik = findViewById(R.id.tv_lirik);
        tvLink = findViewById(R.id.tv_link);
        btnStop = findViewById(R.id.btn_stop);

        Intent terima = getIntent();
//        ypMusik = terima.getStringExtra("xLink");
        tArtis = terima.getStringExtra("xArtis");
        tJudul = terima.getStringExtra("xJudul");
        tAlbum = terima.getStringExtra("xAlbum");
        tLirik = terima.getStringExtra("xLirik");
        tLink = terima.getStringExtra("xLink");

        tvArtis.setText(tArtis);
        tvJudul.setText(tJudul);
        tvAlbum.setText(tAlbum);
        tvLirik.setText(tLirik);
        tvLink.setText(tLink);

        ypMusik.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.cueVideo(tLink, 0);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ypMusik.release();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ypMusik.release();
    }
}