package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

@Getter
public class BranchAlreadyExistsException extends AlreadyExistsException {

    private final String groupId;
    private final String artifactId;
    private final String branchId;

    public BranchAlreadyExistsException(String groupId, String artifactId, String branchId) {
        super("Branch '" + branchId + "' already exists.");
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.branchId = branchId;
    }

    @Override
    public String errorCode() {
        return "branch_already_exists";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "groupId", groupId,
                "artifactId", artifactId,
                "branchId", branchId
        );
    }
}
