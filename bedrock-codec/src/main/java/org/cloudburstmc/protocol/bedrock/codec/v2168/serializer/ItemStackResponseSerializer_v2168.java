package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v419.serializer.ItemStackResponseSerializer_v419;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ItemStackResponseSerializer_v2168 extends ItemStackResponseSerializer_v419 {

    public static final ItemStackResponseSerializer_v2168 INSTANCE =
            new ItemStackResponseSerializer_v2168();

    private static final Logger log =
            LoggerFactory.getLogger(ItemStackResponseSerializer_v2168.class);

    @Override
    public void serialize(
            ByteBuf buffer,
            BedrockCodecHelper helper,
            ItemStackResponsePacket packet
    ) {
        final int startIndex = buffer.writerIndex();

        log.warn("[DEBUG-STACK-RESPONSE] ===== SERIALIZE START =====");
        log.warn("[DEBUG-STACK-RESPONSE] helper={}", helper.getClass().getName());
        log.warn("[DEBUG-STACK-RESPONSE] entries={}", packet.getEntries().size());

        for (int i = 0; i < packet.getEntries().size(); i++) {
            log.warn(
                    "[DEBUG-STACK-RESPONSE] entry[{}]={}",
                    i,
                    packet.getEntries().get(i)
            );
        }

        try {
            super.serialize(buffer, helper, packet);
        } catch (RuntimeException | Error throwable) {
            final int written = buffer.writerIndex() - startIndex;

            log.error(
                    "[DEBUG-STACK-RESPONSE] SERIALIZE EXCEPTION after {} bytes",
                    written,
                    throwable
            );

            if (written > 0) {
                log.error(
                        "[DEBUG-STACK-RESPONSE] partialHex={}",
                        ByteBufUtil.hexDump(buffer, startIndex, written)
                );
            }

            throw throwable;
        }

        final int written = buffer.writerIndex() - startIndex;

        log.warn("[DEBUG-STACK-RESPONSE] bytesWritten={}", written);
        log.warn(
                "[DEBUG-STACK-RESPONSE] hex={}",
                ByteBufUtil.hexDump(buffer, startIndex, written)
        );
        log.warn("[DEBUG-STACK-RESPONSE] ===== SERIALIZE END =====");
    }
}