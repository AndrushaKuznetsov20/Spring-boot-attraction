package com.trueman.attractions.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
@Schema(description = "Ответ c токеном доступа")
public class JwtAuthenticationResponse {
    @Schema(description = "Токен доступа", example = "lkLfldDLFlsdfFlsf5.jlsdjlFlwfFkw7...")
    private String token;
}
