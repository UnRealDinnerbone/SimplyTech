package com.unrealdinnerbone.lib.tile;

import com.unrealdinnerbone.lib.INBTStorage;
import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.simplytech.SimplyTech;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.RegistryObject;
import org.codehaus.plexus.util.cli.shell.CmdShell;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class BaseTileEntity extends TileEntity {

    private final List<LazyOptional<? extends INBTStorage>> inbtSerializableList = new ArrayList<>();

    public BaseTileEntity(RegistryObject<? extends TileEntityType<?>> tRegistryObject) {
        super(tRegistryObject.get());
    }

    protected <E extends INBTStorage> LazyOptional<E> registerNBTStorage(LazyOptional<E> storage) {
        inbtSerializableList.add(storage);
        return storage;
    }
    protected <E extends INBTStorage> E registerNBTStorage(Supplier<E> storage) {
        E e = storage.get();
        inbtSerializableList.add(LazyOptional.of(() -> e));
        return e;
    }

    @Override
    public void read(BlockState state, CompoundNBT compound) {
        handleTag(compound.getCompound(SimplyReference.MOD_ID));
        super.read(state, compound);
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        compound.put(SimplyReference.MOD_ID, getTag());
        return super.write(compound);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.put(SimplyTech.MOD_ID, getTag());
        return new SUpdateTileEntityPacket(getPos(), 0, compoundNBT);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        handleTag(pkt.getNbtCompound().getCompound(SimplyTech.MOD_ID));

    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT compoundNBT = super.getUpdateTag();
        compoundNBT.put(SimplyTech.MOD_ID, getTag());
        return compoundNBT;
    }

    public void handleTag(CompoundNBT compoundNBT) {
        inbtSerializableList.forEach(storage -> storage.ifPresent(storage1 -> storage1.fromNBT(compoundNBT.getCompound(storage1.getName()))));
    }

    public CompoundNBT getTag() {
        CompoundNBT compoundNBT = new CompoundNBT();
        inbtSerializableList.forEach(storage -> storage.ifPresent(storage1 -> compoundNBT.put(storage1.getName(), storage1.toNBT())));
        return compoundNBT;
    }

    public Optional<TileEntity> getTileEntity(BlockPos blockPos) {
        return Optional.ofNullable(world.getTileEntity(blockPos));
    }

    public void sendUpdateToClient() {
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(), Constants.BlockFlags.BLOCK_UPDATE);
    }

    public void markForUpdate() {
        markDirty();
        sendUpdateToClient();
    }


    protected boolean isServer() {
        return !isClient();
    }

    private boolean isClient() {
        return world.isRemote();
    }
}
