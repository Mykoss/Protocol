package org.cloudburstmc.protocol.bedrock.codec.v2193.serializer;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v924.serializer.ServerboundDiagnosticsSerializer_v924;
import org.cloudburstmc.protocol.bedrock.data.MemoryCategoryCounter;
import org.cloudburstmc.protocol.bedrock.data.diagnostics.SystemCategory;
import org.cloudburstmc.protocol.bedrock.data.diagnostics.WhiskerScopeDataSummary;
import org.cloudburstmc.protocol.bedrock.packet.ServerboundDiagnosticsPacket;
import org.cloudburstmc.protocol.bedrock.util.TypeMap;

public class ServerboundDiagnosticsSerializer_v2193 extends ServerboundDiagnosticsSerializer_v924 {

    public ServerboundDiagnosticsSerializer_v2193(TypeMap<MemoryCategoryCounter.Category> memoryCategoryTypes) {
        super(memoryCategoryTypes);
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.serialize(buffer, helper, packet);

        helper.writeArray(buffer, packet.getEntityDiagnostics(), (buf, h, info) -> {
            helper.writeString(buf, info.getDisplayName());
            helper.writeString(buf, info.getEntity());
            buf.writeLongLE(info.getTimeInNs());
            buf.writeByte(info.getPercentOfTotal());
            helper.writeVector3f(buf, info.getPosition());
            helper.writeString(buf, info.getDimension());
        });

        helper.writeArray(buffer, packet.getSystemDiagnostics(), (buf, h, info) -> {
            helper.writeString(buf, info.getDisplayName());
            buf.writeLongLE(info.getSystemIndex());
            buf.writeLongLE(info.getTimeInNs());
            buf.writeByte(info.getPercentOfTotal());
        });

        helper.writeArray(buffer, packet.getSystemCategories(), (buf, h, info) -> {
            helper.writeString(buf, info.categoryName());
            buf.writeLongLE(info.systemIndex());
        });

        helper.writeArray(buffer, packet.getWhiskerScopes(), (buf, h, info) -> {
            helper.writeString(buf, info.label());
            helper.writeString(buf, info.indentation());
            buf.writeLongLE(info.totalHighCostNS());
            buf.writeLongLE(info.totalMidCostNS());
            buf.writeLongLE(info.totalLowCostNS());
        });
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, ServerboundDiagnosticsPacket packet) {
        super.deserialize(buffer, helper, packet);

        helper.readArray(buffer, packet.getEntityDiagnostics(), (buf, h) -> {
            ServerboundDiagnosticsPacket.EntityDiagnostics info = new ServerboundDiagnosticsPacket.EntityDiagnostics();
            info.setDisplayName(helper.readString(buf));
            info.setEntity(helper.readString(buf));
            info.setTimeInNs(buf.readLongLE());
            info.setPercentOfTotal(buf.readUnsignedByte());
            info.setPosition(helper.readVector3f(buf));
            info.setDimension(helper.readString(buf));
            return info;
        });

        helper.readArray(buffer, packet.getSystemDiagnostics(), (buf, h) -> {
            ServerboundDiagnosticsPacket.SystemDiagnostics info = new ServerboundDiagnosticsPacket.SystemDiagnostics();
            info.setDisplayName(helper.readString(buf));
            info.setSystemIndex(buf.readLongLE());
            info.setTimeInNs(buf.readLongLE());
            info.setPercentOfTotal(buf.readUnsignedByte());
            return info;
        });

        helper.readArray(buffer, packet.getSystemCategories(), (buf, h) ->
                new SystemCategory(helper.readString(buf), buf.readLongLE()));

        helper.readArray(buffer, packet.getWhiskerScopes(), (buf, h) ->
                new WhiskerScopeDataSummary(
                        helper.readString(buf),
                        helper.readString(buf),
                        buf.readLongLE(),
                        buf.readLongLE(),
                        buf.readLongLE()));
    }
}
