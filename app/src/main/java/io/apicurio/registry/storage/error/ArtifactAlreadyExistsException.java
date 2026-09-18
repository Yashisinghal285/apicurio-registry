package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

public class ArtifactAlreadyExistsException extends AlreadyExistsException {

    private static final long serialVersionUID = -1015140450163088675L;

    @Getter
    private String groupId;

    @Getter
    private String artifactId;

    public ArtifactAlreadyExistsException(String groupId, String artifactId) {
        super(message(groupId, artifactId));
        this.artifactId = artifactId;
        this.groupId = groupId;
    }

    private static String message(String groupId, String artifactId) {
        return "An artifact with ID '" + artifactId + "' in group '" + groupId + "' already exists.";
    }

    @Override
    public String errorCode() {
        return "artifact_already_exists";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "groupId", groupId,
                "artifactId", artifactId
        );
    }
}
