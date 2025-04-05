package com.thy.route_calculator.repository;

import com.thy.route_calculator.model.entity.Transportation;
import com.thy.route_calculator.model.enums.TransportationType;
import jakarta.persistence.LockModeType;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public interface TransportationRepository extends JpaRepository<Transportation, Long> {
  @NonNull
  @Override
  @Lock(value = LockModeType.OPTIMISTIC)
  Optional<Transportation> findById(Long id);

  @Query("SELECT t FROM Transportation t " +
          "WHERE t.transportationType = :type " +
          "AND t.originLocation.city = :originCity " +
          "AND t.destinationLocation.city = :destinationCity " +
          "AND :dayOfWeek IN elements(t.operatingDays)")
  List<Transportation> findByTypeAndOriginCityAndDestinationCityAndOperatingDay(
          @Param("type") TransportationType type,
          @Param("originCity") String originCity,
          @Param("destinationCity") String destinationCity,
          @Param("dayOfWeek") int dayOfWeek
  );

  @Query("SELECT t FROM Transportation t " +
          "WHERE t.transportationType <> :excludedType " +
          "AND t.originLocation.id = :originId " +
          "AND t.destinationLocation.id = :destinationId " +
          "AND :dayOfWeek IN elements(t.operatingDays)")
  List<Transportation> findByTypeNotAndOriginIdAndDestinationIdAndOperatingDay(
          @Param("excludedType") TransportationType excludedType,
          @Param("originId") Long originId,
          @Param("destinationId") Long destinationId,
          @Param("dayOfWeek") int dayOfWeek
  );
}
