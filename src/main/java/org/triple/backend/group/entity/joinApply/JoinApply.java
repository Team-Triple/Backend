package org.triple.backend.group.entity.joinApply;

import jakarta.persistence.*;
import org.triple.backend.global.common.BaseEntity;
import org.triple.backend.group.entity.group.Group;
import org.triple.backend.user.entity.User;

@Entity
public class JoinApply extends BaseEntity {

    @Id
    @Column(name = "join_apply_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Group group;
}
