package com.trueman.attractions.dto.locality;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Ответ в виде списка местоположений")
public class ListResponse {
    private List<ReadRequest> localities;
}
