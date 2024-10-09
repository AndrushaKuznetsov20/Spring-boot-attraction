package com.trueman.attractions.integrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.trueman.attractions.dto.attraction.CreateRequest;
import com.trueman.attractions.dto.attraction.ListResponse;
import com.trueman.attractions.dto.attraction.ReadRequest;
import com.trueman.attractions.models.Assistance;
import com.trueman.attractions.models.Attraction;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAssistance;
import com.trueman.attractions.models.enums.TypeAttraction;
import com.trueman.attractions.repositories.AssistanceRepository;
import com.trueman.attractions.repositories.AttractionRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.util.ArrayList;
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
public class AttractionRepositoryTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AttractionRepository attractionRepository;

    @Autowired
    private LocalityRepository localityRepository;
    @Autowired
    private AssistanceRepository assistanceRepository;
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
    void getListAttraction() throws Exception {
        Locality locality1 = new Locality();
        locality1.setId(1L);
        locality1.setSettlement("Moscow");
        locality1.setRegion("Moscow region");
        locality1.setLatitude(54.33);
        locality1.setLongitude(12.33);

        localityRepository.save(locality1);

        Assistance assistance = new Assistance();
        assistance.setId(1L);
        assistance.setTypeAssistance(TypeAssistance.CAR_TOUR);
        assistance.setBriefDescription("Description1");
        assistance.setPerformer("Performer1");

        assistanceRepository.save(assistance);

        List<Assistance> assistanceList = new ArrayList<>();
        assistanceList.add(assistance);

        Attraction attraction1 = new Attraction();
        attraction1.setId(1L);
        attraction1.setName("Name2");
        attraction1.setBriefDescription("Description2");
        attraction1.setTypeAttraction(TypeAttraction.PALACES);
//        attraction1.setCreateDate();
        attraction1.setLocality(locality1);
        attraction1.setAssistanceList(assistanceList);

        attractionRepository.save(attraction1);

        TypeAttraction typeAttraction = TypeAttraction.PALACES;
        Long localityId = attraction1.getLocality().getId();

        var getListResponse = mvc.perform(get("http://localhost:8092/attraction/read")
                        .param("typeAttraction", typeAttraction.name())
                        .param("localityId", localityId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        List<Attraction> attractions = attractionRepository.findAll();

        if (!attractions.isEmpty()) {
            List<ReadRequest> expectedAttractions = attractions.stream().map(attraction -> {
                ReadRequest dto = new ReadRequest();
                dto.setId(attraction.getId());
                dto.setName(attraction.getName());
//                dto.setCreateDate(attraction.getCreateDate());
                dto.setBriefDescription(attraction.getBriefDescription());
                dto.setTypeAttraction(attraction.getTypeAttraction());
                dto.setLocality(attraction.getLocality());
                dto.setAssistanceList(attraction.getAssistanceList());
                return dto;
            }).toList();

            ListResponse expectedResponse = new ListResponse();
            expectedResponse.setAttractions(expectedAttractions);

            String expectedJson = new ObjectMapper().writeValueAsString(expectedResponse);

            String actualJson = getListResponse.getResponse().getContentAsString();

            assertEquals(expectedJson, actualJson);

        }
    }
    @Test
    @Order(2)
    @SneakyThrows
    void createAttraction() {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setName("Name1");
        createRequest.setBriefDescription("Description1");
        createRequest.setTypeAttraction(TypeAttraction.MUSEUMS);

        Locality locality1 = new Locality();
        locality1.setId(1L);
        locality1.setSettlement("Moscow");
        locality1.setRegion("Moscow region");
        locality1.setLatitude(54.33);
        locality1.setLongitude(12.33);

        localityRepository.save(locality1);

        createRequest.setLocality(locality1);

        var createResponse = mvc.perform(post("http://localhost:8092/attraction/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createRequest)))
                        .andExpect(status().isOk())
                        .andReturn();

        String expected = "Достопримечательность успешно создана!";
        assertEquals(expected, createResponse.getResponse().getContentAsString());

        var attractions = attractionRepository.findAll();
        assertThat(attractions).isNotEmpty();

        boolean containsCreatedAttraction = attractions.stream().anyMatch(attraction ->
                        attraction.getName().equals(createRequest.getName()) &&
                        attraction.getBriefDescription().equals(createRequest.getBriefDescription()) &&
                        attraction.getTypeAttraction().equals(createRequest.getTypeAttraction()) &&
                                attraction.getLocality().equals(createRequest.getLocality())
        );

        assertThat(containsCreatedAttraction).isTrue();

    }
    @Test
    @Order(1)
    void deleteLocalityById() throws Exception {
        Locality locality1 = new Locality();
        locality1.setId(1L);
        locality1.setSettlement("Moscow");
        locality1.setRegion("Moscow region");
        locality1.setLatitude(54.33);
        locality1.setLongitude(12.33);

        localityRepository.save(locality1);

        Attraction attraction = new Attraction();
        attraction.setId(1L);
        attraction.setName("Name1");
        attraction.setBriefDescription("Description1");
        attraction.setTypeAttraction(TypeAttraction.PALACES);
        attraction.setCreateDate(LocalDate.parse("2024-07-10"));
        attraction.setLocality(locality1);

        attractionRepository.save(attraction);

        var deleteResponse = mvc.perform(delete("http://localhost:8092/attraction/delete/1"))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "Достопримечательность успешно удалена!";
        assertEquals(expected, deleteResponse.getResponse().getContentAsString());

        assertThat(attractionRepository.existsById(1L)).isFalse();

    }
}