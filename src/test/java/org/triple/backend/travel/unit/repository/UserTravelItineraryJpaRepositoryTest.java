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
import org.triple.backend.travel.entity.UserRole;
import org.triple.backend.travel.entity.UserTravelItinerary;
import org.triple.backend.travel.repository.TravelItineraryJpaRepository;
import org.triple.backend.travel.repository.UserTravelItineraryJpaRepository;
import org.triple.backend.user.entity.User;
import org.triple.backend.user.repository.UserJpaRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class UserTravelItineraryJpaRepositoryTest {

    @Autowired
    private UserTravelItineraryJpaRepository userTravelItineraryJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private GroupJpaRepository groupJpaRepository;

    @Autowired
    private TravelItineraryJpaRepository travelItineraryJpaRepository;

    @Test
    @DisplayName("유저-여행 일정 매핑이 저장된다.")
    void 유저_여행_일정_매핑_저장() {
        //given
        User user = userJpaRepository.save(User.builder().build());
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
        TravelItinerary travel = travelItineraryJpaRepository.save(TravelItinerary.of(req, group));

        //when
        UserTravelItinerary savedUserTravelItinerary = userTravelItineraryJpaRepository.save(
                UserTravelItinerary.of(user, travel, UserRole.LEADER)
        );

        //then
        UserTravelItinerary foundedUserTravelItinerary = userTravelItineraryJpaRepository.findById(savedUserTravelItinerary.getId()).orElseThrow();

        assertThat(foundedUserTravelItinerary)
                .extracting("user", "travelItinerary", "userRole")
                .containsExactly(user, travel, UserRole.LEADER);
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
