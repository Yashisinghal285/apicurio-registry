package io.apicurio.registry.limits;

import io.apicurio.registry.types.RegistryErrorDetails;
import io.apicurio.registry.types.RegistryException;

public class LimitExceededException extends RegistryException implements RegistryErrorDetails {

    private static final long serialVersionUID = -8689268705454834808L;

    public LimitExceededException(String message) {
        super(message);
    }

    public LimitExceededException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String errorCode() {
        return "limit_exceeded";
    }
}
