package com.twelve.mp3.player.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.twelve.mp3.player.API.APIRequestData;
import com.twelve.mp3.player.API.RetroServer;
import com.twelve.mp3.player.Activity.Detail;
import com.twelve.mp3.player.Activity.MainActivity;
import com.twelve.mp3.player.Activity.Ubah;
import com.twelve.mp3.player.Model.ModelMusik;
import com.twelve.mp3.player.Model.ModelResponse;
import com.twelve.mp3.player.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterMusik extends RecyclerView.Adapter<AdapterMusik.VHMusik> {
    private Context ctx;
    private List<ModelMusik> listMusik;

    public AdapterMusik(Context ctx, List<ModelMusik> listMusik){
        this.ctx = ctx;
        this.listMusik = listMusik;
    }

    @NonNull
    @Override
    public VHMusik onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View varView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_musik, parent, false);
        return new VHMusik(varView);
    }

    @Override
    public void onBindViewHolder(@NonNull VHMusik holder, int position) {
        ModelMusik MM = listMusik.get(position);
        holder.tvId.setText(MM.getId());
        holder.tvJudul.setText(MM.getJudul());
        holder.tvArtis.setText(MM.getArtis());
        holder.tvAlbum.setText(MM.getAlbum());
        holder.tvGenre.setText(MM.getGenre());
        holder.tvLirik.setText(MM.getLirik());
        holder.tvLink.setText(MM.getLink());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String xArtis = MM.getArtis();
                String xJudul = MM.getJudul();
                String xAlbum = MM.getAlbum();
                String xLirik = MM.getLirik();
                String xLink = MM.getLink();

                Intent kirim = new Intent(ctx, Detail.class);
                kirim.putExtra("xArtis", xArtis);
                kirim.putExtra("xJudul", xJudul);
                kirim.putExtra("xAlbum", xAlbum);
                kirim.putExtra("xLirik", xLirik);
                kirim.putExtra("xLink", xLink);
                ctx.startActivity(kirim);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listMusik.size();
    }

    public class VHMusik extends RecyclerView.ViewHolder{
        TextView tvId, tvJudul, tvArtis, tvAlbum, tvGenre, tvLirik, tvLink;

        public VHMusik(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvArtis = itemView.findViewById(R.id.tv_artis);
            tvAlbum = itemView.findViewById(R.id.tv_album);
            tvGenre = itemView.findViewById(R.id.tv_genre);
            tvLirik = itemView.findViewById(R.id.tv_lirik);
            tvLink = itemView.findViewById(R.id.tv_link);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder pesan = new AlertDialog.Builder(ctx);
                    pesan.setTitle("Edit Tag");
                    pesan.setCancelable(true);

                    pesan.setNegativeButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            hapusMusik(tvId.getText().toString());
                            dialogInterface.dismiss();
                        }
                    });

                    pesan.setPositiveButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent pindah = new Intent(ctx, Ubah.class);
                            pindah.putExtra("xId", tvId.getText().toString());
                            pindah.putExtra("xjudul", tvJudul.getText().toString());
                            pindah.putExtra("xartis", tvArtis.getText().toString());
                            pindah.putExtra("xalbum", tvAlbum.getText().toString());
                            pindah.putExtra("xgenre", tvGenre.getText().toString());
                            pindah.putExtra("xlirik", tvLirik.getText().toString());
                            pindah.putExtra("xlink", tvLink.getText().toString());
                            ctx.startActivity(pindah);
                        }
                    });
                    pesan.show();
                    return false;
                }
            });
        }


    }

    private void hapusMusik(String idMusik) {
        APIRequestData ARD = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> proses = ARD.ardDelete(idMusik);

        proses.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(ctx, "Kode: " + kode + ", Pesan: " + pesan, Toast.LENGTH_SHORT).show();
                ((MainActivity) ctx).retrieveMusik();
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(ctx, "Silahkan refresh", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
