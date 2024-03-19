package com.example.touristparttwo.repository;

import com.example.touristparttwo.model.Attraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttractionDBRepository {
    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String pass;

    public List<Attraction> getAllAttractions(){
        List<Attraction> attractions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {
            Statement statement = connection.createStatement();

            String SQL = "SELECT a_name,a_description,city, GROUP_CONCAT(tag SEPARATOR ', ') AS 'tags' " +
                    "FROM attraction JOIN location ON CITY_ID = location.ID " +
                    "LEFT JOIN attractiontags ON attraction.id = a_id " +
                    "LEFT JOIN tag ON tag.id = t_id " +
                    "GROUP BY attraction.id;";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()){
                attractions.add(new Attraction(
                        resultSet.getString("a_name"),
                        resultSet.getString("a_description"),
                        resultSet.getString("city"),
                        resultSet.getString("tag").split(",")
                ));
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return attractions;
    }

    public Attraction getAttractionByName(String name){
        Attraction attraction = null;

        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {

            String SQL = "SELECT a_name,a_description,city, GROUP_CONCAT(tag SEPARATOR ', ') AS 'tags' " +
                    "FROM attraction JOIN location ON CITY_ID = location.ID " +
                    "LEFT JOIN attractiontags ON attraction.id = a_id " +
                    "LEFT JOIN tag ON tag.id = t_id " +
                    "WHERE a_name LIKE ?" +
                    "GROUP BY attraction.id;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1,"%"+name+"%");

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                attraction = new Attraction(
                        resultSet.getString("a_name"),
                        resultSet.getString("a_description"),
                        resultSet.getString("city"),
                        resultSet.getString("tag").split(",")
                );
            }

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attraction;
    }
}
