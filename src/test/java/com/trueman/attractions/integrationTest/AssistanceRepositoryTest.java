package com.trueman.attractions.integrationTest;

import com.trueman.attractions.dto.assistance.CreateRequest;
import com.trueman.attractions.dto.assistance.ListResponse;
import com.trueman.attractions.dto.assistance.ReadRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import com.trueman.attractions.models.Assistance;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAssistance;
import com.trueman.attractions.repositories.AssistanceRepository;
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
public class AssistanceRepositoryTest {
    @Autowired
    private MockMvc mvc;

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
    void getListAssistance() throws Exception {
        Assistance assistance1 = new Assistance();
        assistance1.setId(1L);
        assistance1.setTypeAssistance(TypeAssistance.PHOTOSHOOT);
        assistance1.setBriefDescription("Description");
        assistance1.setPerformer("Photoshoot");

        assistanceRepository.save(assistance1);

        var getListResponse = mvc.perform(get("http://localhost:8092/assistance/read")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andReturn();

        List<Assistance> assistances = assistanceRepository.findAll();

        if (!assistances.isEmpty()) {
            List<ReadRequest> expectedAssistances = assistances.stream().map(assistance -> {
                ReadRequest dto = new ReadRequest();
                dto.setId(assistance.getId());
                dto.setTypeAssistance(assistance.getTypeAssistance());
                dto.setBriefDescription(assistance.getBriefDescription());
                dto.setPerformer(assistance.getPerformer());
                return dto;
            }).toList();

            ListResponse expectedResponse = new ListResponse();
            expectedResponse.setAssistances(expectedAssistances);

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
        createRequest.setTypeAssistance(TypeAssistance.FOOD);
        createRequest.setBriefDescription("Description");
        createRequest.setPerformer("Food");

        var createResponse = mvc.perform(post("http://localhost:8092/assistance/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(createRequest)))
                        .andExpect(status().isOk())
                        .andReturn();

        String expected = "Услуга успешно создана!";
        assertEquals(expected, createResponse.getResponse().getContentAsString());

        var assistances = assistanceRepository.findAll();
        assertThat(assistances).isNotEmpty();

        boolean containsCreatedAssistance = assistances.stream().anyMatch(assistance ->
                        assistance.getTypeAssistance().equals(createRequest.getTypeAssistance()) &&
                        assistance.getBriefDescription().equals(createRequest.getBriefDescription()) &&
                        assistance.getPerformer().equals(createRequest.getPerformer())
        );

        assertThat(containsCreatedAssistance).isTrue();

    }
    @Test
    @Order(1)
    void deleteLocalityById() throws Exception {
        Assistance assistance = new Assistance();
        assistance.setId(1L);
        assistance.setTypeAssistance(TypeAssistance.CAR_TOUR);
        assistance.setBriefDescription("Description1");
        assistance.setPerformer("Performer1");

        assistanceRepository.save(assistance);

        var deleteResponse = mvc.perform(delete("http://localhost:8092/assistance/delete/1"))
                .andExpect(status().isOk())
                .andReturn();

        String expected = "Услуга успешно удалена!";
        assertEquals(expected, deleteResponse.getResponse().getContentAsString());

        assertThat(assistanceRepository.existsById(1L)).isFalse();

    }
}