package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.ItemStackResponseSerializer_v419;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;

public class ItemStackResponseSerializer_v2168 extends ItemStackResponseSerializer_v419 {
    public static final ItemStackResponseSerializer_v2168 INSTANCE = new ItemStackResponseSerializer_v2168();

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, ItemStackResponsePacket packet) {
        int startIndex = buffer.writerIndex();

        System.out.println("[DEBUG-STACK-RESPONSE] ===== SERIALIZE START =====");
        System.out.println("[DEBUG-STACK-RESPONSE] entries=" + packet.getEntries().size());

        for (int i = 0; i < packet.getEntries().size(); i++) {
            System.out.println("[DEBUG-STACK-RESPONSE] entry[" + i + "]=" + packet.getEntries().get(i));
        }

        try {
            super.serialize(buffer, helper, packet);
        } catch (RuntimeException | Error throwable) {
            int written = buffer.writerIndex() - startIndex;
            System.out.println("[DEBUG-STACK-RESPONSE] SERIALIZE EXCEPTION=" + throwable);
            System.out.println("[DEBUG-STACK-RESPONSE] bytesWrittenBeforeException=" + written);
            if (written > 0) {
                System.out.println("[DEBUG-STACK-RESPONSE] partialHex=" +
                        ByteBufUtil.hexDump(buffer, startIndex, written));
            }
            throwable.printStackTrace();
            throw throwable;
        }

        int written = buffer.writerIndex() - startIndex;
        System.out.println("[DEBUG-STACK-RESPONSE] bytesWritten=" + written);
        System.out.println("[DEBUG-STACK-RESPONSE] hex=" +
                ByteBufUtil.hexDump(buffer, startIndex, written));
        System.out.println("[DEBUG-STACK-RESPONSE] ===== SERIALIZE END =====");
    }
}