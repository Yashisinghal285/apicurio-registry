package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.ArtifactState;
import io.apicurio.registry.types.RegistryErrorDetails;

import java.util.Map;

public class InvalidArtifactStateException extends RegistryStorageException {

    private static final long serialVersionUID = 1L;

    private String groupId;
    private String artifactId;
    private String version;
    private ArtifactState state;

    public InvalidArtifactStateException(String groupId, String artifactId, String version,
            ArtifactState state) {
        super(String.format("Artifact %s [%s] in group (%s) is not active: %s", artifactId, version, groupId,
                state));
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.state = state;
    }

    public InvalidArtifactStateException(ArtifactState previousState, ArtifactState newState) {
        super(errorMsg(previousState, newState));
    }

    public static String errorMsg(ArtifactState previousState, ArtifactState newState) {
        return String.format("Cannot transition artifact from %s to %s", previousState, newState);
    }

    @Override
    public String errorCode() {
        return "invalid_artifact_state";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "groupId", groupId,
                "artifactId", artifactId,
                "version", version,
                "state", state != null ? state.name() : null
        );
    }

}
