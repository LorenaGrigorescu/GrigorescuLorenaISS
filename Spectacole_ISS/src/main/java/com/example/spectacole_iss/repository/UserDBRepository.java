package com.example.spectacole_iss.repository;

import com.example.spectacole_iss.model.Bilet;
import com.example.spectacole_iss.model.Rezervare;
import com.example.spectacole_iss.model.User;
import com.example.spectacole_iss.utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserDBRepository {

    private JDBCUtils jdbcUtils;

    public UserDBRepository(Properties properties) {
        this.jdbcUtils = new JDBCUtils(properties);
    }

    public User adaugaUser(User user) {
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into users(nume, parola, type, username) values(?, ?, ?, ?)"
        )) {
            preparedStatement.setString(1, user.getNume());
            preparedStatement.setString(2, user.getParola());
            preparedStatement.setString(3, user.getType().toString());
            preparedStatement.setString(4, user.getUsername());
            int result = preparedStatement.executeUpdate();
            if (result == 0) return null;
            return user;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public User cautaUser(String username, String parola, User.Type type) {
        Connection connection = jdbcUtils.getConnection();

        try (PreparedStatement preparedStatement
                     = connection.prepareStatement("select id, nume, username, parola, type from users where username=? and parola=? and type=?")) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, parola);
            preparedStatement.setString(3, String.valueOf(type));
            ResultSet resultSet = preparedStatement.executeQuery();
//            if (!resultSet.isBeforeFirst()) return null;
            int id = -1;
            String nume = null;
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                nume = resultSet.getString(2);
                username = resultSet.getString(3);
                parola = resultSet.getString(4);
                type = User.Type.valueOf(resultSet.getString(5));
            }
            return new User(id, nume, username, parola, type);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Bilet cumparaBilet(int idCumparator, Bilet bilet) {
        Connection connection = jdbcUtils.getConnection();
        int numarLocuri = -1;
        try (PreparedStatement preparedStatement1 = connection.
                prepareStatement("select locuri from spectacole where start like ?")) {
            preparedStatement1.setString(1, bilet.getDataSpectacol().toString() + "%");
            ResultSet resultSet = preparedStatement1.executeQuery();
            while (resultSet.next()) {
                numarLocuri = resultSet.getInt(1);
            }

        } catch (SQLException exp) {
            System.out.println(exp);
        }
        try (PreparedStatement preparedStatement2 = connection.
                prepareStatement("update spectacole set locuri = ? where start like ?")) {
            preparedStatement2.setInt(1, numarLocuri - 1);
            preparedStatement2.setString(2, bilet.getDataSpectacol().toString() + "%");
            preparedStatement2.executeUpdate();
        } catch (SQLException exp) {
            System.out.println(exp);
        }
        try (PreparedStatement preparedStatement = connection.
                prepareStatement("insert into bilete (rand, loja, pret," +
                        " data_spectacol, data_achizitie, id_cumparator) values " +
                        "(?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, bilet.getRand());
            preparedStatement.setString(2, bilet.getLoja());
            preparedStatement.setInt(3, bilet.getPret());
            preparedStatement.setString(4, bilet.getDataSpectacol().toString());
            preparedStatement.setString(5, bilet.getDataAchizitie().toString());
            preparedStatement.setInt(6, idCumparator);
            int result = preparedStatement.executeUpdate();
            if (result == 0) return null;
            return bilet;
        } catch (SQLException exception) {
            System.out.println(exception);
        }
        return null;
    }

    public Rezervare modificaLocuriRezervare(Rezervare rezervare, int newNumarLocuri, int loggedUserID) {
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("update rezervari set locuri=? where data_spectacol = ? and id_spectator = ?")) {
            preparedStatement.setInt(1, newNumarLocuri);
            preparedStatement.setString(2, rezervare.getDataSpectacol());
            preparedStatement.setInt(3, loggedUserID);
            preparedStatement.executeUpdate();
            rezervare.setNumarLocuri(newNumarLocuri);
            return rezervare;
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return null;
    }

    public List<Rezervare> getAllRezervari(int loggedUserID) {
        List<Rezervare> rezervari = new ArrayList<>();
        Connection connection = jdbcUtils.getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement("select numar_telefon, email, data_spectacol, locuri from rezervari where id_spectator = ?")) {
            preparedStatement.setInt(1, loggedUserID);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String numarTelefon = resultSet.getString(1);
                String email = resultSet.getString(2);
                String dataSpectacol = resultSet.getString(3);
                int numarLocuri = resultSet.getInt(4);
                Rezervare rezervare = new Rezervare(numarTelefon, email, numarLocuri, dataSpectacol);
                rezervari.add(rezervare);
            }
            return rezervari;
        } catch (SQLException sqlException) {
            System.out.println(sqlException);
        }
        return rezervari;
    }

    public Rezervare stergeRezervare(Rezervare rezervare, int loggedUserID) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from rezervari where id_spectator = ? and data_spectacol = ?"))
        {
            preparedStatement.setInt(1, loggedUserID);
            preparedStatement.setString(2,rezervare.getDataSpectacol());
            int result = preparedStatement.executeUpdate();
            return rezervare;
        }catch (SQLException sqlException)
        {
            System.out.println(sqlException);
        }
        return null;
    }
}
