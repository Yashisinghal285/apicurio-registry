package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

public class ContentAlreadyExistsException extends AlreadyExistsException {

    private static final long serialVersionUID = 6415287691931770433L;

    @Getter
    private final Long contentId;

    public ContentAlreadyExistsException(long contentId) {
        super("Content with ID " + contentId + " already exists.");
        this.contentId = contentId;
    }

    @Override
    public String errorCode() {
        return "content_already_exists";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "contentId", contentId != null ? String.valueOf(contentId) : null
        );
    }
}
