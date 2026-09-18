package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import io.apicurio.registry.types.VersionState;

import java.util.Map;

public class InvalidVersionStateException extends RegistryStorageException {

    private static final long serialVersionUID = 1L;

    private String groupId;
    private String artifactId;
    private String version;
    private VersionState state;

    public InvalidVersionStateException(String groupId, String artifactId, String version,
            VersionState state) {
        super(String.format("Artifact %s [%s] in group (%s) is not active: %s", artifactId, version, groupId,
                state));
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.state = state;
    }

    public InvalidVersionStateException(VersionState previousState, VersionState newState) {
        super(errorMsg(previousState, newState));
    }

    public static String errorMsg(VersionState previousState, VersionState newState) {
        return String.format("Cannot transition artifact from %s to %s", previousState, newState);
    }

    @Override
    public String errorCode() {
        return "invalid_version_state";
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
