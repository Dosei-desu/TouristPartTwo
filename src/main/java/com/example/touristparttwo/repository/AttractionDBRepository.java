package com.example.touristparttwo.repository;

import com.example.touristparttwo.model.Attraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AttractionDBRepository {
    @Value("jdbc:mysql://firstsqlserver.mysql.database.azure.com:3306/attraction")
    private String db_url;
    @Value("Juicer")
    private String user;
    @Value("yuuZug4ii")
    private String pass;

    public List<Attraction> getAllAttractions(){
        List<Attraction> attractions = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return attractions;
    }
}
