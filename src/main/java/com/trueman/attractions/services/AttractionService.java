package com.trueman.attractions.services;

import com.trueman.attractions.dto.attraction.CreateRequest;
import com.trueman.attractions.dto.attraction.ListResponse;
import com.trueman.attractions.dto.attraction.ReadRequest;
import com.trueman.attractions.dto.attraction.UpdateRequest;
import com.trueman.attractions.models.Attraction;
import com.trueman.attractions.models.Locality;
import com.trueman.attractions.models.enums.TypeAttraction;
import com.trueman.attractions.repositories.AttractionRepository;
import com.trueman.attractions.repositories.LocalityRepository;
import com.trueman.attractions.specification.SpecificationAttraction;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
/**
 * Сервис, который содержит бизнес-логику для управления достопримечательностей.
 */
@Service
@RequiredArgsConstructor
public class AttractionService {
    /**
     * Внедрение репозиториев для взаимодействия с базой данных.
     */
    private final AttractionRepository attractionRepository;
    private final LocalityRepository localityRepository;

    /**
     * Метод получения списка отфильтрованных достопримечательностей по типу достопримечательности.
     */
    public ResponseEntity<?> getListAttraction(TypeAttraction typeAttraction, Long localityId) throws Exception{

        Specification<Attraction> specification = Specification.where(null);

        if(typeAttraction != null)
        {
            specification = specification.and(SpecificationAttraction.findByTypeAttraction(typeAttraction));
        }
        if(localityId != null)
        {
            Locality locality = localityRepository.findById(localityId).orElse(null);
            specification = specification.and(SpecificationAttraction.findByLocality(locality));
        }

        List<Attraction> attractions = attractionRepository.findAll(specification, Sort.by("name"));

        List<ReadRequest> attractionDTOList = new ArrayList<>();
        for (Attraction attraction : attractions) {
            ReadRequest attractionDto = new ReadRequest();
            attractionDto.setId(attraction.getId());
            attractionDto.setName(attraction.getName());
            attractionDto.setCreateDate(attraction.getCreateDate());
            attractionDto.setBriefDescription(attraction.getBriefDescription());
            attractionDto.setTypeAttraction(attraction.getTypeAttraction());
            attractionDto.setLocality(attraction.getLocality());
            attractionDto.setAssistanceList(attraction.getAssistanceList());
            attractionDTOList.add(attractionDto);
        }

        ListResponse attractionDtoDTOListResponse = new ListResponse();
        attractionDtoDTOListResponse.setAttractions(attractionDTOList);

        return new ResponseEntity<>(attractionDtoDTOListResponse, HttpStatus.OK);
    }

    /**
     * Метод создания новой достопримечательности.
     */
    public ResponseEntity<String> createAttraction(CreateRequest createRequest) throws Exception{
        try {
            Attraction attraction = new Attraction();
            attraction.setName(createRequest.getName());
            attraction.setBriefDescription(createRequest.getBriefDescription());
            attraction.setCreateDate();
            attraction.setTypeAttraction(createRequest.getTypeAttraction());
            Locality locality = localityRepository.findById(createRequest.getLocality().getId()).orElse(null);
            attraction.setLocality(locality);

            attractionRepository.save(attraction);
            return ResponseEntity.ok("Достопримечательность успешно создана!");
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().body("Ошибка: неверные данные!");
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Ошибка сервера!");
        }
    }

    /**
     * Метод обновления достопримечательности.
     */
    public ResponseEntity<String> updateAttraction(UpdateRequest updateRequest, Long id) throws Exception{
        try {
            Attraction attraction = attractionRepository.findById(id).orElse(null);
            attraction.setBriefDescription(updateRequest.getBriefDescription());

            attractionRepository.save(attraction);
            return ResponseEntity.ok("Достопримечательность успешно обновлена!");
        } catch (EntityNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Достопримечательность с id: " + id + " не найдена!");
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Ошибка сервера!");
        }
    }

    /**
     * Метод удаления достопримечательности.
     */
    public ResponseEntity<String> deleteAttraction(Long id) throws Exception{
        try {
            Attraction attraction = attractionRepository.findById(id).orElse(null);
            if(attraction == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Достопримечательность с id: " + id + " не найдена!");
            } else {
                attractionRepository.deleteById(id);
                return ResponseEntity.ok("Достопримечательность успешно удалена!");
            }
        } catch (Exception exception) {
            return ResponseEntity.status(500).body("Ошибка сервера!");
        }
    }
}
