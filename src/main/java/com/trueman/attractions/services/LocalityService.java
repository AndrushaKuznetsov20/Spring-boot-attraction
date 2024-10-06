package com.trueman.attractions.services;

import com.trueman.attractions.dto.locality.CreateRequest;
import com.trueman.attractions.dto.locality.ListResponse;
import com.trueman.attractions.dto.locality.ReadRequest;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.repositories.LocalityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Сервис, который содержит бизнес-логику для управления местоположениями.
 */
@Service
@RequiredArgsConstructor
public class LocalityService {
    /**
     * Внедрение репозитория для взаимодействия с базой данных.
     */
    private final LocalityRepository localityRepository;

    /**
     * Метод получения списка всех местоположений.
     */
    public ResponseEntity<?> getListLocality() {
        try {
            List<Locality> localityList = localityRepository.findAll();
            List<ReadRequest> localityDTOList = new ArrayList<>();

            for (Locality locality : localityList) {
                ReadRequest localityDto = new ReadRequest();
                localityDto.setId(locality.getId());
                localityDto.setSettlement(locality.getSettlement());
                localityDto.setRegion(locality.getRegion());
                localityDto.setAttractionList(locality.getAttractionList());
                localityDto.setLatitude(locality.getLatitude());
                localityDto.setLongitude(locality.getLongitude());

                localityDTOList.add(localityDto);
            }
            ListResponse localityDTOListResponse = new ListResponse();
            localityDTOListResponse.setLocalities(localityDTOList);

            return new ResponseEntity<>(localityDTOListResponse, HttpStatus.OK);
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Ошибка сервера!");
        }
    }

    /**
     * Метод создания нового местоположения.
     */
    public ResponseEntity<String> createLocality(CreateRequest createRequest) {
        try {
            Locality locality = new Locality();
            locality.setSettlement(createRequest.getSettlement());
            locality.setRegion(createRequest.getRegion());
            locality.setLatitude(createRequest.getLatitude());
            locality.setLongitude(createRequest.getLongitude());

            localityRepository.save(locality);
            return ResponseEntity.ok("Местоположение успешно создано!");

        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body("Ошибка: неверные данные!");
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Ошибка сервера!");
        }
    }

    /**
     * Метод удаления местоположения.
     */
    public ResponseEntity<String> deleteLocality(Long id) {
        try {
            Locality locality = localityRepository.findById(id).orElse(null);
            if(locality == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Местоположение с id: " + id + " не найдено!");
            } else {
                localityRepository.deleteById(id);
                return ResponseEntity.ok("Местоположение успешно удалено!");
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Ошибка сервера!");
        }
    }
}
