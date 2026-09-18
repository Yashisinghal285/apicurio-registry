package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

public class RoleMappingAlreadyExistsException extends AlreadyExistsException {

    private static final long serialVersionUID = 2950093578954587049L;

    @Getter
    private String principalId;

    @Getter
    private String role;

    public RoleMappingAlreadyExistsException(String principalId, String role) {
        super("A mapping for principal '" + principalId + "' and role '" + role + "' already exists.");
        this.principalId = principalId;
        this.role = role;
    }

    @Override
    public String errorCode() {
        return "role_mapping_already_exists";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "principalId", principalId,
                "role", role
        );
    }
}
