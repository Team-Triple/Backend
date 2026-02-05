package org.triple.backend.travel.entity;

import jakarta.persistence.*;
import org.triple.backend.user.entity.User;

@Entity
public class UserTravelItinerary {

    @Id
    @Column(name = "user_travel_itinerary_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "travel_itinerary_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private TravelItinerary travelItinerary;

}
