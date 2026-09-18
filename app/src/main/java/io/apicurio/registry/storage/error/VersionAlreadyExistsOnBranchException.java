package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

@Getter
public class VersionAlreadyExistsOnBranchException extends AlreadyExistsException {

    private static final long serialVersionUID = 3567623491368394677L;

    private String groupId;
    private String artifactId;
    private String version;
    private String branchId;

    public VersionAlreadyExistsOnBranchException(String groupId, String artifactId, String version,
            String branchId) {
        super(message(groupId, artifactId, version, branchId));
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.branchId = branchId;
    }

    private static String message(String groupId, String artifactId, String version, String branchId) {
        return "Version '" + version + "' (for artifact ID '" + artifactId + "' " + "in group '" + groupId
                + "') already exists in branch '" + branchId + "'.";
    }

    @Override
    public String errorCode() {
        return "version_already_exists_on_branch";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "groupId", groupId,
                "artifactId", artifactId,
                "version", version,
                "branchId", branchId
        );
    }
}
