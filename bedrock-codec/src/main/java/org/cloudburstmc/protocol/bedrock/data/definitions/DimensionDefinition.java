package org.cloudburstmc.protocol.bedrock.data.definitions;

import java.util.UUID;

/** Describes a data-driven dimension registered through DimensionDataPacket. */
public record DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType,
                                  int dimensionType, UUID packId) {

    public DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType) {
        this(id, maximumHeight, minimumHeight, generatorType, 0, null);
    }

    public DimensionDefinition(String id, int maximumHeight, int minimumHeight, int generatorType,
                               int dimensionType) {
        this(id, maximumHeight, minimumHeight, generatorType, dimensionType, null);
    }

    public UUID getPackId() {
        return packId;
    }
}
