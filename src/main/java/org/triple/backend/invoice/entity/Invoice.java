package org.triple.backend.invoice.entity;

import jakarta.persistence.*;
import org.triple.backend.global.common.BaseEntity;
import org.triple.backend.group.entity.group.Group;
import org.triple.backend.payment.entity.Payment;
import org.triple.backend.user.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Invoice extends BaseEntity {

    @Id
    @Column(name = "invoice_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id")
    private User creator;

    @OneToMany(mappedBy = "invoice")
    private List<InvoiceUser> invoiceUsers = new ArrayList<>();

    @OneToMany(mappedBy = "invoice")
    private List<Payment> payments = new ArrayList<>();

    private String title;

    private BigDecimal totalAmount;

    private LocalDateTime dueAt;
}
