package io.apicurio.registry.types;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Interface for exceptions that expose structured, surface-agnostic error details.
 */
public interface RegistryErrorDetails {

    /**
     * Stable, surface-agnostic error code (e.g., "artifact_not_found").
     */
    String errorCode();

    /**
     * Structured context parameters for the error (e.g., {"groupId": "...", "artifactId": "..."}).
     */
    default Map<String, String> context() {
        return Collections.emptyMap();
    }

    /**
     * Helper to build an unmodifiable, null-safe context map from key-value pairs.
     * Odd-length trailing keys and null values are omitted.
     */
    static Map<String, String> buildContext(String... keyValues) {
        if (keyValues == null || keyValues.length == 0) {
            return Collections.emptyMap();
        }
        Map<String, String> map = new LinkedHashMap<>();
        for (int i = 0; i < keyValues.length - 1; i += 2) {
            String key = keyValues[i];
            String value = keyValues[i + 1];
            if (key != null && value != null) {
                map.put(key, value);
            }
        }
        return Collections.unmodifiableMap(map);
    }
}
