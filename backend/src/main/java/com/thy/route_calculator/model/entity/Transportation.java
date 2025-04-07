package com.thy.route_calculator.model.entity;

import com.thy.route_calculator.model.enums.TransportationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(
    name = "transportation",
    uniqueConstraints =
        @UniqueConstraint(
            columnNames = {"origin_location_id", "destination_location_id", "transportation_type"}))
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE location SET deleted = true WHERE id = ? AND version = ?")
@Where(clause = "deleted = false")
public class Transportation extends BaseEntity {
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @NotNull
  @JoinColumn(name = "origin_location_id", nullable = false)
  private Location originLocation;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @NotNull
  @JoinColumn(name = "destination_location_id", nullable = false)
  private Location destinationLocation;

  @Enumerated(EnumType.STRING)
  @NotNull
  @Column(name = "transportation_type", nullable = false)
  private TransportationType transportationType;

  /**
   * Haftanın hangi günlerinde çalıştığını belirten alan.
   * transportation_operating_days tablosundaki day_of_week sütunundan gelir.
   */
  @ElementCollection
  @CollectionTable(
      name = "transportation_operating_days",
      joinColumns = @JoinColumn(name = "transportation_id"))
  @Column(name = "day_of_week")
  private List<Integer> operatingDays;
}
