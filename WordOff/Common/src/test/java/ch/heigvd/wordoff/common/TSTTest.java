/*
 * File: TSTTest.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.common;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TSTTest {
    @Test
    public void mainTest() {
        TST tst = new TST();
        assertTrue(tst.isEmpty());

        tst.insert("Ceci est un mot");
        tst.insert("Ceci est un autre mot");
        tst.insert("banana");
        tst.insert("\'kay");

        assertFalse(tst.isEmpty());
        assertTrue(tst.contains("\'kay"));
        assertFalse(tst.contains("\'ka"));
        assertFalse(tst.contains("\'kayy"));
        assertTrue(tst.contains("Ceci est un mot"));

        tst.clear();
        assertTrue(tst.isEmpty());
        assertFalse(tst.contains("\'kay"));
    }
}