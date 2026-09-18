package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

public class RoleMappingNotFoundException extends NotFoundException {

    private static final long serialVersionUID = -2662972482016902671L;

    @Getter
    private String principalId;

    @Getter
    private String role;

    public RoleMappingNotFoundException(String principalId) {
        super("No role mapping for principal '" + principalId + "' was found.");
        this.principalId = principalId;
    }

    public RoleMappingNotFoundException(String principalId, String role) {
        super("No mapping for principal '" + principalId + "' and role '" + role + "' was found.");
        this.principalId = principalId;
        this.role = role;
    }

    @Override
    public String errorCode() {
        return "role_mapping_not_found";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "principalId", principalId,
                "role", role
        );
    }
}
