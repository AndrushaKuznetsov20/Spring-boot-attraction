package com.trueman.attractions.services;

import com.trueman.attractions.dto.assistance.CreateRequest;
import com.trueman.attractions.dto.assistance.ListResponse;
import com.trueman.attractions.dto.assistance.ReadRequest;
import com.trueman.attractions.models.Assistance;
import com.trueman.attractions.repositories.AssistanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistanceService {
    private final AssistanceRepository assistanceRepository;

    public ResponseEntity<ListResponse> getListAssistance() {

        List<Assistance> assistanceList = assistanceRepository.findAll();
        List<ReadRequest> assistanceDTOList = new ArrayList<>();

        for (Assistance assistance : assistanceList) {
            ReadRequest assistanceDto = new ReadRequest();
            assistanceDto.setId(assistance.getId());
            assistanceDto.setTypeAssistance(assistance.getTypeAssistance());
            assistanceDto.setBriefDescription(assistance.getBriefDescription());
            assistanceDto.setPerformer(assistance.getPerformer());
            assistanceDTOList.add(assistanceDto);
        }
        ListResponse assistanceDTOListResponse = new ListResponse();
        assistanceDTOListResponse.setAssistances(assistanceDTOList);

        return new ResponseEntity<>(assistanceDTOListResponse, HttpStatus.OK);
    }

    public ResponseEntity<String> createAssistance(CreateRequest createRequest) {
        Assistance assistance = new Assistance();
        assistance.setTypeAssistance(createRequest.getTypeAssistance());
        assistance.setBriefDescription(createRequest.getBriefDescription());
        assistance.setPerformer(createRequest.getPerformer());

        assistanceRepository.save(assistance);
        return ResponseEntity.ok("Услуга успешно создана!");
    }

    public ResponseEntity<String> deleteAssistance(Long id) {
        assistanceRepository.deleteById(id);
        return ResponseEntity.ok("Услуга успешно удалена!");
    }
}
