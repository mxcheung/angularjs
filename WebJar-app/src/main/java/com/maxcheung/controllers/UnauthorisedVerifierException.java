package com.maxcheung.controllers;

public class UnauthorisedVerifierException extends Exception {

    private static final long serialVersionUID = -7167645019014692507L;

    public UnauthorisedVerifierException(String message) {
        super(message);
    }
}
