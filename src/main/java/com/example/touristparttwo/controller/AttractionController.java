package com.example.touristparttwo.controller;

import com.example.touristparttwo.model.Attraction;
import com.example.touristparttwo.model.Location;
import com.example.touristparttwo.model.Tag;
import com.example.touristparttwo.service.AttractionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/attractions")
public class AttractionController {
    private final AttractionService attractionService;

    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping("")
    public String getAttractions(Model model){
        List<Attraction> attractions = attractionService.getAttractions();
        model.addAttribute("attractions",attractions);
        return "attractions";
    }

    @GetMapping("/{name}/tags")
    public String getAttractionTags(@PathVariable String name, Model model){
        List<String> attractionTags;
        if(attractionService.getAttractionByName(name).getTags() != null){
            attractionTags = attractionService.getAttractionByName(name).getTags();
        } else {
            attractionTags = new ArrayList<>(List.of("No Tags"));
        }
        model.addAttribute("attractionTags",attractionTags);
        model.addAttribute("attractionName",name);
        return "attractionTags";
    }

    @GetMapping("/add")
    public String createAttraction(Model model){
        List<String> locations = attractionService.getLocations();
        List<String> tags = attractionService.getTags();
        model.addAttribute("attraction",new Attraction());
        model.addAttribute("locations",locations);
        model.addAttribute("tags",tags);
        return "addAttraction";
    }

    @PostMapping("/add")
    public String addAttraction(@ModelAttribute Attraction attraction){
        attractionService.addAttraction(attraction);
        return "redirect:/attractions";
    }

    @GetMapping("/{name}/update")
    public String updateById(@PathVariable String name, Model model){
        Attraction attraction = attractionService.getAttractionByName(name);
        List<String> locations = attractionService.getLocations();
        List<String> tags = attractionService.getTags();
        model.addAttribute("attraction",attraction);
        model.addAttribute("locations",locations);
        model.addAttribute("tags",tags);
        return "updateAttraction";
    }

    @PostMapping("/update")
    public String updateAttraction(@ModelAttribute Attraction attraction){
        attractionService.updateAttraction(attraction);
        return "redirect:/attractions";
    }

    @GetMapping("/{name}/delete")
    public String deleteAttraction(@PathVariable String name){
        attractionService.deleteAttractionByName(name);
        return "redirect:/attractions";
    }
}
