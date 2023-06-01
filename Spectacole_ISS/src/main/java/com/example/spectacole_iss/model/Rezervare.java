package com.example.spectacole_iss.model;

import java.util.Objects;

public class Rezervare {
    private String numarTelefon;
    private String email;
    private int numarLocuri;
    private String dataSpectacol;

    public Rezervare(String numarTelefon, String email, int numarLocuri, String dataSpectacol) {
        this.numarTelefon = numarTelefon;
        this.email = email;
        this.numarLocuri = numarLocuri;
        this.dataSpectacol = dataSpectacol;
    }

    public String getNumarTelefon() {
        return numarTelefon;
    }

    public void setNumarTelefon(String numarTelefon) {
        this.numarTelefon = numarTelefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumarLocuri() {
        return numarLocuri;
    }

    public void setNumarLocuri(int numarLocuri) {
        this.numarLocuri = numarLocuri;
    }

    public String getDataSpectacol() {
        return dataSpectacol;
    }

    public void setDataSpectacol(String dataSpectacol) {
        this.dataSpectacol = dataSpectacol;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rezervare rezervare = (Rezervare) o;
        return numarLocuri == rezervare.numarLocuri && Objects.equals(numarTelefon, rezervare.numarTelefon) && Objects.equals(email, rezervare.email) && Objects.equals(dataSpectacol, rezervare.dataSpectacol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numarTelefon, email, numarLocuri, dataSpectacol);
    }

    @Override
    public String toString() {
        return "Rezervare{" +
                "numarTelefon='" + numarTelefon + '\'' +
                ", email='" + email + '\'' +
                ", numarLocuri=" + numarLocuri +
                ", dataSpectacol='" + dataSpectacol + '\'' +
                '}';
    }
}
