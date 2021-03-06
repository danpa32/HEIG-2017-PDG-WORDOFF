/*
 * File: PowerDto.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common.Dto.Game;

public enum PowerDto {
    PASS(0, "Passe le tour"),
    PEEK(10, "Aperçu"),
    HINT(10, "Indice"),
    WORDANALYZER(10, "Word Analyzer"),
    DISCARD_2(0, "Jeter 2 tuiles"),
    DISCARD_ALL(10, "Tout jeter");

    private final int COST;
    private final String NAME;

    PowerDto(int cost, String name) {
        this.COST = cost;
        this.NAME = name;
    }

    public String getName() {
        return NAME;
    }

    public int getCost() {
        return COST;
    }
}
