package com.mstftrgt.todo_app_hexagonal.infra.integration;


import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.rest.dto.RegisterRequest;
import com.mstftrgt.todo_app_hexagonal.infra.adapters.user.rest.dto.RegisterResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.from;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIT {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void shouldRegisterUser() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .username("test username")
                .password("test password")
                .build();

        var responseEntity = testRestTemplate
                .exchange("/auth/register", HttpMethod.POST, new HttpEntity<>(registerRequest, null), RegisterResponse.class);

        assertThat(responseEntity).isNotNull()
                .returns(HttpStatus.CREATED, from(ResponseEntity::getStatusCode));

        assertThat(responseEntity.getBody()).isNotNull();
    }

}
