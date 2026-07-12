package com.office.tenant.exception;

public class DuplicateTenantException
        extends RuntimeException {

    public DuplicateTenantException(
            String message
    ) {
        super(message);
    }
}