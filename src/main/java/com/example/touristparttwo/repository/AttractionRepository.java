package com.example.touristparttwo.repository;

import com.example.touristparttwo.model.Attraction;
import com.example.touristparttwo.model.Location;
import com.example.touristparttwo.model.Tag;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AttractionRepository {
    private List<Attraction> attractions;

/*
    public AttractionRepository(){
        attractions = new ArrayList<>(List.of(
                new Attraction("Zoo","They have pandas!", Location.COPENHAGEN.getName(),
                        List.of(
                                Tag.ANIMALS.getName(),
                                Tag.ENTERTAINMENT.getName(),
                                Tag.INDOORS.getName(),
                                Tag.OUTDOORS.getName())),

                new Attraction("Thorvaldsen's Museum","Sculpture artwork galore",
                        Location.COPENHAGEN.getName(),
                        List.of(
                                Tag.ART.getName(),
                                Tag.MUSEUM.getName(),
                                Tag.INDOORS.getName())),

                    new Attraction("Frederiksborg Slot","A fancy old palace with " +
                        "a large garden",Location.HILLERØD.getName(),
                        List.of(
                                Tag.ART.getName(),
                                Tag.MUSEUM.getName(),
                                Tag.INDOORS.getName(),
                                Tag.OUTDOORS.getName())),

                new Attraction("LEGOLAND","LEGO-based amusement park",
                        Location.BILLUND.getName(),
                        List.of(
                                Tag.RIDES.getName(),
                                Tag.ENTERTAINMENT.getName(),
                                Tag.INDOORS.getName(),
                                Tag.OUTDOORS.getName())),

                new Attraction("Jellinge Stones","Historical artefacts from the " +
                        "Viking age",Location.JELLINGE.getName(),
                        List.of(
                                Tag.ART.getName(),
                                Tag.MUSEUM.getName(),
                                Tag.HISTORICAL.getName(),
                                Tag.OUTDOORS.getName())),

                new Attraction("Roskilde Cathedral","The resting place of Danish " +
                        "Royalty",Location.ROSKILDE.getName(),
                        List.of(
                                Tag.RELIGION.getName(),
                                Tag.HISTORICAL.getName(),
                                Tag.INDOORS.getName()))
        ));
    }

 */

    public List<Tag> getTags(){
        return new ArrayList<>(List.of(Tag.values()));
    }

    public List<String> getTagsName(){
        List<String> stringList = new ArrayList<>();
        for (Tag tag: Tag.values()) {
            stringList.add(tag.getName());
        }
        return stringList;
    }

    public List<Location> getLocations(){
        return new ArrayList<>(List.of(Location.values()));
    }

    public List<Attraction> getAttractions() {
        return attractions;
    }

    public Attraction getAttractionByName(String name){
        for (Attraction attr: attractions){
            if (attr.getName().equalsIgnoreCase(name)){
                return attr;
            }
        }
        return null;
    }

    public void addAttraction(Attraction attraction){
        boolean flag = false;
        for (Attraction attr: attractions) {
            if (attr.getName().equalsIgnoreCase(attraction.getName())){
                flag = true;
            }
        }
        if (!flag){
            attractions.add(attraction);
        }
    }

    public void updateAttraction(Attraction attraction){
        for (int i = 0; i < attractions.size(); i++) {
            if (attractions.get(i).getName().equalsIgnoreCase(attraction.getName())){
                attractions.set(i,attraction);
                return; //search algo ends here to save some time
            }
        }
    }

    public void deleteAttractionByName(String name){
        for (int i = 0; i < attractions.size(); i++) {
            if (attractions.get(i).getName().equalsIgnoreCase(name)){
                attractions.remove(i);
                return; //search algo ends here to save some time
            }
        }
    }

    public void deleteAttraction(Attraction attraction){
        for (int i = 0; i < attractions.size(); i++) {
            if (attractions.get(i).getName().equalsIgnoreCase(attraction.getName())){
                attractions.remove(attraction);
                return; //search algo ends here to save some time
            }
        }
    }
}
