package com.example.spectacole_iss.model;

import java.util.Objects;

public class User {

    public enum Type {MANAGER, SPECTATOR}

    private int id;

    private String nume;

    private String username;

    private String parola;


    private Type type;

    public User(int id, String nume, String username, String parola, Type type) {
        this.id = id;
        this.nume = nume;
        this.username = username;
        this.parola = parola;
        this.type = type;
    }

    public User() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(nume, user.nume) && Objects.equals(username, user.username) && Objects.equals(parola, user.parola) && type == user.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nume, username, parola, type);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nume='" + nume + '\'' +
                ", username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                ", type=" + type +
                '}';
    }
}