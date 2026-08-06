package org.cloudburstmc.protocol.bedrock.data.skin;

import lombok.Data;
import java.util.UUID;

@Data
public class PersonaPieceData {
    String id;
    PersonaPieceType pieceType;
    UUID packUuid;
    boolean isDefault;
    String productId;

    public PersonaPieceData(String id, String type, String packId, boolean isDefault, String productId) {
        this(id, PersonaPieceType.fromName(type), UUID.fromString(packId), isDefault, productId);
    }

    public PersonaPieceData(String id, PersonaPieceType pieceType, UUID packId, boolean isDefault, String productId) {
        this.id = id;
        this.pieceType = pieceType;
        this.packUuid = packId;
        this.isDefault = isDefault;
        this.productId = productId;
    }

    // Compatibility with the record API used by codecs before v2168.
    public String id() { return id; }
    public String type() { return getType(); }
    public String packId() { return getPackId(); }
    public boolean isDefault() { return isDefault; }
    public String productId() { return productId; }

    public String getPackId() { return packUuid.toString(); }
    public String getType() { return pieceType.getSerializeName(); }
}
