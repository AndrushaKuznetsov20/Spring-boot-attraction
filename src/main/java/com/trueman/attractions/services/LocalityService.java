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

@Service
@RequiredArgsConstructor
public class LocalityService {

    private final LocalityRepository localityRepository;

    public ResponseEntity<ListResponse> getListLocality() {

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
    }

    public ResponseEntity<String> createLocality(CreateRequest createRequest) {
        Locality locality = new Locality();
        locality.setSettlement(createRequest.getSettlement());
        locality.setRegion(createRequest.getRegion());
        locality.setLatitude(createRequest.getLatitude());
        locality.setLongitude(createRequest.getLongitude());

        localityRepository.save(locality);
        return ResponseEntity.ok("Местоположение успешно создано!");
    }

    public ResponseEntity<String> deleteLocality(Long id) {
        localityRepository.deleteById(id);
        return ResponseEntity.ok("Местоположение успешно удалено!");
    }

//    public ResponseEntity<String> updateLocality(UpdateRequest updateRequest, Long id) {
//        Locality locality = localityRepository.findById(id).orElse(null);
//
//        return ResponseEntity.ok("Местоположение успешно создано");
//    }
}
