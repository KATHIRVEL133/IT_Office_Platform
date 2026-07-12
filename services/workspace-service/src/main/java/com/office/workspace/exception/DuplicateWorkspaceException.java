package com.office.workspace.exception;

public class DuplicateWorkspaceException
        extends RuntimeException {

    public DuplicateWorkspaceException(String message) {
        super(message);
    }
}