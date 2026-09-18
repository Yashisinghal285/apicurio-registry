package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import io.apicurio.registry.types.RuleType;
import lombok.Getter;

import java.util.Map;

public class RuleNotFoundException extends NotFoundException {

    private static final long serialVersionUID = -5024749463194169679L;

    @Getter
    private final RuleType rule;

    public RuleNotFoundException(RuleType rule) {
        super(message(rule));
        this.rule = rule;
    }

    public RuleNotFoundException(RuleType rule, Throwable cause) {
        super(message(rule), cause);
        this.rule = rule;
    }

    private static String message(RuleType rule) {
        return "No rule named '" + rule.name() + "' was found.";
    }

    @Override
    public String errorCode() {
        return "rule_not_found";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "rule", rule != null ? rule.name() : null
        );
    }
}
