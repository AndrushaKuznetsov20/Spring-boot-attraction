package com.trueman.attractions.servicesTest;

import com.trueman.attractions.dto.locality.CreateRequest;
import com.trueman.attractions.dto.locality.ListResponse;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.repositories.LocalityRepository;
import com.trueman.attractions.services.LocalityService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocalityServiceTest {
    @Mock
    private LocalityRepository localityRepository;

    @InjectMocks
    private LocalityService localityService;

    @Test
    public void testGetListLocality() {
        List<Locality> localityList = new ArrayList<>();

        Locality locality_1 = new Locality();
        locality_1.setId(1L);
        locality_1.setSettlement("Санкт-Петербург");
        locality_1.setRegion("Ленинградская область, 78 регион");
        locality_1.setLatitude(54.3);
        locality_1.setLongitude(33.2);
        localityList.add(locality_1);

        Locality locality_2 = new Locality();
        locality_2.setId(1L);
        locality_2.setSettlement("Москва");
        locality_2.setRegion("Московская область, 77 регион");
        locality_2.setLatitude(63.2);
        locality_2.setLongitude(48.7);
        localityList.add(locality_2);

        when(localityRepository.findAll()).thenReturn(localityList);

        ResponseEntity<?> response = localityService.getListLocality();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ListResponse listResponse = (ListResponse) response.getBody();
        assertEquals(2, listResponse.getLocalities().size());
    }

    @Test
    public void testCreateNewLocality() {
        CreateRequest createRequest = new CreateRequest();
        createRequest.setSettlement("Владимир");
        createRequest.setRegion("Владимирская область,33 регион");
        createRequest.setLatitude(34.2);
        createRequest.setLongitude(56.3);

        ResponseEntity<String> response = localityService.createLocality(createRequest);
        String expected = "Местоположение успешно создано!";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());

        verify(localityRepository, times(1)).save(any(Locality.class));
    }

    @Test
    public void testDeleteLocality() {
        Long id = 1L;

        Locality locality = new Locality();
        locality.setId(id);

        when(localityRepository.findById(id)).thenReturn(Optional.of(locality));

        ResponseEntity<String> response = localityService.deleteLocality(id);
        String expected = "Местоположение успешно удалено!";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());

        verify(localityRepository, times(1)).deleteById(id);
    }
}
