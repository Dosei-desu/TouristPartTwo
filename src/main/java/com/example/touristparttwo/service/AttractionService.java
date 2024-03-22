package com.example.touristparttwo.service;

import com.example.touristparttwo.model.Attraction;
import com.example.touristparttwo.model.Location;
import com.example.touristparttwo.model.Tag;
import com.example.touristparttwo.repository.AttractionDBRepository;
import com.example.touristparttwo.repository.AttractionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttractionService {
    private final AttractionDBRepository attractionRepository;

    public AttractionService(AttractionDBRepository attractionRepository){
        this.attractionRepository = attractionRepository;
    }

    public List<Attraction> getAttractions() {
        return attractionRepository.getAllAttractions();
    }

    public Attraction getAttractionByName(String name){
        return attractionRepository.getAttractionByName(name);
    }

    public void addAttraction(Attraction attraction){
        attractionRepository.addAttraction(attraction);
    }

    public void updateAttraction(Attraction attraction){
        attractionRepository.updateAttraction(attraction);
    }

    public void deleteAttractionByName(String name){
        attractionRepository.deleteAttractionByName(name);
    }

    public List<String> getTags(){
        return attractionRepository.getTags();
    }


    public List<String> getLocations(){
        return attractionRepository.getLocations();
    }
}
