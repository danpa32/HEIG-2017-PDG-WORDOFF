package ch.heigvd.wordoff.server.Model;

import ch.heigvd.wordoff.common.IModel.ITile;
import ch.heigvd.wordoff.server.Model.Tiles.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Project : WordOff
 * Date : 10.10.17
 */
public class Bag implements Serializable {

    protected Bag() {}

    public Bag(List<Tile> tiles) {
        setTiles(new LinkedList<>(tiles));
        shuffle();
    }

    private List<Tile> tiles;

    private void shuffle() {
        Collections.shuffle(getTiles());
    }

    public Tile pop() {
        Tile tmp = getTiles().get(0);
        getTiles().remove(0);
        return tmp;
    }

    public List<Tile> getXTile(int nbTiles) {
        List<Tile> newTiles = new ArrayList<>();
        for (int i = 0; i < nbTiles; i++) {
            newTiles.add(pop());
        }
        return newTiles;
    }

    public List<ITile> getSevenTiles() {
        List<ITile> newTiles = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            newTiles.add(getTiles().get(i));
        }
        shuffle();
        return newTiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }
}
