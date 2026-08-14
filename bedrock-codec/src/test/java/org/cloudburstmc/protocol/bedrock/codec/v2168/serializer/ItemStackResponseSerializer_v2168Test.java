package org.cloudburstmc.protocol.bedrock.codec.v2168.serializer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.Unpooled;
import org.cloudburstmc.protocol.bedrock.codec.v2168.Bedrock_v2168;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponse;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseSlot;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseStatus;
import org.cloudburstmc.protocol.bedrock.packet.ItemStackResponsePacket;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemStackResponseSerializer_v2168Test {

    @Test
    void dropResponseUsesV2168PresenceFraming() {
        var helper = Bedrock_v2168.CODEC.createHelper();
        var containerName = new FullContainerName(ContainerSlotType.INVENTORY, null);
        var slot = new ItemStackResponseSlot(25, 25, 0, 0, "", 0, "");
        var container = new ItemStackResponseContainer(
                ContainerSlotType.INVENTORY,
                List.of(slot),
                containerName
        );
        var packet = new ItemStackResponsePacket();
        packet.getEntries().add(new ItemStackResponse(
                ItemStackResponseStatus.OK,
                -3,
                List.of(container)
        ));
        ByteBuf buffer = Unpooled.buffer();

        ItemStackResponseSerializer_v2168.INSTANCE.serialize(buffer, helper, packet);

        // This is the response shape emitted for the DROP request captured in
        // server.log. Keep the fork-specific v2168 presence framing stable.
        assertEquals(
                "0100050101011d00011919000100000000",
                ByteBufUtil.hexDump(buffer)
        );

        var decoded = new ItemStackResponsePacket();
        ItemStackResponseSerializer_v2168.INSTANCE.deserialize(buffer, helper, decoded);

        assertEquals(packet.getEntries(), decoded.getEntries());
        assertEquals(0, buffer.readableBytes());
    }
}
