package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

public class ConfigPropertyNotFoundException extends NotFoundException {

    private static final long serialVersionUID = -9088094366735526214L;

    @Getter
    private final String propertyName;

    public ConfigPropertyNotFoundException(String propertyName) {
        super("No configuration property named '" + propertyName + "' was found.");
        this.propertyName = propertyName;
    }

    @Override
    public String errorCode() {
        return "config_property_not_found";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "propertyName", propertyName
        );
    }
}
