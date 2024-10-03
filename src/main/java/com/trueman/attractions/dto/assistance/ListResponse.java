package com.trueman.attractions.dto.assistance;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "Ответ в виде списка услуг")
public class ListResponse {
    private List<ReadRequest> assistances;
}
