package com.example.Backend.searchpackage;
import com.example.Backend.classes.Technician;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.exceptions.MeilisearchException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class searchClient {
    static String meiliSearchUrl="http://localhost:7700";
    static String masterKey="CwX3aqNGjXLkSyRt7bF7PfCjkALlB9G8XRQAoWjXrNg";
    private static final Config config =new Config("http://localhost:7700", "CwX3aqNGjXLkSyRt7bF7PfCjkALlB9G8XRQAoWjXrNg");
    private static final Client client=new Client(config);
    private static final String INDEX_NAME = "tech";
    public static void addTechnicians(List<Technician> technicians) throws MeilisearchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String jsonPayload = objectMapper.writeValueAsString(technicians);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer CwX3aqNGjXLkSyRt7bF7PfCjkALlB9G8XRQAoWjXrNg");

        // Prepare HTTP entity
        HttpEntity<String> entity = new HttpEntity<>(jsonPayload, headers);

        // Make request
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:7700/indexes/tech/documents";
        String response = restTemplate.postForObject(url, entity, String.class);

        System.out.println("Response from Meilisearch: " + response);
    }
    public static List<Technician> generateTechnicians() {
        List<Technician> technicians = new ArrayList<>();

        for (int i = 1; i <= 50; i++) {
            technicians.add(new Technician(
                    i,
                    "user" + i,
                    "Domain" + (i % 5 + 1),  // Assign one of 5 domains
                    "user" + i + "@example.com",
                    "FirstName" + i,
                    "LastName" + i,
                    new Date(90, i % 12, (i % 28) + 1),  // Simulate different DOBs
                    "123456789" + i,
                    i % 2 == 0 ? "google" : "facebook",  // Alternate auth providers
                    "Governorate" + (i % 3 + 1),
                    "District" + (i % 4 + 1),
                    new Date(121, i % 12, (i % 28) + 1), // Simulate different start dates
                    String.valueOf(10 + i % 5)           // Rate between 10 and 14
            ));
        }

        return technicians;
    }
    public static String searchTechnicians(String query) {
        RestTemplate template=new RestTemplate();
        String searchUrl = meiliSearchUrl + "/indexes/tech/search";
        String jsonBody = "{ \"q\": \"" + query + "\" }"; // Search query in JSON format
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + masterKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        // Perform the search request using POST
        ResponseEntity<String> response = template.exchange(searchUrl, HttpMethod.POST, entity, String.class);

        // Return the search result as a String (you can process it further as needed)
        return response.getBody();

    }
    public static void main(String[] args) throws JsonProcessingException {
         //List<Technician>list=generateTechnicians();
         //list.add(new Technician(100,"Mmm","Carpentry","www.cc@gmail.com","Mahmoud","Addas",new Date(20,10,15),"01003207216","Google","Alexandria","Elraml",new Date(20,20,20),"10"));
         //addTechnicians(list);
          int x=0;
        for (int i = 0; i < 1000000000; i++) {
            x++;
        }
        System.out.println(searchTechnicians("mhmud"));
          
    }
}
