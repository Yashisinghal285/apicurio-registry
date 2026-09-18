package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import lombok.Getter;

import java.util.Map;

public class CommentNotFoundException extends NotFoundException {

    private static final long serialVersionUID = -3708928902316703363L;

    @Getter
    private String commentId;

    public CommentNotFoundException(String commentId) {
        super("No comment with ID '" + commentId + "' was found.");
        this.commentId = commentId;
    }

    @Override
    public String errorCode() {
        return "comment_not_found";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "commentId", commentId
        );
    }
}
