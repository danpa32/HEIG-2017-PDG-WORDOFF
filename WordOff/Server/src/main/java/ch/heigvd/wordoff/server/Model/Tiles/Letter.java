/*
 * File: Letter.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Model.Tiles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Letter implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;

    /**
     * The value of the letter.
     */
    private char value;

    /**
     * The score of the letter.
     */
    private int score;

    @Column(name = "lang_set_id")
    private Integer langSetId;

    public Letter() {}

    public Letter(char value, int score) {
        this.value = value;
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public char getValue() {
        return value;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
