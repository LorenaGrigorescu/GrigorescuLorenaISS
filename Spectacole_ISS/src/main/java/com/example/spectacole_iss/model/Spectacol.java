package com.example.spectacole_iss.model;


import java.util.Objects;

public class Spectacol {
    public enum Gen {DRAMA, AVENTURA, ROMANTA, COMEDIE, HORROR, SF}

    private int id;

    private String nume;

    private int durata;

    private String start;

    private int numar_locuri;

    private String descriere;

    private Gen gen;

    public Spectacol(int id, String nume, int durata, String start, int numar_locuri, String descriere, Gen gen) {
        this.id = id;
        this.nume = nume;
        this.durata = durata;
        this.start = start;
        this.numar_locuri = numar_locuri;
        this.descriere = descriere;
        this.gen = gen;
    }

    public Spectacol() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public int getNumar_locuri() {
        return numar_locuri;
    }

    public void setNumar_locuri(int numar_locuri) {
        this.numar_locuri = numar_locuri;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Gen getGen() {
        return gen;
    }

    public void setGen(Gen gen) {
        this.gen = gen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Spectacol spectacol = (Spectacol) o;
        return id == spectacol.id && durata == spectacol.durata && numar_locuri == spectacol.numar_locuri && Objects.equals(nume, spectacol.nume) && Objects.equals(start, spectacol.start) && Objects.equals(descriere, spectacol.descriere) && gen == spectacol.gen;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nume, durata, start, numar_locuri, descriere, gen);
    }

    @Override
    public String toString() {
        return "Spectacol{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", durata=" + durata +
                ", start='" + start + '\'' +
                ", numar_locuri=" + numar_locuri +
                ", descriere='" + descriere + '\'' +
                ", gen=" + gen +
                '}';
    }
}
