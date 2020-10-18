package com.unrealdinnerbone.simplytech.packets;

import com.unrealdinnerbone.lib.util.ILoading;
import com.unrealdinnerbone.simplytech.SimplyTech;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class SimplyPackets implements ILoading
{
    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(SimplyTech.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    static {
        INSTANCE.registerMessage(0, ToggleButtonPacket.class, ToggleButtonPacket::toBytes, ToggleButtonPacket::fromBytes, ToggleButtonPacket::handle);
    }
}
