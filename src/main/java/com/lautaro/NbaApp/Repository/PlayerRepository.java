package com.lautaro.NbaApp.Repository;

import com.lautaro.NbaApp.Models.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    /**
     * Finds players whose first name or last name contains the specified string, ignoring case.
     *
     * @param firstName, lastName The string to search for within first name or last name.
     * @return A list of players with matching first name or last name.
     */
    List<Player> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

    Page<Player> findAll (Pageable pageable);
}
