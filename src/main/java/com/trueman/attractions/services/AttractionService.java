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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionService {
    private final AttractionRepository attractionRepository;
    private final LocalityRepository localityRepository;

    public ResponseEntity<ListResponse> getListAttraction(TypeAttraction typeAttraction) {

        List<Attraction> attractionList;
        if (typeAttraction != null)
        {
            attractionList = attractionRepository.findByTypeAttractionSortByName(typeAttraction);
        } else {
            attractionList = attractionRepository.findAllSortByName();
        }

        List<ReadRequest> attractionDTOList = new ArrayList<>();

        for (Attraction attraction : attractionList) {
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

    public ResponseEntity<ListResponse> getListAttractionByLocality(Long localityId) {

        Locality locality = localityRepository.findById(localityId).orElse(null);

        List<Attraction> attractionList = attractionRepository.findByLocalityAttraction(locality);
        List<ReadRequest> attractionDTOList = new ArrayList<>();

        for (Attraction attraction : attractionList) {
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

    public ResponseEntity<String> createAttraction(CreateRequest createRequest) {
        Attraction attraction = new Attraction();
        attraction.setName(createRequest.getName());
        attraction.setBriefDescription(createRequest.getBriefDescription());
        attraction.setCreateDate();
        attraction.setTypeAttraction(createRequest.getTypeAttraction());
        attraction.setLocality(createRequest.getLocality());

        attractionRepository.save(attraction);
        return ResponseEntity.ok("Достопримечательность успешно создана!");
    }

    public ResponseEntity<String> updateAttraction(UpdateRequest updateRequest, Long id) {
        Attraction attraction = attractionRepository.findById(id).orElse(null);

        attraction.setBriefDescription(updateRequest.getBriefDescription());

        attractionRepository.save(attraction);
        return ResponseEntity.ok("Достопримечательность успешно обновлена!");
    }

    public ResponseEntity<String> deleteAttraction(Long id) {
        attractionRepository.deleteById(id);
        return ResponseEntity.ok("Достопримечательность успешно удалена!");
    }
}
