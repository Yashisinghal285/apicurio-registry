package io.apicurio.registry.types;

import io.apicurio.registry.limits.LimitExceededException;
import io.apicurio.registry.model.GAV;
import io.apicurio.registry.rules.violation.RuleViolationException;
import io.apicurio.registry.storage.error.AlreadyExistsException;
import io.apicurio.registry.storage.error.ArtifactAlreadyExistsException;
import io.apicurio.registry.storage.error.ArtifactNotFoundException;
import io.apicurio.registry.storage.error.BranchAlreadyExistsException;
import io.apicurio.registry.storage.error.BranchNotFoundException;
import io.apicurio.registry.storage.error.CommentNotFoundException;
import io.apicurio.registry.storage.error.CommitFailedException;
import io.apicurio.registry.storage.error.ConfigPropertyNotFoundException;
import io.apicurio.registry.storage.error.ContentAlreadyExistsException;
import io.apicurio.registry.storage.error.ContentNotFoundException;
import io.apicurio.registry.storage.error.ContentSearchNotSupportedException;
import io.apicurio.registry.storage.error.DownloadNotFoundException;
import io.apicurio.registry.storage.error.GroupAlreadyExistsException;
import io.apicurio.registry.storage.error.GroupNotEmptyException;
import io.apicurio.registry.storage.error.GroupNotFoundException;
import io.apicurio.registry.storage.error.InvalidArtifactIdException;
import io.apicurio.registry.storage.error.InvalidArtifactStateException;
import io.apicurio.registry.storage.error.InvalidArtifactTypeException;
import io.apicurio.registry.storage.error.InvalidContentException;
import io.apicurio.registry.storage.error.InvalidContractMetadataException;
import io.apicurio.registry.storage.error.InvalidGroupIdException;
import io.apicurio.registry.storage.error.InvalidPropertiesException;
import io.apicurio.registry.storage.error.InvalidPropertyValueException;
import io.apicurio.registry.storage.error.InvalidVersionStateException;
import io.apicurio.registry.storage.error.NotAllowedException;
import io.apicurio.registry.storage.error.NotFoundException;
import io.apicurio.registry.storage.error.ReadOnlyStorageException;
import io.apicurio.registry.storage.error.RegistryStorageException;
import io.apicurio.registry.storage.error.RoleMappingAlreadyExistsException;
import io.apicurio.registry.storage.error.RoleMappingNotFoundException;
import io.apicurio.registry.storage.error.RuleAlreadyExistsException;
import io.apicurio.registry.storage.error.RuleNotFoundException;
import io.apicurio.registry.storage.error.StorageBusyException;
import io.apicurio.registry.storage.error.VersionAlreadyExistsException;
import io.apicurio.registry.storage.error.VersionAlreadyExistsOnBranchException;
import io.apicurio.registry.storage.error.VersionNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistryErrorDetailsTest {

    @Test
    void testContextHelperEdgeCases() {
        assertTrue(RegistryErrorDetails.buildContext().isEmpty());
        assertTrue(RegistryErrorDetails.buildContext((String[]) null).isEmpty());

        Map<String, String> ctx = RegistryErrorDetails.buildContext(
                "key1", "val1",
                "key2", null,
                null, "val3",
                "key4"
        );
        assertEquals(1, ctx.size());
        assertEquals("val1", ctx.get("key1"));

        assertThrows(UnsupportedOperationException.class, () -> ctx.put("key5", "val5"));
    }

    @Test
    void testArtifactNotFoundException() {
        ArtifactNotFoundException ex = new ArtifactNotFoundException("my-group", "my-artifact");
        assertEquals("artifact_not_found", ex.errorCode());
        assertEquals(Map.of("groupId", "my-group", "artifactId", "my-artifact"), ex.context());

        ArtifactNotFoundException defaultGroupEx = new ArtifactNotFoundException("standalone-artifact");
        assertEquals("artifact_not_found", defaultGroupEx.errorCode());
        assertEquals("default", defaultGroupEx.context().get("groupId"));
        assertEquals("standalone-artifact", defaultGroupEx.context().get("artifactId"));
    }

    @Test
    void testArtifactAlreadyExistsException() {
        ArtifactAlreadyExistsException ex = new ArtifactAlreadyExistsException("my-group", "my-artifact");
        assertEquals("artifact_already_exists", ex.errorCode());
        assertEquals(Map.of("groupId", "my-group", "artifactId", "my-artifact"), ex.context());
    }

    @Test
    void testGroupNotFoundException() {
        GroupNotFoundException ex = new GroupNotFoundException("my-group");
        assertEquals("group_not_found", ex.errorCode());
        assertEquals(Map.of("groupId", "my-group"), ex.context());
    }

    @Test
    void testGroupAlreadyExistsException() {
        GroupAlreadyExistsException ex = new GroupAlreadyExistsException("my-group");
        assertEquals("group_already_exists", ex.errorCode());
        assertEquals(Map.of("groupId", "my-group"), ex.context());
    }

    @Test
    void testGroupNotEmptyException() {
        GroupNotEmptyException ex = new GroupNotEmptyException("my-group", 5);
        assertEquals("group_not_empty", ex.errorCode());
        assertEquals("my-group", ex.context().get("groupId"));
        assertEquals("5", ex.context().get("artifactCount"));
    }

    @Test
    void testVersionNotFoundException() {
        VersionNotFoundException ex1 = new VersionNotFoundException("g1", "a1", "1.0.0");
        assertEquals("version_not_found", ex1.errorCode());
        assertEquals("g1", ex1.context().get("groupId"));
        assertEquals("a1", ex1.context().get("artifactId"));
        assertEquals("1.0.0", ex1.context().get("version"));
        assertFalse(ex1.context().containsKey("globalId"));

        VersionNotFoundException ex2 = new VersionNotFoundException(42L);
        assertEquals("version_not_found", ex2.errorCode());
        assertEquals("42", ex2.context().get("globalId"));
        assertFalse(ex2.context().containsKey("groupId"));

        GAV gav = new GAV("g-gav", "a-gav", "2.0.0");
        VersionNotFoundException ex3 = new VersionNotFoundException(gav, null);
        assertEquals("version_not_found", ex3.errorCode());
        assertEquals("g-gav", ex3.context().get("groupId"));
        assertEquals("a-gav", ex3.context().get("artifactId"));
        assertEquals("2.0.0", ex3.context().get("version"));
    }

    @Test
    void testVersionAlreadyExistsException() {
        VersionAlreadyExistsException ex1 = new VersionAlreadyExistsException("g1", "a1", "1.0.0");
        assertEquals("version_already_exists", ex1.errorCode());
        assertEquals("g1", ex1.context().get("groupId"));
        assertEquals("a1", ex1.context().get("artifactId"));
        assertEquals("1.0.0", ex1.context().get("version"));

        VersionAlreadyExistsException ex2 = new VersionAlreadyExistsException(101L);
        assertEquals("version_already_exists", ex2.errorCode());
        assertEquals("101", ex2.context().get("globalId"));
    }

    @Test
    void testVersionAlreadyExistsOnBranchException() {
        VersionAlreadyExistsOnBranchException ex = new VersionAlreadyExistsOnBranchException("g1", "a1", "1.0.0", "main");
        assertEquals("version_already_exists_on_branch", ex.errorCode());
        assertEquals("g1", ex.context().get("groupId"));
        assertEquals("a1", ex.context().get("artifactId"));
        assertEquals("1.0.0", ex.context().get("version"));
        assertEquals("main", ex.context().get("branchId"));
    }

    @Test
    void testBranchExceptions() {
        BranchNotFoundException notFound = new BranchNotFoundException("g1", "a1", "dev");
        assertEquals("branch_not_found", notFound.errorCode());
        assertEquals("dev", notFound.context().get("branchId"));

        BranchAlreadyExistsException exists = new BranchAlreadyExistsException("g1", "a1", "dev");
        assertEquals("branch_already_exists", exists.errorCode());
        assertEquals("dev", exists.context().get("branchId"));
    }

    @Test
    void testContentExceptions() {
        ContentNotFoundException notFoundById = new ContentNotFoundException(99L);
        assertEquals("content_not_found", notFoundById.errorCode());
        assertEquals("99", notFoundById.context().get("contentId"));

        ContentNotFoundException notFoundByHash = new ContentNotFoundException("abc123hash");
        assertEquals("content_not_found", notFoundByHash.errorCode());
        assertEquals("abc123hash", notFoundByHash.context().get("contentHash"));

        ContentAlreadyExistsException exists = new ContentAlreadyExistsException(77L);
        assertEquals("content_already_exists", exists.errorCode());
        assertEquals("77", exists.context().get("contentId"));
    }

    @Test
    void testRuleExceptions() {
        RuleNotFoundException notFound = new RuleNotFoundException(RuleType.VALIDITY);
        assertEquals("rule_not_found", notFound.errorCode());
        assertEquals("VALIDITY", notFound.context().get("rule"));

        RuleAlreadyExistsException exists = new RuleAlreadyExistsException(RuleType.COMPATIBILITY);
        assertEquals("rule_already_exists", exists.errorCode());
        assertEquals("COMPATIBILITY", exists.context().get("rule"));
    }

    @Test
    void testRoleMappingExceptions() {
        RoleMappingNotFoundException notFound = new RoleMappingNotFoundException("user-1", "ADMIN");
        assertEquals("role_mapping_not_found", notFound.errorCode());
        assertEquals("user-1", notFound.context().get("principalId"));
        assertEquals("ADMIN", notFound.context().get("role"));

        RoleMappingAlreadyExistsException exists = new RoleMappingAlreadyExistsException("user-2", "DEVELOPER");
        assertEquals("role_mapping_already_exists", exists.errorCode());
        assertEquals("user-2", exists.context().get("principalId"));
        assertEquals("DEVELOPER", exists.context().get("role"));
    }

    @Test
    void testCommitFailedException() {
        CommitFailedException ex = new CommitFailedException("g1", "a1", "Concurrent write detected");
        assertEquals("commit_failed", ex.errorCode());
        assertEquals("g1", ex.context().get("groupId"));
        assertEquals("a1", ex.context().get("artifactId"));
        assertEquals("Concurrent write detected", ex.context().get("reason"));
    }

    @Test
    void testConfigPropertyAndCommentAndDownload() {
        ConfigPropertyNotFoundException configEx = new ConfigPropertyNotFoundException("apicurio.storage.kind");
        assertEquals("config_property_not_found", configEx.errorCode());
        assertEquals("apicurio.storage.kind", configEx.context().get("propertyName"));

        CommentNotFoundException commentEx = new CommentNotFoundException("comment-123");
        assertEquals("comment_not_found", commentEx.errorCode());
        assertEquals("comment-123", commentEx.context().get("commentId"));

        DownloadNotFoundException downloadEx = new DownloadNotFoundException();
        assertEquals("download_not_found", downloadEx.errorCode());
        assertTrue(downloadEx.context().isEmpty());
    }

    @Test
    void testLimitExceededAndRuleViolation() {
        LimitExceededException limitEx = new LimitExceededException("Artifact limit reached");
        assertEquals("limit_exceeded", limitEx.errorCode());
        assertTrue(limitEx.context().isEmpty());

        RuleViolationException ruleEx = new RuleViolationException("Rule violated", RuleType.INTEGRITY, "SYNTAX_ONLY", Collections.emptySet());
        assertEquals("rule_violation", ruleEx.errorCode());
        assertEquals("INTEGRITY", ruleEx.context().get("ruleType"));
    }

    @Test
    void testInvalidStateExceptions() {
        InvalidArtifactStateException artStateEx = new InvalidArtifactStateException("g1", "a1", "1.0", ArtifactState.DISABLED);
        assertEquals("invalid_artifact_state", artStateEx.errorCode());
        assertEquals("g1", artStateEx.context().get("groupId"));
        assertEquals("DISABLED", artStateEx.context().get("state"));

        InvalidVersionStateException verStateEx = new InvalidVersionStateException("g1", "a1", "1.0", VersionState.DEPRECATED);
        assertEquals("invalid_version_state", verStateEx.errorCode());
        assertEquals("1.0", verStateEx.context().get("version"));
        assertEquals("DEPRECATED", verStateEx.context().get("state"));
    }

    @Test
    void testValidationAndStorageExceptions() {
        assertEquals("invalid_artifact_id", new InvalidArtifactIdException("bad-id").errorCode());
        assertEquals("invalid_artifact_type", new InvalidArtifactTypeException("bad-type").errorCode());
        assertEquals("invalid_group_id", new InvalidGroupIdException("bad-group").errorCode());
        assertEquals("invalid_content", new InvalidContentException("bad-content").errorCode());
        assertEquals("invalid_contract_metadata", new InvalidContractMetadataException("bad-meta").errorCode());
        assertEquals("invalid_properties", new InvalidPropertiesException("bad-props", null).errorCode());
        assertEquals("invalid_property_value", new InvalidPropertyValueException("bad-prop-val").errorCode());
        assertEquals("read_only_storage", new ReadOnlyStorageException("read-only").errorCode());
        assertEquals("storage_busy", new StorageBusyException("busy").errorCode());
        assertEquals("not_allowed", new NotAllowedException("not allowed").errorCode());
        assertEquals("content_search_not_supported", new ContentSearchNotSupportedException("search disabled").errorCode());
    }

    @Test
    void testBaseStorageExceptionDefaults() {
        RegistryStorageException base = new RegistryStorageException("general error");
        assertEquals("storage_error", base.errorCode());
        assertTrue(base.context().isEmpty());

        NotFoundException nf = new NotFoundException("general not found") {};
        assertEquals("not_found", nf.errorCode());

        AlreadyExistsException ae = new AlreadyExistsException("general already exists") {};
        assertEquals("already_exists", ae.errorCode());
    }

    @Test
    void testPolymorphicGenericAccess() {
        Throwable t = new ArtifactNotFoundException("io.github.test", "weather");

        assertTrue(t instanceof RegistryErrorDetails);
        RegistryErrorDetails details = (RegistryErrorDetails) t;
        assertEquals("artifact_not_found", details.errorCode());

        // Generic access enables surfaces like MCP to construct resource identifiers without casting
        String mcpIdentifier = details.context().get("groupId") + "/" + details.context().get("artifactId");
        assertEquals("io.github.test/weather", mcpIdentifier);

        // Generic access enables Iceberg to construct table not found errors without casting
        String icebergTable = details.context().get("artifactId");
        assertEquals("weather", icebergTable);
    }
}
