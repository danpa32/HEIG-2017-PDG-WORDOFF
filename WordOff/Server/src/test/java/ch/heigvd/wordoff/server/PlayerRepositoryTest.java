/*
 * File: PlayerRepositoryTest.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.server.Model.Ai;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class PlayerRepositoryTest {
    private static Logger log = LoggerFactory.getLogger(PlayerRepositoryTest.class);

    @Autowired
    private PlayerRepository repository;

    @Test
    public void testCanDifferenciateBetweenUserAndAI() throws Exception {
        User user = new User("testUser");

        user = repository.save(user);

        Player player = repository.findOne(1L);

        Class<? extends Player> playerClass = player.getClass();

        assertThat(playerClass).isEqualTo(Ai.class);

        player = repository.findOne(user.getId());

        playerClass = player.getClass();

        assertThat(playerClass).isEqualTo(User.class);
    }

    @Test
    public void testFirstPlayerIsAI() throws Exception {
        Player player = repository.findOne(1L);

        Class<? extends Player> playerClass = player.getClass();

        assertThat(playerClass).isEqualTo(Ai.class);
    }
}
