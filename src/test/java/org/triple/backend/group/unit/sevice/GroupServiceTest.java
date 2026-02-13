package org.triple.backend.group.unit.sevice;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.triple.backend.common.annotation.ServiceTest;
import org.triple.backend.group.dto.request.CreateGroupRequestDto;
import org.triple.backend.group.dto.response.CreateGroupResponseDto;
import org.triple.backend.group.entity.group.Group;
import org.triple.backend.group.entity.group.GroupKind;
import org.triple.backend.group.entity.userGroup.JoinStatus;
import org.triple.backend.group.entity.userGroup.Role;
import org.triple.backend.group.entity.userGroup.UserGroup;
import org.triple.backend.group.repository.GroupJpaRepository;
import org.triple.backend.group.repository.UserGroupJpaRepository;
import org.triple.backend.group.service.GroupService;
import org.triple.backend.user.entity.User;
import org.triple.backend.user.repository.UserJpaRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ServiceTest
@Import(GroupService.class)
public class GroupServiceTest {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private GroupJpaRepository groupJpaRepository;

    @Autowired
    private UserGroupJpaRepository userGroupJpaRepository;

    @Test
    @DisplayName("그룹 생성 시 그룹이 저장되고, 생성자는 OWNER로 UserGroup이 함께 저장된다")
    void 그룹_생성_시_그룹이_저장되고_생성자는_OWNER로_UserGroup이_함께_저장된다() {
        // given
        User owner = userJpaRepository.save(
                User.builder()
                        .providerId("kakao-1")
                        .nickname("상윤")
                        .email("test@test.com")
                        .profileUrl("http://img")
                        .build()
        );

        CreateGroupRequestDto req = new CreateGroupRequestDto(
                "여행모임",
                "3월 일본 여행",
                10,
                GroupKind.PUBLIC,
                "https://example.com/thumb.png"
        );

        // when
        CreateGroupResponseDto res = groupService.create(req, owner.getId());

        // then
        Group savedGroup = groupJpaRepository.findById(res.groupId()).orElseThrow();
        assertThat(savedGroup.getName()).isEqualTo("여행모임");
        assertThat(savedGroup.getDescription()).isEqualTo("3월 일본 여행");
        assertThat(savedGroup.getMemberLimit()).isEqualTo(10);
        assertThat(savedGroup.getGroupKind()).isEqualTo(GroupKind.PUBLIC);
        assertThat(savedGroup.getThumbNailUrl()).isEqualTo("https://example.com/thumb.png");

        List<UserGroup> memberships = userGroupJpaRepository.findAll();
        assertThat(memberships).hasSize(1);

        UserGroup userGroup = memberships.get(0);
        assertThat(userGroup.getUser().getId()).isEqualTo(owner.getId());
        assertThat(userGroup.getGroup().getId()).isEqualTo(savedGroup.getId());
        assertThat(userGroup.getRole()).isEqualTo(Role.OWNER);
        assertThat(userGroup.getJoinStatus()).isEqualTo(JoinStatus.JOINED);
        assertThat(userGroup.getJoinedAt()).isNotNull();
    }
}
