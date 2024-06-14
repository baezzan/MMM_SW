package com.example.map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Controller
public class MapController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/map")
    public String getMapPage() {
        return "map";
    }

    @GetMapping("/restaurants")
    @ResponseBody
    public List<Map<String, Object>> getRestaurants(@RequestParam(required = false) String category) {
        String url = "http://example.com/api/restaurants?category=" + (category != null ? category : "");
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (List<Map<String, Object>>) response.get("result");
    }
}
