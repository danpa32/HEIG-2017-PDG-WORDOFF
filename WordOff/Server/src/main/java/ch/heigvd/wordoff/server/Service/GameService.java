package ch.heigvd.wordoff.server.Service;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.common.Dto.ChallengeDto;
import ch.heigvd.wordoff.common.Dto.SideDto;
import ch.heigvd.wordoff.common.WordAnalyzer;
import ch.heigvd.wordoff.server.Model.*;
import ch.heigvd.wordoff.server.Model.Racks.SwapRack;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;
import ch.heigvd.wordoff.server.Rest.Exception.InvalidAiLevel;
import ch.heigvd.wordoff.server.Rest.Exception.InvalidWordException;
import ch.heigvd.wordoff.server.Rest.Exception.WrongPlayer;
import ch.heigvd.wordoff.server.Util.ChallengeFactory;
import ch.heigvd.wordoff.server.Util.DictionaryLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;

/**
 * Service used to s
 */
@Service
public class GameService {
    private DictionaryLoader dictionaryLoader;

    public GameService() {
        dictionaryLoader = new DictionaryLoader();
    }

    public Game play(Game game, Player player, Challenge challenge) {
        // Load the dico
        dictionaryLoader.loadDictionary(game.getLang());

        Side side = null;

        // Check if it's the right player who try to play
        if (player.equals(game.getCurrPlayer())) {
            String wordChallenge = challenge.getWord();

            // If the word doesn't exists
            if (!dictionaryLoader.getDico(game.getLang()).contains(wordChallenge)) {
                throw new InvalidWordException("The word is not in the dictionary !");
            } else {
                // get a list of the tile taken from the bag of the game
                List<Tile> newTiles = game.getBag().getXTile(Constants.PLAYER_RACK_SIZE -
                        game.getSideOfPlayer(player).getPlayerRack().getTiles().size());

                // Update the player side
                updatePlayerSide(game.getSideOfPlayer(player), challenge, newTiles);

                // Send swap tiles to other player
                game.getSideOfPlayer(game.getOtherPlayer(player)).getChallenge().setSwapRack(game.getSideOfPlayer(player).getChallenge().getSwapRack());

                // set new empty swap rack
                game.getSideOfPlayer(player).getChallenge().setSwapRack(new SwapRack());

                // switch player
                game.setCurrPlayer(game.getOtherPlayer(player));

                // PlayerDto Side updated
                side = game.getSideOfPlayer(player);
            }
        } else {
            throw new WrongPlayer("Not player turn to play !");
        }

        return game;
    }

    public Game makeAiPLay(Game game, User player) {
        List<ITile> word = new ArrayList<>();

        WordAnalyzer wa = new WordAnalyzer(dictionaryLoader.getDico(game.getLang()), game.getSideResp().getChallenge(), game.getSideResp().getPlayerRack());

        // get the words by score
        TreeMap<Integer, List<ITile>> wordsByScore = wa.getWordsByScore();

        // Get the size of the treemap
        int sizeWordsByScore = wordsByScore.size();

        if (sizeWordsByScore == 0) {
            /* The AI can't create a word, it pass */
        } else if (sizeWordsByScore == 1) {
            // The AI play the only best possible word
            word = new ArrayList<>(wordsByScore.firstEntry().getValue());
        } else {
            Random random = new Random();
            int index;
            int lowerBound = sizeWordsByScore / 3;
            int middleUpperBound = (lowerBound * 2) + 1;

            switch (player.getLevel()) {
                case 1:
                    index = random.nextInt(lowerBound);
                    break;
                case 2:
                    index = random.nextInt(middleUpperBound) + lowerBound;
                    break;
                case 3:
                    index = random.nextInt(sizeWordsByScore - 1) + middleUpperBound;
                    break;
                case 4:
                    index = sizeWordsByScore - 1;
                    break;
                default:
                    throw new InvalidAiLevel("This ai level is not handled !");
            }

            // Get the List of tile chosen by the Ai
            word = new ArrayList<>(wordsByScore.values()).get(index);
        }

        // Move word to challenge
        game.getSideResp().setChallenge(word);

        // get a list of the tile taken from the bag of the game
        List<Tile> newTiles = game.getBag().getXTile(Constants.PLAYER_RACK_SIZE -
                game.getSideOfPlayer(player).getPlayerRack().getTiles().size());

        // Update the side of the Ai
        updatePlayerSide(game.getSideResp(), game.getSideResp().getChallenge(), newTiles);

        // Send swap tiles to other player
        game.getSideOfPlayer(game.getOtherPlayer(player)).getChallenge().setSwapRack(game.getSideOfPlayer(player).getChallenge().getSwapRack());

        // set new empty swap rack
        game.getSideOfPlayer(player).getChallenge().setSwapRack(new SwapRack());

        // switch player
        game.setCurrPlayer(game.getOtherPlayer(player));

        /* TODO -> set side as sideDTO */

        return game;
    }

    /**
     * @brief Update the player side
     * @param side Side of the player
     * @param challenge Challenge in the player side
     * @param newTiles New tiles taken from the bag
     * @details - update the score
     *          - create a log with the word and the score
     *          - add new tiles to player rack
     *          - create a new challenge
     */
    private void updatePlayerSide(Side side, Challenge challenge, List<Tile> newTiles) {
        // Update the score of the side of the player
        side.updateScore(challenge.getScore());

        // Create the answer for the history
        side.addAnswer(challenge.getWord(), side.getScore());

        // add the new tiles to the player Rack
        side.addTilesToPlayerRack(newTiles);

        // Create new challenge
        side.setChallenge(new ChallengeFactory(side).create());
    }
}