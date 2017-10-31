package ch.heigvd.wordoff.common.Model.Racks;

import ch.heigvd.wordoff.common.Constants;
import ch.heigvd.wordoff.common.Model.Tiles.Tile;

import javax.persistence.*;
import java.util.List;

@Embeddable
@AssociationOverride(
        name="tiles",
        joinTable=@JoinTable(
                name="SwapRack"
        )
)
public class SwapRack extends Rack {
    /**
     * Constructeur
     */
    public SwapRack() {
        super();
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
    public List<Tile> getRack() {
        return super.getRack();
    }

    /**
     * Calcul le bonus/malus sur le score
     *
     * @param score score avant calcul
     * @return le nouveau score
     */
    public int applyBonus(int score) {
        // Rack vide = x2 sur le score
        // Rack non vide = score - valeur de chaque tuile
        if (this.isEmpty()) {
            score *= 2;
        } else {
            for (Tile t : super.getRack()) {
                score -= t.getScore();
            }
        }

        return score;
    }

    /**
     * Retourne la taille du rack
     *
     * @return
     */
    @Override
    public int getMaxSizeRack() {
        return Constants.SWAP_RACK_SIZE;
    }

    @Override
    public Tile getTileByPos(int pos){
        return super.getTileByPos(pos);
    }
}
