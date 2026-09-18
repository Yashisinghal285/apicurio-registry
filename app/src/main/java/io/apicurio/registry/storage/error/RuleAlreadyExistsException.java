package io.apicurio.registry.storage.error;

import io.apicurio.registry.types.RegistryErrorDetails;
import io.apicurio.registry.types.RuleType;
import lombok.Getter;

import java.util.Map;

public class RuleAlreadyExistsException extends AlreadyExistsException {

    private static final long serialVersionUID = 2412206165461946827L;

    @Getter
    private final RuleType rule;

    public RuleAlreadyExistsException(RuleType rule) {
        super("A rule named '" + rule.name() + "' already exists.");
        this.rule = rule;
    }

    @Override
    public String errorCode() {
        return "rule_already_exists";
    }

    @Override
    public Map<String, String> context() {
        return RegistryErrorDetails.buildContext(
                "rule", rule != null ? rule.name() : null
        );
    }
}
