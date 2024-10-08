package com.trueman.attractions.servicesTest;

import com.trueman.attractions.dto.assistance.CreateRequest;
import com.trueman.attractions.dto.assistance.ListResponse;
import com.trueman.attractions.models.Assistance;
import com.trueman.attractions.models.Attraction;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAssistance;
import com.trueman.attractions.models.enums.TypeAttraction;
import com.trueman.attractions.repositories.AssistanceRepository;
import com.trueman.attractions.services.AssistanceService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AssistanceServiceTest {
    @Mock
    private AssistanceRepository assistanceRepository;
    @InjectMocks
    private AssistanceService assistanceService;

    @Test
    public void testGetListAssistance() throws Exception {
        List<Assistance> assistanceList = new ArrayList<>();
        List<Attraction> attractionList1 = new ArrayList<>();
        List<Attraction> attractionList2 = new ArrayList<>();

        Locality locality = new Locality();
        locality.setId(1L);
        locality.setSettlement("Москва");

        Attraction attraction1 = new Attraction();
        attraction1.setId(1L);
        attraction1.setName("Эрмитаж");
        attraction1.setTypeAttraction(TypeAttraction.PARKS);
        attraction1.setLocality(locality);

        Attraction attraction2 = new Attraction();
        attraction2.setId(2L);
        attraction2.setName("Эрмитаж1");
        attraction2.setTypeAttraction(TypeAttraction.PALACES);
        attraction2.setLocality(locality);

        attractionList1.add(attraction1);
        attractionList2.add(attraction2);

        Assistance assistance_1 = new Assistance();
        assistance_1.setId(1L);
        assistance_1.setTypeAssistance(TypeAssistance.CAR_TOUR);
        assistance_1.setBriefDescription("Автотур по городу");
        assistance_1.setPerformer("АвтоТур");
        assistance_1.setAttractionList(attractionList1);

        Assistance assistance_2 = new Assistance();
        assistance_2.setId(1L);
        assistance_1.setTypeAssistance(TypeAssistance.CAR_TOUR);
        assistance_1.setBriefDescription("Автотур по городу");
        assistance_1.setPerformer("АвтоТур");
        assistance_1.setAttractionList(attractionList2);

        assistanceList.add(assistance_1);
        assistanceList.add(assistance_2);

        when(assistanceRepository.findAll()).thenReturn(assistanceList);

        ResponseEntity<?> response = assistanceService.getListAssistance();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ListResponse listResponse = (ListResponse) response.getBody();
        assertEquals(2, listResponse.getAssistances().size());
    }

    @Test
    public void testCreateNewAssistance() throws Exception {
        CreateRequest createRequest = new CreateRequest();

        createRequest.setTypeAssistance(TypeAssistance.FOOD);
        createRequest.setBriefDescription("Трёхразовое питание");
        createRequest.setPerformer("Круглосуточное питание");

        ResponseEntity<String> response = assistanceService.createAssistance(createRequest);
        String expected = "Услуга успешно создана!";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());

        verify(assistanceRepository, times(1)).save(any(Assistance.class));
    }

    @Test
    public void testDeleteAssistance() throws Exception {
        Long id = 1L;

        Assistance assistance = new Assistance();
        assistance.setId(id);

        when(assistanceRepository.findById(id)).thenReturn(Optional.of(assistance));

        ResponseEntity<String> response = assistanceService.deleteAssistance(id);
        String expected = "Услуга успешно удалена!";

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expected, response.getBody());

        verify(assistanceRepository, times(1)).deleteById(id);
    }
}
