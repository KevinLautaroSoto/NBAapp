package com.lautaro.NbaApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lautaro.NbaApp.Models.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {}
