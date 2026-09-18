package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import io.apicurio.registry.types.RegistryException;

public class InvalidPropertyValueException extends RegistryException implements RegistryErrorDetails {

    private static final long serialVersionUID = 4930984250014469626L;

    public InvalidPropertyValueException(String message) {
        super(message);
    }

    @Override
    public String errorCode() {
        return "invalid_property_value";
    }

}