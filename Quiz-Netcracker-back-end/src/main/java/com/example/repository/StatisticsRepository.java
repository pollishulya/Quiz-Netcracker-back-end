package com.example.repository;

import com.example.model.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, UUID> {
    Statistics getStatisticsById(UUID id);

    List<Statistics> getStatisticsByPlayerId(UUID id);
}
