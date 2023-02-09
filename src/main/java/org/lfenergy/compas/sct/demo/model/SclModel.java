package org.lfenergy.compas.sct.demo.model;

import java.util.Optional;
import java.util.UUID;

public class SclModel {
    private Optional<UUID> id = Optional.of(UUID.randomUUID());
    private String version;
    private String revision;

    public SclModel() {}
    public SclModel(Optional<UUID> id, String version, String revision) {
        this.id = id;
        this.version = version;
        this.revision = revision;
    }

    public void setId(Optional<UUID> id) {
        this.id = id;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Optional<UUID> getId() {
        return id;
    }

    public String getVersion() {
        return version;
    }

    public String getRevision() {
        return revision;
    }
}
