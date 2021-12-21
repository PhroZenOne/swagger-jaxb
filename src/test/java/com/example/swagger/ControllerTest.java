package com.example.swagger;

import com.fasterxml.jackson.core.JsonProcessingException;
import generated.Example;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void exampleFromString() {
        // Given
        String request = "<Example>innerText</Example>";

        // When
        RequestEntity<String> requestEntity = RequestEntity
                .post("http://localhost:" + port + "/example")
                .contentType(MediaType.APPLICATION_XML)
                .body(request);
        ResponseEntity<Example> responseAsExample = this.restTemplate.exchange(requestEntity, Example.class);
        ResponseEntity<String> responseAsString = this.restTemplate.exchange(requestEntity, String.class);

        // Then
        assertThat(responseAsExample.getBody()).hasFieldOrPropertyWithValue("value", "innerText");
        assertThat(responseAsString.getBody()).isEqualTo(request);
    }

}