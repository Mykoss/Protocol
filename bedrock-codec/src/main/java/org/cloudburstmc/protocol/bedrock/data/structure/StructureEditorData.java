package org.cloudburstmc.protocol.bedrock.data.structure;

/**
 * Describes the editable state of a structure block.
 *
 * @param name               the structure template name
 * @param includingPlayers   whether players should be included in saves
 * @param boundingBoxVisible whether the structure bounding box should be shown
 * @param type               the structure block mode
 * @param settings           the structure placement and export settings
 * @param dataField          the auxiliary data field used by the structure block
 * @param redstoneSaveMode   the save mode used when triggered by redstone
 * @param filteredName       the profanity-filtered form of {@code name}
 */
public record StructureEditorData(String name, boolean includingPlayers, boolean boundingBoxVisible, StructureBlockType type,
                                  StructureSettings settings, String dataField, StructureRedstoneSaveMode redstoneSaveMode, String filteredName) {

    /**
     * Compatibility constructor matching the field order used by the v2168 upstream serializer.
     */
    public StructureEditorData(String name, String filteredName, String dataField, boolean includingPlayers,
                               boolean boundingBoxVisible, StructureBlockType type, StructureSettings settings,
                               StructureRedstoneSaveMode redstoneSaveMode) {
        this(name, includingPlayers, boundingBoxVisible, type, settings, dataField, redstoneSaveMode, filteredName);
    }

    public boolean isIncludingPlayers() {
        return includingPlayers;
    }

    public boolean isBoundingBoxVisible() {
        return boundingBoxVisible;
    }
}
