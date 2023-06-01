package com.example.spectacole_iss.repository;

import com.example.spectacole_iss.model.Spectacol;
import com.example.spectacole_iss.utils.JDBCUtils;
import org.hibernate.procedure.internal.ProcedureParamBindings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SpectacolDBRepository {

    private JDBCUtils jdbcUtils;

    public SpectacolDBRepository(Properties properties) {
        this.jdbcUtils = new JDBCUtils(properties);

    }

    public Spectacol adaugaSpectacol(Spectacol spectacol) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select * from spectacole where nume=? or start LIKE ?"))
        {
            preparedStatement.setString(1, spectacol.getNume());
            preparedStatement.setString(2, spectacol.getStart().split(";")[0] + ";%");
            ResultSet result = preparedStatement.executeQuery();
            if(result.next()) return null;
        }catch (SQLException e)
        {
            System.out.println(e);
        }
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "insert into spectacole(durata,locuri, descriere, gen, nume, start) values (?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, spectacol.getDurata());
            preparedStatement.setInt(2, spectacol.getNumar_locuri());
            preparedStatement.setString(3, spectacol.getDescriere());
            preparedStatement.setString(4, String.valueOf(spectacol.getGen()));
            preparedStatement.setString(5, spectacol.getNume());
            preparedStatement.setString(6, spectacol.getStart());
            int result = preparedStatement.executeUpdate();
            if (result == 0) return null;
            return spectacol;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Spectacol modificaSpectacol(Spectacol spectacol, String newStart) {
        Connection connection = jdbcUtils.getConnection();
        int id = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "select id from spectacole where durata=? AND locuri=? AND descriere=? AND gen=? AND nume=? AND start=?")) {
            preparedStatement.setInt(1, spectacol.getDurata());
            preparedStatement.setInt(2, spectacol.getNumar_locuri());
            preparedStatement.setString(3, spectacol.getDescriere());
            preparedStatement.setString(4, String.valueOf(spectacol.getGen()));
            preparedStatement.setString(5, spectacol.getNume());
            preparedStatement.setString(6, spectacol.getStart());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        if (id == 0) return null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "update spectacole set start = ? where id = ?")) {
            preparedStatement.setString(1, newStart);
            preparedStatement.setInt(2,id);
            int result = preparedStatement.executeUpdate();
            if (result == 0) return null;
            return spectacol;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public Iterable<Spectacol> getAll() {
        Connection connection = jdbcUtils.getConnection();
        List<Spectacol> spectacole = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select id, durata, nume, descriere, start, gen, locuri from spectacole"))
        {
            try(ResultSet resultSet = preparedStatement.executeQuery())
            {
                while(resultSet.next())
                {
                    int id = resultSet.getInt(1);
                    int durata = resultSet.getInt(2);
                    String nume = resultSet.getString(3);
                    String descriere = resultSet.getString(4);
                    String start = resultSet.getString(5);
                    Spectacol.Gen gen = Spectacol.Gen.valueOf(resultSet.getString(6));
                    int locuri = resultSet.getInt(7);
                    Spectacol spectacol = new Spectacol(id, nume, durata, start, locuri, descriere, gen);
                    spectacole.add(spectacol);
                }
                return spectacole;
            }
        }catch (SQLException e)
        {
            System.out.println(e);
        }
        return spectacole;
    }

    public List<Spectacol>spectacolePeZile(String dataSpectacol)
    {
        Connection connection = jdbcUtils.getConnection();
        List<Spectacol> spectacole = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement("select id, durata,nume, descriere, start, gen, locuri  from spectacole where start like ?"))
        {
            preparedStatement.setString(1, dataSpectacol + "%");
            try(ResultSet resultSet = preparedStatement.executeQuery())
            {
                while (resultSet.next())
                {
                    int id = resultSet.getInt(1);
                    int durata = resultSet.getInt(2);
                    String nume = resultSet.getString(3);
                    String descriere = resultSet.getString(4);
                    String start = resultSet.getString(5);
                    Spectacol.Gen gen = Spectacol.Gen.valueOf(resultSet.getString(6));
                    int locuri = resultSet.getInt(7);
                    Spectacol spectacol = new Spectacol(id, nume, durata, start, locuri, descriere, gen);
                    spectacole.add(spectacol);
                }
                return spectacole;
            }
        }catch(SQLException sqlException)
        {
            System.out.println(sqlException);
        }
        return spectacole;
    }

    public Spectacol stergeSpectacol(Spectacol spectacol) {
        Connection connection = jdbcUtils.getConnection();
        try(PreparedStatement preparedStatement = connection.prepareStatement("delete from spectacole where nume = ? and start=?"))
        {
            preparedStatement.setString(1, spectacol.getNume());
            preparedStatement.setString(2, spectacol.getStart());
            int result = preparedStatement.executeUpdate();
            return  spectacol;
        }catch (SQLException sqlException)
        {
            System.out.println(sqlException);
        }
        return null;
    }
}
