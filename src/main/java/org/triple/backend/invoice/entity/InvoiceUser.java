package org.triple.backend.invoice.entity;

import jakarta.persistence.*;
import org.triple.backend.user.entity.User;

import java.math.BigDecimal;

@Entity
public class InvoiceUser {

    @Id
    @Column(name = "invoice_user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "remain_amount", nullable = false)
    private BigDecimal remainAmount;
}
