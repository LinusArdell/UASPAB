package com.twelve.mp3.player.Model;

import java.util.List;

public class ModelResponse {
    private String kode, pesan;
    private List<ModelMusik> data;
    private List<ModelPengguna> dataPengguna;
    public String getPesan() {return pesan;}

    public String getKode() {return kode;}

    public List<ModelMusik> getData() {
        return data;
    }
    public List<ModelPengguna> getDataPengguna() {
        return dataPengguna;
    }
}
