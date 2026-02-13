package org.triple.backend.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.triple.backend.group.entity.group.Group;

public interface GroupJpaRepository extends JpaRepository<Group, Long> {
}
