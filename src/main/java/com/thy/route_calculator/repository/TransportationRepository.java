package com.thy.route_calculator.repository;

import com.thy.route_calculator.model.Transportation;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
  @NonNull
  @Override
  @Lock(value = LockModeType.OPTIMISTIC)
  Optional<Transportation> findById(Long id);
}
