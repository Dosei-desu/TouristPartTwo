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

        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return attractions;
    }
}
