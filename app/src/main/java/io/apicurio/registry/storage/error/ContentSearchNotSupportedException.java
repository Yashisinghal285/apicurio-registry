package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import io.apicurio.registry.types.RegistryException;

public class ContentSearchNotSupportedException extends RegistryException implements RegistryErrorDetails {

    private static final long serialVersionUID = 1L;

    public ContentSearchNotSupportedException(String message) {
        super(message);
    }

    @Override
    public String errorCode() {
        return "content_search_not_supported";
    }

}
