package ch.heigvd.wordoff.common.Racks;

import ch.heigvd.wordoff.common.logic.Tile;

import java.util.ArrayList;

public class PlayerRack extends Rack {

    private static final int maxSizeRack = 7;

    /**
     * Constructeur
     */
    public PlayerRack() {
        super(maxSizeRack);
    }

    /**
     * Retourne si le rack est vide
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }

    /**
     * Retirer une tuile du rack
     *
     * @param idTile id de la tuile à retirer
     * @return
     */
    @Override
    public Tile getTile(int idTile) {
        return super.getTile(idTile);
    }

    /**
     * Ajouter une tuile, confirmation.
     *
     * @param tile tuile à ajouter
     */
    @Override
    public boolean addTile(Tile tile) {
        return super.addTile(tile);
    }

    /**
     * Retourne la liste du rack
     *
     * @return
     */
    @Override
    public ArrayList<Tile> getRack() {
        return super.getRack();
    }

    /**
     * Retourne la taille du rack
     *
     * @return
     */
    @Override
    public int getMaxSizeRack() {
        return super.getMaxSizeRack();
    }
}
