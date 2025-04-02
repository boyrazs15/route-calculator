package com.thy.route_calculator.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name="transportation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "origin_location_id")
    private Location originLocationId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "destination_location_id")
    private Location destinationLocationId;

    @Enumerated(EnumType.STRING)
    @Column(name="transportation_type", nullable = false)
    private TransportationType transportationType;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name="operating_days", columnDefinition = "integer[]")
    private List<Integer> operatingDays;

}
