package com.thy.route_calculator.model;

import com.thy.route_calculator.model.enums.TransportationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "origin_location_id", nullable = false)
    private Location originLocation;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "destination_location_id", nullable = false)
    private Location destinationLocation;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name="transportation_type", nullable = false)
    private TransportationType transportationType;

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name="operating_days", columnDefinition = "integer[]")
    private List<Integer> operatingDays;

}
