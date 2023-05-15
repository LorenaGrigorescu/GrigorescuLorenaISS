package com.example.spectacole_iss.service;

import com.example.spectacole_iss.model.Spectacol;
import com.example.spectacole_iss.model.User;
import com.example.spectacole_iss.repository.SpectacolDBRepository;
import com.example.spectacole_iss.repository.UserDBRepository;

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
}
