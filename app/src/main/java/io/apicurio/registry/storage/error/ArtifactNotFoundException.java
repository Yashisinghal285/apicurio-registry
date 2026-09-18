package io.apicurio.registry.storage.error;

import io.apicurio.registry.model.GroupId;
import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

public class ArtifactNotFoundException extends NotFoundException {

    private static final long serialVersionUID = -3614783501078800654L;

    @Getter
    private String groupId;

    @Getter
    private String artifactId;

    public ArtifactNotFoundException(String groupId, String artifactId) {
        super(message(groupId, artifactId));
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    public ArtifactNotFoundException(String groupId, String artifactId, Throwable cause) {
        super(message(groupId, artifactId), cause);
        this.groupId = groupId;
        this.artifactId = artifactId;
    }

    public ArtifactNotFoundException(String artifactId) {
        this(GroupId.DEFAULT.getRawGroupIdWithDefaultString(), artifactId);
    }

    private static String message(String groupId, String artifactId) {
        return "No artifact with ID '" + artifactId + "' in group '" + groupId + "' was found.";
    }

    @Override
    public String errorCode() {
        return "artifact_not_found";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "groupId", groupId,
                "artifactId", artifactId
        );
    }
}
