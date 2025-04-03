package com.thy.route_calculator.model;

import com.thy.route_calculator.model.enums.TransportationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "transportation")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE location SET deleted = true WHERE id = ? AND version = ?")
@Where(clause = "deleted = false")
public class Transportation extends BaseEntity {
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
  @Column(name = "transportation_type", nullable = false)
  private TransportationType transportationType;

  @JdbcTypeCode(SqlTypes.ARRAY)
  @Column(name = "operating_days", columnDefinition = "integer[]")
  private List<Integer> operatingDays;
}
