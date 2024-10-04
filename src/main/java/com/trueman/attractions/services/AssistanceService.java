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

/**
 * Сервис, который содержит бизнес-логику для управления услугами.
 */
@Service
@RequiredArgsConstructor
public class AssistanceService {
    /**
     * Внедрение репозитория для взаимодействия с базой данных.
     */
    private final AssistanceRepository assistanceRepository;

    /**
     * Метод для получения списка услуг
     */
    public ResponseEntity<?> getListAssistance() throws Exception{
        try {
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
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Ошибка сервера!");
        }
    }

    /**
     * Метод для создания новой услуги
     */
    public ResponseEntity<String> createAssistance(CreateRequest createRequest) throws Exception{
        try {
            Assistance assistance = new Assistance();
            assistance.setTypeAssistance(createRequest.getTypeAssistance());
            assistance.setBriefDescription(createRequest.getBriefDescription());
            assistance.setPerformer(createRequest.getPerformer());

            assistanceRepository.save(assistance);
            return ResponseEntity.ok("Услуга успешно создана!");
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body("Ошибка: неверные данные!");
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Ошибка сервера!");
        }
    }

    /**
     * Метод для удаления услуги
     */
    public ResponseEntity<String> deleteAssistance(Long id) throws Exception{
        try {
            Assistance assistance = assistanceRepository.findById(id).orElse(null);
            if(assistance == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Услуга с id: " + id + " не найдена!");
            } else {
                assistanceRepository.deleteById(id);
                return ResponseEntity.ok("Услуга успешно удалена!");
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Ошибка сервера!");
        }
    }
}
