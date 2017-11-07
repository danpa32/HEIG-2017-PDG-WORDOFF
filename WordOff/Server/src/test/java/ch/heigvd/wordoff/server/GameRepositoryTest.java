package ch.heigvd.wordoff.server;

import ch.heigvd.wordoff.server.Model.Bag;
import ch.heigvd.wordoff.server.Model.Game;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.server.Model.User;
import ch.heigvd.wordoff.server.Repository.GameRepository;
import ch.heigvd.wordoff.server.Repository.PlayerRepository;
import ch.heigvd.wordoff.server.Repository.SideRepository;
import ch.heigvd.wordoff.server.Repository.LangSetRepository;
import ch.heigvd.wordoff.server.Util.ChallengeFactory;
import ch.heigvd.wordoff.server.Model.Answer;
import ch.heigvd.wordoff.server.Model.Challenge;
import ch.heigvd.wordoff.server.Model.Player;
import ch.heigvd.wordoff.server.Model.Racks.PlayerRack;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Side;
import ch.heigvd.wordoff.server.Model.Slots.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class GameRepositoryTest {
    private static Logger log = LoggerFactory.getLogger(GameRepositoryTest.class);

    @Autowired
    private GameRepository repository;

    @Autowired
    private SideRepository sideRepository;

    @Autowired
    private LangSetRepository tilesRepository;

    private Player one;
    private Player two;
    private Player ai;

    @Autowired
    private PlayerRepository playerRepository;

    @Before
    public void setUp() {
        one = playerRepository.save(new User("one"));
        two = playerRepository.save(new User("two"));
        ai = playerRepository.findOne(1L);
    }

    @Test
    public void testCanCreateAndSaveAGame() throws Exception {
        LangSet set = tilesRepository.findByName("Français");
        Game game = new Game(one, two, set);

        repository.save(game);

        Game savedGame = repository.findOne(1L);
        assertThat(savedGame).isNotNull();
    }

    @Test
    public void testCanCreateAndSaveSide() throws Exception {
        LangSet set = tilesRepository.findByName("Français");
        List<Tile> tiles = set.getTiles();
        Bag bag = new Bag(tiles);
        Player player = new Player("testPlayer");
        Side side = new Side(player);

        // Answers
        List<Answer> answers = side.getAnswers();
        answers.add(new Answer(side, (short)1, "Hello", 23));
        answers.add(new Answer(side, (short)2,"World", 32));
        answers.add(new Answer(side, (short)3,"Bye", 14));

        // Challenge
        Challenge challenge = new ChallengeFactory(side).addAll(Arrays.asList(
                L2Slot.class,
                Slot.class,
                SwapSlot.class,
                L3Slot.class,
                Slot.class,
                SwapSlot.class,
                LastSlot.class
        )).create();

        challenge.addTile(bag.pop());
        side.setChallenge(challenge);

        // Racks
        SwapRack swapRack = side.getChallenge().getSwapRack();
        swapRack.addTile(bag.pop());

        PlayerRack playerRack = side.getPlayerRack();
        playerRack.addTile(bag.pop());
        playerRack.addTile(bag.pop());

        sideRepository.save(side);

        Side savedSide = sideRepository.findOne(1L);
        assertThat(savedSide).isNotNull();
    }
}
