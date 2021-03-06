/*
 * File: TileRepositoryTest.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
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
public class TileRepositoryTest {
    private static Logger log = LoggerFactory.getLogger(TileRepositoryTest.class);

    @Autowired
    private LangSetRepository repository;

    @Test
    public void testAutoPopulate() throws Exception {
        Iterable<LangSet> allTileSet = repository.findAll();

        assertThat(allTileSet).isNotEmpty();

        for(LangSet set : allTileSet) {
            log.info("LangSet " + set.getName() + " has " + set.getTiles().size() + " tiles.");
            assertThat(set.getTiles()).isNotEmpty();
        }
    }

    @Test
    public void testFindByName() throws Exception {
        LangSet set = repository.findByName("fr");

        assertThat(set).isNotNull();
    }

}