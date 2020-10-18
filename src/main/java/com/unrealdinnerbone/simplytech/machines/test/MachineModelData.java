package com.unrealdinnerbone.simplytech.machines.test;

import com.unrealdinnerbone.lib.api.TransferType;
import com.unrealdinnerbone.lib.api.UpgradeType;
import net.minecraft.util.Direction;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.Map;

public class MachineModelData
{
    public static final ModelProperty<MachineModelData> UPGRADE_PROPERTY = new ModelProperty<>();

    private Map<Direction, TransferType> transferTypes;

    public MachineModelData(Map<Direction, TransferType> transferTypes) {
        this.transferTypes = transferTypes;
    }

    public Map<Direction, TransferType> getTransferTypes()
    {
        return transferTypes;
    }
}
