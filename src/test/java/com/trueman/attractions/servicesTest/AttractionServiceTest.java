package com.trueman.attractions.servicesTest;

import com.trueman.attractions.dto.attraction.CreateRequest;
import com.trueman.attractions.dto.attraction.ListResponse;
import com.trueman.attractions.dto.attraction.UpdateRequest;
import com.trueman.attractions.models.Attraction;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAttraction;
import com.trueman.attractions.repositories.AttractionRepository;
import com.trueman.attractions.repositories.LocalityRepository;
import com.trueman.attractions.services.AttractionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AttractionServiceTest {
    @Mock
    private AttractionRepository attractionRepository;

    @Mock
    private LocalityRepository localityRepository;

    @InjectMocks
    private AttractionService attractionService;

    @Test
    public void testGetListAttraction() throws Exception {
        Locality locality = new Locality();

        locality.setId(1L);
        locality.setSettlement("Москва");

        Attraction attraction = new Attraction();
        attraction.setId(1L);
        attraction.setName("Эрмитаж");
        attraction.setTypeAttraction(TypeAttraction.PARKS);
        attraction.setLocality(locality);

        Attraction attraction2 = new Attraction();
        attraction2.setId(2L);
        attraction2.setName("Эрмитаж1");
        attraction.setTypeAttraction(TypeAttraction.PALACES);
        attraction2.setLocality(locality);

        List<Attraction> attractionList = new ArrayList<>();
        attractionList.add(attraction);

        when(localityRepository.findById(1L)).thenReturn(Optional.of(locality));
        when(attractionRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(attractionList);

        ResponseEntity<?> response = attractionService.getListAttraction(TypeAttraction.PARKS, locality.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ListResponse responseBody = (ListResponse) response.getBody();
        assertNotNull(responseBody);
        assertEquals(1, responseBody.getAttractions().size());
        assertEquals("Эрмитаж", responseBody.getAttractions().get(0).getName());

    }

    @Test
    public void testCreateNewAttraction() throws Exception {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setName("Эрмитаж");
        createRequest.setBriefDescription("Большой дворец");
        createRequest.setTypeAttraction(TypeAttraction.PALACES);
        Locality locality = new Locality();
        locality.setId(1L);
        createRequest.setLocality(locality);

        ResponseEntity<String> response = attractionService.createAttraction(createRequest);
        String expected = "Достопримечательность успешно создана!";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());

        verify(attractionRepository, times(1)).save(any(Attraction.class));
    }

    @Test
    public void testUpdateAttraction() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setBriefDescription("Новое описание");

        Long id = 1L;
        Attraction attraction = new Attraction();
        attraction.setId(id);
        attraction.setBriefDescription("Старое описание");

        when(attractionRepository.findById(id)).thenReturn(Optional.of(attraction));

        ResponseEntity<String> response = attractionService.updateAttraction(updateRequest, id);
        String expected = "Достопримечательность успешно обновлена!";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());
    }
    @Test
    public void testDeleteAttraction() throws Exception {
        Long id = 1L;

        Attraction attraction = new Attraction();
        attraction.setId(id);

        when(attractionRepository.findById(id)).thenReturn(Optional.of(attraction));

        ResponseEntity<String> response = attractionService.deleteAttraction(id);
        String expected = "Достопримечательность успешно удалена!";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());

        verify(attractionRepository, times(1)).deleteById(id);
    }
}
