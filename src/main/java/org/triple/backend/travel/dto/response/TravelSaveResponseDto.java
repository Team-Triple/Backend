package org.triple.backend.travel.dto.response;

import lombok.Builder;

@Builder
public record TravelSaveResponseDto(
        Long itineraryId
) {
    public static TravelSaveResponseDto from(Long itineraryId) {
        return TravelSaveResponseDto.builder()
                .itineraryId(itineraryId)
                .build();
    }
}
