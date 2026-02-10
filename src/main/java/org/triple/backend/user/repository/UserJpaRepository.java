package org.triple.backend.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.triple.backend.user.entity.User;

public interface UserJpaRepository extends JpaRepository<User,Long> {
}
