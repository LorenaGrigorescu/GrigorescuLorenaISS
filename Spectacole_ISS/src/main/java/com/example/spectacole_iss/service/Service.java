package com.example.spectacole_iss.service;

import com.example.spectacole_iss.model.Bilet;
import com.example.spectacole_iss.model.Rezervare;
import com.example.spectacole_iss.model.Spectacol;
import com.example.spectacole_iss.model.User;
import com.example.spectacole_iss.repository.SpectacolDBRepository;
import com.example.spectacole_iss.repository.UserDBRepository;

import java.util.List;

public class Service {
    SpectacolDBRepository spectacolRepository ;
    UserDBRepository userRepository ;

    public Service(SpectacolDBRepository spectacolRepository, UserDBRepository userRepository) {
        this.spectacolRepository = spectacolRepository;
        this.userRepository = userRepository;
    }

    public User adaugaUser(User user)
    {
        return this.userRepository.adaugaUser(user);
    }

    public User cautaUser(String username, String parola, User.Type type)
    {
        return this.userRepository.cautaUser(username, parola, type);
    }

    public Spectacol adaugaSpectacol(Spectacol spectacol)
    {
        return this.spectacolRepository.adaugaSpectacol(spectacol);
    }

    public Spectacol modificaSpectacol(Spectacol spectacol, String newStart)
    {
        return this.spectacolRepository.modificaSpectacol(spectacol, newStart);
    }

    public Iterable<Spectacol> getAll() {
        return this.spectacolRepository.getAll();
    }

    public List<Spectacol> spectacolePeZile(String dataSpectacol)
    {
        return this.spectacolRepository.spectacolePeZile(dataSpectacol);
    }

    public Bilet cumaparaBilet(int idCumparator, Bilet bilet)
    {
        return this.userRepository.cumparaBilet(idCumparator, bilet);
    }
    public Rezervare modificareLocuriRezervare(Rezervare rezervare, int newNumarLocuri, int loggedUSerID)
    {
        return this.userRepository.modificaLocuriRezervare(rezervare, newNumarLocuri, loggedUSerID);

    }

    public List<Rezervare> getAllRezervari(int loggedUserID)
    {
        return this.userRepository.getAllRezervari(loggedUserID);
    }

    public Rezervare stergeRezervare(Rezervare rezervare, int loggedUserID) {
        return this.userRepository.stergeRezervare(rezervare, loggedUserID);
    }

    public Spectacol stergeSpectacol(Spectacol spectacol) {
        return  this.spectacolRepository.stergeSpectacol(spectacol);
    }
}
