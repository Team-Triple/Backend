package org.triple.backend.travel.unit.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.triple.backend.common.annotation.RepositoryTest;
import org.triple.backend.group.entity.group.Group;
import org.triple.backend.group.entity.group.GroupKind;
import org.triple.backend.group.repository.GroupJpaRepository;
import org.triple.backend.travel.dto.request.TravelSaveRequestDto;
import org.triple.backend.travel.entity.TravelItinerary;
import org.triple.backend.travel.repository.TravelItineraryJpaRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class TravelItineraryJpaRepositoryTest {

    @Autowired
    private TravelItineraryJpaRepository travelItineraryJpaRepository;

    @Autowired
    private GroupJpaRepository groupJpaRepository;

    @Test
    @DisplayName("여행 일정 저장 후 조회된다.")
    void 여행_일정_저장_조회() {
        //given
        Group group = groupJpaRepository.save(createGroup());

        TravelSaveRequestDto req = new TravelSaveRequestDto(
                "제목",
                LocalDateTime.of(2026, 2, 14, 0, 0),
                LocalDateTime.of(2026, 2, 16, 0, 0),
                group.getId(),
                "설명",
                "test-url",
                5
        );

        //when
        TravelItinerary savedTravelItinerary = travelItineraryJpaRepository.save(TravelItinerary.of(req, group));
        TravelItinerary foundedTravelItinerary = travelItineraryJpaRepository.findById(savedTravelItinerary.getId()).orElseThrow();

        //then
        assertThat(foundedTravelItinerary)
                .extracting("title", "description", "thumbnailUrl", "memberLimit", "memberCount", "group")
                .containsExactly("제목", "설명", "test-url", 5, 1, group);
    }

    private Group createGroup() {
        return Group.builder()
                .groupKind(GroupKind.PUBLIC)
                .name("모임")
                .description("설명")
                .thumbNailUrl("http://thumb")
                .memberLimit(10)
                .build();
    }
}
