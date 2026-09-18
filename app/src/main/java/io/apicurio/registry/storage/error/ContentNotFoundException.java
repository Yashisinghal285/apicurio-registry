package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

public class ContentNotFoundException extends NotFoundException {

    private static final long serialVersionUID = -3640094007953927715L;

    @Getter
    private Long contentId;

    @Getter
    private String contentHash;

    public ContentNotFoundException(long contentId) {
        super(message(contentId, null));
        this.contentId = contentId;
    }

    public ContentNotFoundException(String contentHash) {
        super(message(null, contentHash));
        this.contentHash = contentHash;
    }

    private static String message(Long contentId, String contentHash) {
        if (contentId != null) {
            return "No content with ID '" + contentId + "' was found.";
        } else {
            return "No content with hash '" + contentHash + "' was found.";
        }
    }

    @Override
    public String errorCode() {
        return "content_not_found";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "contentId", contentId != null ? String.valueOf(contentId) : null,
                "contentHash", contentHash
        );
    }
}
