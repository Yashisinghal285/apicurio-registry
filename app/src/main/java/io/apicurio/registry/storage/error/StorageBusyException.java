package io.apicurio.registry.storage.error;

public class StorageBusyException extends RegistryStorageException {

    public StorageBusyException(String message) {
        super(message);
    }

    @Override
    public String errorCode() {
        return "storage_busy";
    }
}
