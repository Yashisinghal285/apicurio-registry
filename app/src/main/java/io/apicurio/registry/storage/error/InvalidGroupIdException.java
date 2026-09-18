package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import io.apicurio.registry.types.RegistryException;

public class InvalidGroupIdException extends RegistryException implements RegistryErrorDetails {

    private static final long serialVersionUID = 1L;

    public InvalidGroupIdException(String message) {
        super(message);
    }

    @Override
    public String errorCode() {
        return "invalid_group_id";
    }

}