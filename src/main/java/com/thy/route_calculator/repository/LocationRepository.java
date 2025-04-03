package com.thy.route_calculator.repository;

import com.thy.route_calculator.model.Location;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
  @NonNull
  @Override
  @Lock(value = LockModeType.OPTIMISTIC)
  Optional<Location> findById(Long id);
}
