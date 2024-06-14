package com.example.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/signin")
    public String getSignInPage() {
        return "signin";
    }

    @PostMapping("/sign-in")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> signIn(@RequestBody Map<String, String> credentials) {
        String userID = credentials.get("userID");
        String password = credentials.get("password");

        // 로그인 API 요청
        String url = "http://example.com/api/sign-in";
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("userID", userID);
        requestBody.put("password", password);

        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestBody, Map.class);
        return ResponseEntity.ok(response.getBody());
    }
}
