package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

public class GroupAlreadyExistsException extends AlreadyExistsException {

    private static final long serialVersionUID = 2412206165461946827L;

    @Getter
    private final String groupId;

    public GroupAlreadyExistsException(String groupId) {
        super("Group '" + groupId + "' already exists.");
        this.groupId = groupId;
    }

    @Override
    public String errorCode() {
        return "group_already_exists";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "groupId", groupId
        );
    }
}
