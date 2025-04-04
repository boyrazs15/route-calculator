package com.thy.route_calculator.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "location")
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE location SET deleted = true WHERE id = ? AND version = ?")
@Where(clause = "deleted = false")
public class Location extends BaseEntity {
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @NotNull
  @Column(name = "country", nullable = false)
  private String country;

  @NotNull
  @Column(name = "city", nullable = false)
  private String city;

  @NotNull
  @Column(name = "location_code", unique = true, nullable = false)
  private String locationCode;
}
