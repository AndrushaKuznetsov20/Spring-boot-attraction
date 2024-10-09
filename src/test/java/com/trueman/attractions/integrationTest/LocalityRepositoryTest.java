package com.trueman.attractions.integrationTest;

import com.trueman.attractions.dto.locality.CreateRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.trueman.attractions.dto.locality.ListResponse;
import com.trueman.attractions.dto.locality.ReadRequest;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.repositories.LocalityRepository;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LocalityRepositoryTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private LocalityRepository localityRepository;

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:15"));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.jpa.generate-ddl", () -> true);
    }

    @Test
    @Order(3)
    void getListLocality() throws Exception {
        Locality locality1 = new Locality();
        locality1.setId(1L);
        locality1.setSettlement("Moscow");
        locality1.setRegion("Moscow region");
        locality1.setLatitude(525.7558);
        locality1.setLongitude(327.6173);

        localityRepository.save(locality1);

        var getListResponse = mvc.perform(get("http://localhost:8092/locality/read")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        List<Locality> localities = localityRepository.findAll();

        if (!localities.isEmpty()) {
            List<ReadRequest> expectedLocalities = localities.stream().map(locality -> {
                ReadRequest dto = new ReadRequest();
                dto.setId(locality.getId());
                dto.setSettlement(locality.getSettlement());
                dto.setRegion(locality.getRegion());
                dto.setAttractionList(locality.getAttractionList());
                dto.setLatitude(locality.getLatitude());
                dto.setLongitude(locality.getLongitude());
                return dto;
            }).toList();

            ListResponse expectedResponse = new ListResponse();
            expectedResponse.setLocalities(expectedLocalities);

            String expectedJson = new ObjectMapper().writeValueAsString(expectedResponse);

            String actualJson = getListResponse.getResponse().getContentAsString();

            assertEquals(expectedJson, actualJson);

        }
    }
    @Test
    @Order(2)
    @SneakyThrows
    void createLocality() {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setSettlement("Moscow");
        createRequest.setRegion("Moscow region");
        createRequest.setLatitude(55.7558);
        createRequest.setLongitude(37.6173);

        var createResponse = mvc.perform(post("http://localhost:8092/locality/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "Местоположение успешно создано!";
        assertEquals(expected, createResponse.getResponse().getContentAsString());

        var localities = localityRepository.findAll();
        assertThat(localities).isNotEmpty();

        boolean containsCreatedLocality = localities.stream().anyMatch(locality ->
                locality.getSettlement().equals(createRequest.getSettlement()) &&
                        locality.getRegion().equals(createRequest.getRegion()) &&
                        locality.getLatitude() == createRequest.getLatitude() &&
                        locality.getLongitude() == createRequest.getLongitude()
        );

        assertThat(containsCreatedLocality).isTrue();


    }
    @Test
    @Order(1)
    void deleteLocalityById() throws Exception {
        Locality locality = new Locality();
        locality.setId(1L);
        locality.setSettlement("Москва");
        locality.setRegion("Московская область");
        locality.setLatitude(55.7558);
        locality.setLongitude(37.6173);

        localityRepository.save(locality);

        var deleteResponse = mvc.perform(delete("http://localhost:8092/locality/delete/1"))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "Местоположение успешно удалено!";
        assertEquals(expected, deleteResponse.getResponse().getContentAsString());

        assertThat(localityRepository.existsById(1L)).isFalse();

    }
}
