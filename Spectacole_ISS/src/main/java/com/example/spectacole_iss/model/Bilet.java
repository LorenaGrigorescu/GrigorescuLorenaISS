package com.example.spectacole_iss.model;

import java.time.LocalDate;

public class Bilet {
    private int rand;
    private String loja;
    private int pret;
    private LocalDate dataSpectacol;
    private LocalDate dataAchizitie;

    public Bilet(int rand, String loja, int pret, LocalDate dataSpectacol, LocalDate dataAchizitie) {
        this.rand = rand;
        this.loja = loja;
        this.pret = pret;
        this.dataSpectacol = dataSpectacol;
        this.dataAchizitie = dataAchizitie;
    }

    public int getRand() {
        return rand;
    }

    public void setRand(int rand) {
        this.rand = rand;
    }

    public String getLoja() {
        return loja;
    }

    public void setLoja(String loja) {
        this.loja = loja;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public LocalDate getDataSpectacol() {
        return dataSpectacol;
    }

    public void setDataSpectacol(LocalDate dataSpectacol) {
        this.dataSpectacol = dataSpectacol;
    }

    public LocalDate getDataAchizitie() {
        return dataAchizitie;
    }

    public void setDataAchizitie(LocalDate dataAchizitie) {
        this.dataAchizitie = dataAchizitie;
    }
}
