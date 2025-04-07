package com.thy.route_calculator.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiSuccessResponse<T> {
    private boolean success;
    @Schema(nullable = true)
    private T data;
}
