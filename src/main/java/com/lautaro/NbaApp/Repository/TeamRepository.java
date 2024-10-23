package com.lautaro.NbaApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.lautaro.NbaApp.Models.Team;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     * Finds teams whose names contain the specified string, ignoring case.
     *
     * @param name The string to search for within team names.
     * @return A list of teams with names containing the input string.
     */
    List<Team> findByNameContainingIgnoreCase(String name);
}
