package com.trueman.attractions.dto.attraction;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Ответ в виде списка достопримечательностей")
public class ListResponse {
    private List<ReadRequest> attractions;
}
