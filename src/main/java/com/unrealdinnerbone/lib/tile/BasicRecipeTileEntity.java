package com.unrealdinnerbone.lib.tile;

import com.unrealdinnerbone.lib.api.ButtonState;
import com.unrealdinnerbone.lib.api.IHasButton;
import com.unrealdinnerbone.lib.api.TransferType;
import com.unrealdinnerbone.lib.inventory.ISimplyInventory;
import com.unrealdinnerbone.lib.inventory.UpgradeInventory;
import com.unrealdinnerbone.lib.item.upgrade.EnergyCostUpgradeItem;
import com.unrealdinnerbone.lib.item.upgrade.SpeedUpgradeItem;
import com.unrealdinnerbone.lib.storage.ButtonStateStorage;
import com.unrealdinnerbone.lib.storage.DirectionStorage;
import com.unrealdinnerbone.lib.storage.ProgressStorage;
import com.unrealdinnerbone.lib.storage.RecipeStorage;
import com.unrealdinnerbone.lib.util.LazyHashMap;
import com.unrealdinnerbone.lib.util.OptionalUtils;
import com.unrealdinnerbone.lib.util.TextUtils;
import com.unrealdinnerbone.lib.util.inventory.SimplyItemStackHandler;
import com.unrealdinnerbone.simplytech.config.ServerConfig;
import com.unrealdinnerbone.simplytech.machines.test.MachineModelData;
import com.unrealdinnerbone.simplytech.registries.SimplyMachines;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.client.model.data.IModelData;
import net.minecraftforge.client.model.data.ModelDataMap;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public abstract class BasicRecipeTileEntity<C extends BasicRecipeTileEntity<C, I, R>, I extends IInventory, R extends IRecipe<I>> extends TickableEnergyTileEntity<ServerConfig.MachineConfig> implements ISimplyInventory, IProgressHolder, IHasButton {

    public static final int CRAFTING_TIME = 100;
    protected final ProgressStorage progressStorage;
    protected final ButtonStateStorage transferStateStorage;
    protected final DirectionStorage directionStorage;
    protected final RecipeStorage<I, R> recipeStorage;
    protected final SimplyMachines.Machine<C> machine;
    protected final LazyHashMap<Direction, LazyOptional<SidedInvWrapper>> wrapperMap = new LazyHashMap<>(direction -> LazyOptional.of(() -> new SidedInvWrapper(this, direction)));
    protected final LazyOptional<SimplyItemStackHandler> stackHandler;
    protected final UpgradeInventory upgradeInventory;
    protected final ITextComponent containerName;

    public BasicRecipeTileEntity(SimplyMachines.Machine<C> machine, IRecipeType<R> recipeType, int size, ServerConfig.MachineConfig config) {
        super(machine.getTileEntityTypeRegistryObject(), config);
        this.machine = machine;
        registerNBTStorage(LazyOptional.of(() -> this));
        this.directionStorage = registerNBTStorage(() -> new DirectionStorage("transferType", this::updateModel));
        this.transferStateStorage = registerNBTStorage(() -> new ButtonStateStorage('f', ButtonState.OFF));
        this.progressStorage = registerNBTStorage(() -> new ProgressStorage(0));
        this.recipeStorage = registerNBTStorage(() -> new RecipeStorage<>(recipeType, this::getWorld));
        this.upgradeInventory = registerNBTStorage(() -> new UpgradeInventory(this::markDirty));
        this.stackHandler = LazyOptional.of(() -> new SimplyItemStackHandler(size, this::onSlotUpdated));
        this.containerName = TextUtils.createTranslateTextComponent(machine.getBlockRegistryObject());
    }

    @Override
    public void onServerTick() {
        if(getStackInSlot(0).isEmpty()) {
            reset();
        }
        if(transferStateStorage.getState() == ButtonState.ON) {
            for (Direction dir : Direction.values()) {
                TileEntity tileEntity = world.getTileEntity(pos.offset(dir));
                if (tileEntity != null) {
                    tileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir.getOpposite()).ifPresent(otherInventory -> {
                        TransferType transferType = getTransferTypeForDirection(dir);
                        this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, dir).ifPresent(ourItemHandler -> {
                            if (transferType.isInput()) {
                                for (int i = 0; i < otherInventory.getSlots(); i++) {
                                    ItemStack itemStack = otherInventory.extractItem(i, 1, true);
                                    if(!itemStack.isEmpty()) {
                                        if (ourItemHandler.insertItem(0, itemStack, true).isEmpty()) {
                                            if(ourItemHandler.insertItem(0, itemStack, false).isEmpty()) {
                                                otherInventory.extractItem(i, 1, false);
                                            }
                                            break;
                                        }
                                    }
                                }
                            } else if (transferType.isOutput()) {
                                for (int i = 0; i < otherInventory.getSlots(); i++) {
                                    int amount = Math.min(getStackInSlot(0).getCount(), getStackInSlot(0).getMaxStackSize());
                                    ItemStack outputStack = ourItemHandler.extractItem(0, amount, true);
                                    if(!outputStack.isEmpty()) {
                                        if(otherInventory.insertItem(i, outputStack, true).isEmpty()) {
                                            if(otherInventory.insertItem(i, outputStack, false).isEmpty()) {
                                                ourItemHandler.extractItem(0, amount, false);
                                            }
                                        }
                                    }
                                }
                            }
                        });

                    });
                }
            }
        }


        OptionalUtils.ifPresentOrElse(recipeStorage.getRecipe(), entitySimplyRecipe -> {
            double recipeCost = (getEnergyCost(entitySimplyRecipe) * (getUpgradeEnergyMultiplayer() / 100));
            if (getEnergyStorage().hasEnergy((int) recipeCost)) {
                if (getStackHandler().isItemValid(getSlotForType(TransferType.OUTPUT).get(), entitySimplyRecipe.getRecipeOutput())) {
                    if (progressStorage.getProgress() >= CRAFTING_TIME) {
                        if (craft(entitySimplyRecipe)) {
                            getEnergyStorage().removeEnergy((int) recipeCost);
                            updateActiveRecipe();
                            reset();
                        }
                    } else {
                        getProgressStorage().increaseProgress(getProgressIncrease());
                        markForUpdate();
                    }
                }
            }else {
                reset();
            }
        }, this::reset);
    }

    public void reset() {
        if(!getStackHandler().getStackInSlot(0).isEmpty()) {
            updateActiveRecipe();
        }
        if(getProgressStorage().getProgress() != 0) {
            getProgressStorage().setProgress(0);
            markForUpdate();
        }
    }

    public double getProgressIncrease() {
        ItemStack stack = getUpgradeInventory().getStackInSlot(0);
        if (!stack.isEmpty()) {
            if(stack.getItem() instanceof SpeedUpgradeItem) {
                return 1 + ((SpeedUpgradeItem) stack.getItem()).getInfo().getSpeed() / 100;
            }else {
                return 0;
            }
        }else {
            return 1;
        }
    }


    @Override
    public ITextComponent getDisplayName() {
        return containerName;
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return machine.getFactoryObject().create(id, playerInventory, (C) this);
    }

    public ProgressStorage getProgressStorage() {
        return progressStorage;
    }

    @Override
    public SimplyItemStackHandler getStackHandler() {
        return stackHandler.orElseThrow(() -> new IllegalStateException("Stack Handler not init"));
    }

    @Override
    public void onSlotUpdated(int i) {
        updateActiveRecipe();
    }

    public UpgradeInventory getUpgradeInventory() {
        return upgradeInventory;
    }

    public void updateActiveRecipe() {
        recipeStorage.setRecipeOptional(getActiveRecipe());
    }

    public abstract int getEnergyCost(R r);

    public double getUpgradeEnergyMultiplayer() {
        ItemStack upgradeItem = getUpgradeInventory().getStackInSlot(1);
        if(upgradeItem.isEmpty()) {
            return 1;
        }else {
            if(upgradeItem.getItem() instanceof EnergyCostUpgradeItem) {
                return ((EnergyCostUpgradeItem) upgradeItem.getItem()).getInfo().getEnergyCost();
            }else {
                return 1;
            }
        }
    }

    public abstract Optional<R> getActiveRecipe();
    
    @Nonnull
    @Override
    public <B> LazyOptional<B> getCapability(@Nonnull Capability<B> cap, @Nullable Direction side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? side == null || getTransferTypeForDirection(side) == TransferType.NONE ? LazyOptional.empty().cast() : wrapperMap.get(side).cast() : super.getCapability(cap, side);
    }

    public boolean matches(World worldIn, Ingredient input) {
        return input.test(getStackInSlot(0));
    }

    public boolean craft(R recipe) {
        return getStackHandler().moveAndChangeItemStack(0, 1, recipe.getRecipeOutput().getItem(), recipe.getRecipeOutput().getCount());
    }

    @Override
    public double getProgress() {
        return progressStorage.getProgress();
    }

    public void updateModel() {
        if(this.world != null) {
            if(this.world.isRemote()) {
                requestModelDataUpdate();
                world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), Constants.BlockFlags.RERENDER_MAIN_THREAD);
            }
        }
    }

    @Override
    public void setTransferTypeForDirection(Direction direction, TransferType transferType) {
        this.directionStorage.set(direction, transferType);
    }

    @Override
    public TransferType getTransferTypeForDirection(Direction direction) {
        TransferType transferType = directionStorage.get(direction);
        sendUpdateToClient();
        return transferType;
    }

    @Override
    public Optional<Integer> getSlotForType(TransferType transferType) {
        switch (transferType) {
            case INPUT:
                return Optional.of(0);
            case OUTPUT:
                return Optional.of(1);
            default:
                return Optional.empty();
        }
    }

    @Override
    public ButtonState getButtonState(char name) {
        if(name == 'f') {
            return transferStateStorage.getState();
        }
        return ButtonState.ON;
    }

    @Override
    public void setButtonState(char name, ButtonState state) {
        if(name == 'f') {
            transferStateStorage.setState(state);
            markForUpdate();
        }
    }

    public List<TransferType> getAllowedTransferTypes() {
        return TransferType.DEFAULT;
    }

    @Override
    @Nonnull
    public IModelData getModelData() {
        return new ModelDataMap.Builder().withInitial(MachineModelData.UPGRADE_PROPERTY, new MachineModelData(directionStorage.getTransferTypeHashMap())).build();
    }
}
