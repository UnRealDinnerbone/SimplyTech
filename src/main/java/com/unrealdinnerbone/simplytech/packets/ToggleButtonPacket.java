package com.unrealdinnerbone.simplytech.packets;

import com.unrealdinnerbone.lib.api.ButtonState;
import com.unrealdinnerbone.lib.api.IHasButton;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleButtonPacket {

    private final BlockPos pos;
    private final ButtonState state;
    private final char name;

    public ToggleButtonPacket(BlockPos pos, ButtonState state, char name) {
        this.pos = pos;
        this.state = state;
        this.name = name;
    }

    public static ToggleButtonPacket fromBytes(PacketBuffer buffer) {
        return new ToggleButtonPacket(buffer.readBlockPos(), ButtonState.getFromByte(buffer.readByte()), buffer.readChar());
    }

    public void toBytes(PacketBuffer buffer) {
        buffer.writeBlockPos(pos);
        buffer.writeByte(state.getId());
        buffer.writeChar(name);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> {
            ServerWorld serverWorld = context.getSender().getServerWorld();
            TileEntity tileEntity = serverWorld.getTileEntity(pos);
            if(tileEntity instanceof IHasButton) {
                IHasButton button = (IHasButton) tileEntity;
                button.setButtonState(name, state);
            }
            context.setPacketHandled(true);
        });
    }

    public static void sendButtonChangeToServer(BlockPos pos, ButtonState state, char name) {
        SimplyPackets.INSTANCE.sendToServer(new ToggleButtonPacket(pos, state, name));
    }
}
