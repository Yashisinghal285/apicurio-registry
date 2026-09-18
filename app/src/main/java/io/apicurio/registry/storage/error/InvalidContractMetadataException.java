package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import io.apicurio.registry.types.RegistryException;

/**
 * Exception thrown when contract metadata is invalid.
 */
public class InvalidContractMetadataException extends RegistryException implements RegistryErrorDetails {

    private static final long serialVersionUID = 1L;

    public InvalidContractMetadataException(String message) {
        super(message);
    }

    public InvalidContractMetadataException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String errorCode() {
        return "invalid_contract_metadata";
    }
}
