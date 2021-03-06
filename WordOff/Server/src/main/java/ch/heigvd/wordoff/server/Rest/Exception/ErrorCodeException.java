/*
 * File: ErrorCodeException.java
 * Authors: Antoine FRIANT, Gabriel LUTHIER, Christopher MEIER, Daniel PALUMBO, Edward RANSOME, Michela ZUCCA
 * Date: 16 janvier 2018
 */

package ch.heigvd.wordoff.server.Rest.Exception;

/**
 * Exception to throw if the content sent by the client has an error.
 */
public class ErrorCodeException extends RuntimeException {
    /**
     * The code which specify the type of error.
     */
    private int code;

    public ErrorCodeException(int code, String s) {
        super(s);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
