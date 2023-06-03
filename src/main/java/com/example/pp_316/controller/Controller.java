package com.example.pp_316.controller;

import com.example.pp_316.entity.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;


@RestController
public class Controller {
    private final RestTemplate restTemplate;
    private final String url = "http://94.198.50.185:7081/api/users";
    private HttpHeaders httpHeaders = new HttpHeaders();

    public Controller(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setHttpHeaders(String cookie) {
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Cookie", cookie);
    }

    @GetMapping(value = "/")
    public String getCode() {
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        setHttpHeaders(responseEntity.getHeaders().getFirst("Set-cookie"));
        return postUser().concat(updateUser()).concat(deleteUser());
    }

    public String postUser() {
        User user = new User(3, "James", "Brown", 126);
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    }

    public String updateUser() {
        User user = new User(3, "Thomas", "Shelby", 126);
        HttpEntity<User> entity = new HttpEntity<>(user, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.PUT, entity, String.class).getBody();
    }

    public String deleteUser() {
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        return restTemplate.exchange(url.concat("/3"), HttpMethod.DELETE, entity, String.class).getBody();
    }

}
