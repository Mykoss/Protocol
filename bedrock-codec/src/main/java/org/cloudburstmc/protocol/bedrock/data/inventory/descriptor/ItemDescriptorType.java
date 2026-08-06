package org.cloudburstmc.protocol.bedrock.data.inventory.descriptor;

import lombok.Getter;
import java.util.HashMap;
import java.util.Map;

public enum ItemDescriptorType {
    INVALID("empty"),
    DEFAULT("name"),
    MOLANG("molang"),
    ITEM_TAG("item_tag"),
    @Deprecated DEFERRED("DEFERRED_DEPRECATED"),
    @Deprecated COMPLEX_ALIAS("COMPLEX_ALIAS_DEPRECATED");

    private static final Map<String, ItemDescriptorType> SERIALIZE_NAMES = new HashMap<>(values().length, 1);
    static { for (ItemDescriptorType value : values()) SERIALIZE_NAMES.put(value.serializeName, value); }

    @Getter private final String serializeName;
    ItemDescriptorType(String serializeName) { this.serializeName = serializeName; }
    public static ItemDescriptorType fromName(String serializeName) { return SERIALIZE_NAMES.get(serializeName); }
}
