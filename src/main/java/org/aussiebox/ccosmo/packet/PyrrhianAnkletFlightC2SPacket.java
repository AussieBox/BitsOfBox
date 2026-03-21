package org.aussiebox.ccosmo.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.aussiebox.ccosmo.CCOSMO;

public record PyrrhianAnkletFlightC2SPacket(boolean flightMode) implements CustomPayload {
    public static final Identifier PACKET_ID = CCOSMO.id("pyrrhian_anklet_flight");
    public static final CustomPayload.Id<PyrrhianAnkletFlightC2SPacket> ID = new CustomPayload.Id<>(PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, PyrrhianAnkletFlightC2SPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, PyrrhianAnkletFlightC2SPacket::flightMode,
            PyrrhianAnkletFlightC2SPacket::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
