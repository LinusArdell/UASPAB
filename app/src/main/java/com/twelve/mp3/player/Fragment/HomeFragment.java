package com.twelve.mp3.player.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
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

public class HomeFragment extends Fragment {

    private RecyclerView rvMusik;
    private ProgressBar pb12mp3;
    private AdapterMusik adMusik;
    private List<ModelMusik> listMusik = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        rvMusik = view.findViewById(R.id.rv_12mp3);
        pb12mp3 = view.findViewById(R.id.pb_12mp3);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvMusik.setLayoutManager(layoutManager);

        adMusik = new AdapterMusik(getActivity(), this, listMusik);
        rvMusik.setAdapter(adMusik);

        retrieveMusik();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveMusik();
    }

    public void retrieveMusik() {
        pb12mp3.setVisibility(View.VISIBLE);

        APIRequestData apiRequestData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ModelResponse> call = apiRequestData.ardRetrieve();

        call.enqueue(new Callback<ModelResponse>() {
            @Override
            public void onResponse(Call<ModelResponse> call, Response<ModelResponse> response) {
                String kode = response.body().getKode();
                String pesan = response.body().getPesan();
                listMusik = response.body().getData();

                adMusik.setMusikList(listMusik);
                adMusik.notifyDataSetChanged();

                pb12mp3.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ModelResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to connect to server", Toast.LENGTH_SHORT).show();
                pb12mp3.setVisibility(View.GONE);
            }
        });
    }
}
