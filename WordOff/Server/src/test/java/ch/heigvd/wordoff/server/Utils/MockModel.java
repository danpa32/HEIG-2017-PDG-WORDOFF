/*
 * File: MockModel.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Utils;

import ch.heigvd.wordoff.common.Dto.InvitationStatus;
import ch.heigvd.wordoff.common.Dto.Mode.ModeType;
import ch.heigvd.wordoff.common.Dto.User.RelationStatus;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Modes.DuelMode;
import ch.heigvd.wordoff.server.Model.Modes.TournamentMode;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Slots.*;
import ch.heigvd.wordoff.server.Model.Tiles.LangSet;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockModel {
    private LangSet langSet;
    private List<Slot> slots;
    private List<Slot> emptySlots;
    private SwapRack swapRack;
    private Challenge challenge;
    private Ai ai;
    private User one;
    private User two;
    private Game duelGame;
    private Game aiGame;
    private TournamentMode tournamentMode;
    private Invitation invitation;
    private DuelMode duelMode;
    private final String TOURNAMENT_NAME = "TestTournament";

    private int indexTile;
    private int indexSlot;
    private int indexEmptySlot;

    public MockModel() {
        langSet = new LangSet("fr");
        langSet.setTiles(Arrays.asList(
                new Tile(1, '#', 0),
                new Tile(2, 'A', 1),
                new Tile(3, 'B', 4),
                new Tile(4, 'C', 3),
                new Tile(5, 'D', 5),
                new Tile(6, 'E', 1),
                new Tile(7, 'F', 3),
                new Tile(8, 'G', 2),
                new Tile(9, 'H', 2),
                new Tile(10, 'I', 1),
                new Tile(11, 'J', 8),
                new Tile(12, 'K', 6),
                new Tile(13, 'L', 2),
                new Tile(14, 'M', 1),
                new Tile(15, 'N', 2),
                new Tile(16, 'O', 1),
                new Tile(17, 'P', 2),
                new Tile(18, 'Q', 8),
                new Tile(19, 'R', 2),
                new Tile(20, 'S', 1),
                new Tile(21, 'T', 1),
                new Tile(22, 'U', 1),
                new Tile(23, 'V', 4),
                new Tile(24, 'W', 10),
                new Tile(25, 'X', 10),
                new Tile(26, 'Y', 10),
                new Tile(27, 'Z', 10)
        ));
        indexTile = 0;

        slots = Arrays.asList(
                new SwapSlot((short) 1),
                new Slot((short) 2),
                new L2Slot((short) 3),
                new SwapSlot((short) 4),
                new L3Slot((short) 5),
                new Slot((short) 6),
                new LastSlot((short) 7)
        );
        for(Slot s : slots) {
            s.setTile(getTile());
        }
        indexSlot = 0;

        emptySlots = Arrays.asList(
                new L2Slot((short) 1),
                new Slot((short) 2),
                new SwapSlot((short) 3),
                new Slot((short) 4),
                new L3Slot((short) 5),
                new SwapSlot((short) 6),
                new LastSlot((short) 7)
        );
        indexEmptySlot = 0;

        swapRack = new SwapRack();
        swapRack.addTile(getTile());
        swapRack.addTile(getTile());

        challenge = new Challenge(new ArrayList<>(slots));
        challenge.setSwapRack(swapRack);

        ai = new Ai();
        ai.setId(1L);

        one = new User("testUserOne");
        one.setId(2L);

        two = new User("testUserTwo");
        two.setId(3L);
        two.setRelation(one, RelationStatus.FRIEND);

        tournamentMode = new TournamentMode();
        tournamentMode.setId(1L);
        tournamentMode.setType(ModeType.FRIENDLY_TOURNAMENT);
        tournamentMode.putInvitation(new Invitation(tournamentMode, one, InvitationStatus.ORIGIN, TOURNAMENT_NAME));
        tournamentMode.putInvitation(new Invitation(tournamentMode, two, InvitationStatus.ACCEPT, TOURNAMENT_NAME));
        tournamentMode.setStartDate(LocalDateTime.MIN);

        duelMode = new DuelMode();
        duelMode.setId(2L);
        duelMode.setType(ModeType.FRIEND_DUEL);
        duelMode.putInvitation(new Invitation(duelMode, one, InvitationStatus.ORIGIN, two.getName()));
        invitation = new Invitation(duelMode, two, InvitationStatus.WAITING, one.getName());
        duelMode.putInvitation(invitation);
        duelMode.setStartDate(LocalDateTime.MIN);

        aiGame = new Game(tournamentMode, one, ai, langSet);
        aiGame.setId(1L);
        aiGame.getSideInit().setId(1L);
        aiGame.getSideResp().setId(2L);
        tournamentMode.addGame(aiGame);

        duelGame = new Game(duelMode, one, two, langSet);
        duelGame.setId(2L);
        duelGame.getSideInit().setId(3L);
        duelGame.getSideResp().setId(4L);
        duelMode.addGame(duelGame);
    }

    public LangSet getLangSet() {
        return langSet;
    }

    public Ai getAi() {
        return ai;
    }

    public User getUserOne() {
        return one;
    }

    public User getUserTwo() {
        return two;
    }

    public Tile getTile() {
        List<Tile> tiles = langSet.getTiles();
        indexTile = indexTile % tiles.size();
        return tiles.get(indexTile++);
    }

    public Slot getSlot() {
        List<Slot> s = slots;
        indexSlot = indexSlot % s.size();
        return s.get(indexSlot++);
    }

    public Slot getEmptySlot() {
        List<Slot> s = emptySlots;
        indexEmptySlot = indexEmptySlot % s.size();
        return s.get(indexEmptySlot++);
    }

    public SwapRack getSwapRack() {
        return swapRack;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public Game getDuelGame() {
        return duelGame;
    }

    public Game getAiGame() {
        return aiGame;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public DuelMode getDuelMode() {
        return duelMode;
    }

    public TournamentMode getTournamentMode() {
        return tournamentMode;
    }
}
