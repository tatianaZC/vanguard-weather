package com.vanguard.weather.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "SUBSCRIPTIONS")
public class Subscription {

    @Id
    @Column(name = "API_KEY")
    private String apiKey;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "REMAINING", nullable = false)
    private Integer remainingRequest;

}
