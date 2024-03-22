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
                        new ArrayList<>(List.of(
                                resultSet.getString("tag").split(",")))
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

            String SQL = "SELECT a_name,a_description,city, " +
                    "GROUP_CONCAT(tag SEPARATOR ', ') AS 'tags' " +
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
                        new ArrayList<>(List.of(
                                resultSet.getString("tag").split(",")))
                );
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return attraction;
    }


    public void addAttraction(Attraction attraction){
        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {
            String SQL = "INSERT INTO attraction(a_name,a_description,city) " +
                    "VALUES (?,?,?);";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1,attraction.getName());
            ps.setString(2,attraction.getDescription());
            ps.setString(3,getLocationNumberFromName(attraction.getLocation()));

            ps.executeUpdate();

            for (String tag: attraction.getTags()) {

                SQL = "INSERT INTO attractiontags " +
                        "VALUES (?,?);";
                ps = connection.prepareStatement(SQL);
                ps.setString(1,getAttractionNumberFromName(attraction.getName()));
                ps.setString(2,getTagNumberFromName(tag));

                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAttraction(Attraction attraction){
        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {
            String SQL = "UPDATE attraction " +
                    "SET a_name = ?, a_description = ?, city_id = ? " +
                    "WHERE id = ?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1,attraction.getName());
            ps.setString(2,attraction.getDescription());
            ps.setString(3,getLocationNumberFromName(attraction.getLocation()));
            ps.setString(4,getAttractionNumberFromName(attraction.getName()));

            ps.executeUpdate();

            SQL = "DELETE FROM attractiontags WHERE a_id = ?;";
            ps = connection.prepareStatement(SQL);
            ps.setString(1,getAttractionNumberFromName(attraction.getName()));

            ps.executeUpdate();

            for (String tag: attraction.getTags()) {

                SQL = "INSERT INTO attractiontags " +
                        "VALUES (?,?);";
                ps = connection.prepareStatement(SQL);
                ps.setString(1,getAttractionNumberFromName(attraction.getName()));
                ps.setString(2,getTagNumberFromName(tag));

                ps.executeUpdate();
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAttractionByName(String name){
        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {
            String SQL = "DELETE FROM attraction WHERE a_id = ?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1,getAttractionNumberFromName(name));

            ps.executeUpdate();

            SQL = "DELETE FROM attractiontags WHERE a_id = ?;";
            ps = connection.prepareStatement(SQL);
            ps.setString(1,getAttractionNumberFromName(name));

            ps.executeUpdate();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getTags(){
        List<String> tags = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {
            Statement statement = connection.createStatement();

            String SQL = "SELECT tag FROM tag;";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()){
                tags.add(resultSet.getString("tag"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tags;
    }

    public List<String> getLocations(){
        List<String> locations = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {
            Statement statement = connection.createStatement();

            String SQL = "SELECT city FROM location;";
            ResultSet resultSet = statement.executeQuery(SQL);

            while(resultSet.next()){
                locations.add(resultSet.getString("city"));
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locations;
    }

    //helper method to find the ID associated with an Attraction
    public String getAttractionNumberFromName(String name){
        int number = 0;

        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {

            String SQL = "SELECT ID FROM attraction WHERE a_name = ?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1,name);

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                number = resultSet.getInt("ID");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(number);
    }

    //helper method to find the ID associated with a Location
    public String getLocationNumberFromName(String name){
        int number = 0;

        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {

            String SQL = "SELECT ID FROM location WHERE city = ?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1,name);

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                number = resultSet.getInt("ID");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(number);
    }

    //helper method to find the ID associated with a tag
    public String getTagNumberFromName(String name){
        int number = 0;

        try (Connection connection = DriverManager.getConnection(db_url,user,pass))
        {

            String SQL = "SELECT ID FROM tag WHERE tag = ?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1,name);

            ResultSet resultSet = ps.executeQuery();

            if(resultSet.next()){
                number = resultSet.getInt("ID");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return String.valueOf(number);
    }
}
