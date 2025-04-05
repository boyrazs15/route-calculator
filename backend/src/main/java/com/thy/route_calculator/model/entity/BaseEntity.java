package com.thy.route_calculator.model.entity;

import jakarta.persistence.*;
import java.time.Instant;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
  public static final Boolean DEFAULT_DELETED_VALUE = false;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  @Column(name = "id")
  private Long id;

  @Version
  @Column(name = "version", nullable = false)
  private Long version;

  @CreatedDate
  @Column(name = "created_at")
  private Instant createdAt;

  @LastModifiedDate
  @Column(name = "updated_at")
  private Instant updatedAt;

  @Column(name = "deleted", nullable = false)
  private Boolean deleted = DEFAULT_DELETED_VALUE;

  @PrePersist
  protected void prePersist() {
    createdAt = Instant.now();
  }

  @PreUpdate
  protected void preUpdate() {
    updatedAt = Instant.now();
  }
}
