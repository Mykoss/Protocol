package org.cloudburstmc.protocol.bedrock.codec.v2193;

import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.v2169.Bedrock_v2169;
import org.cloudburstmc.protocol.bedrock.codec.v2193.serializer.*;
import org.cloudburstmc.protocol.bedrock.data.PacketRecipient;
import org.cloudburstmc.protocol.bedrock.packet.*;

public final class Bedrock_v2193 {

    public static final BedrockCodec CODEC = Bedrock_v2169.CODEC.toBuilder()
            .protocolVersion(2193)
            .minecraftVersion("1.26.52")
            .helper(() -> new BedrockCodecHelper_v2193(
                    Bedrock_v2169.ENTITY_DATA,
                    Bedrock_v2169.GAME_RULE_TYPES,
                    Bedrock_v2169.ITEM_STACK_REQUEST_TYPES,
                    Bedrock_v2169.CONTAINER_SLOT_TYPES,
                    Bedrock_v2169.PLAYER_ABILITIES,
                    Bedrock_v2169.TEXT_PROCESSING_ORIGINS
            ))
            .updateSerializer(BossEventPacket.class, BossEventSerializer_v2193.INSTANCE)
            .updateSerializer(CameraPresetsPacket.class, CameraPresetsSerializer_v2193.INSTANCE)
            .updateSerializer(ClientboundAttributeLayerSyncPacket.class, ClientboundAttributeLayerSyncSerializer_v2193.INSTANCE)
            .updateSerializer(PrimitiveShapesPacket.class, PrimitiveShapesSerializer_v2193.INSTANCE)
            .updateSerializer(DimensionDataPacket.class, DimensionDataSerializer_v2193.INSTANCE)
            .updateSerializer(InventoryTransactionPacket.class, InventoryTransactionSerializer_v2193.INSTANCE)
            .updateSerializer(ItemStackResponsePacket.class, ItemStackResponseSerializer_v2193.INSTANCE)
            .updateSerializer(MoveEntityDeltaPacket.class, MoveEntityDeltaSerializer_v2193.INSTANCE)
            .updateSerializer(PlayerAuthInputPacket.class, PlayerAuthInputSerializer_v2193.INSTANCE)
            .updateSerializer(PlaySoundPacket.class, PlaySoundSerializer_v2193.INSTANCE)
            .updateSerializer(SubChunkPacket.class, SubChunkSerializer_v2193.INSTANCE)
            .updateSerializer(ServerboundDiagnosticsPacket.class,
                    new ServerboundDiagnosticsSerializer_v2193(Bedrock_v2169.MEMORY_CATEGORY_TYPES))
            .updateSerializer(ServerboundPackSettingChangePacket.class, ServerboundPackSettingChangeSerializer_v2193.INSTANCE)
            .registerPacket(SetPlayerFurnaceOptionsPacket::new, SetPlayerFurnaceOptionsSerializer_v2193.INSTANCE, 351, PacketRecipient.BOTH)
            .registerPacket(RecordStartedPacket::new, RecordStartedSerializer_v2193.INSTANCE, 352, PacketRecipient.CLIENT)
            .build();

    private Bedrock_v2193() {
    }
}
