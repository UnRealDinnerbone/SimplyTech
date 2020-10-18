package com.unrealdinnerbone.lib.item.upgrade;

import com.unrealdinnerbone.lib.api.IUpgradeInfo;
import com.unrealdinnerbone.lib.api.UpgradeType;
import com.unrealdinnerbone.simplytech.SimplyTech;
import com.unrealdinnerbone.simplytech.items.UpgradeItem;

public class EnergyCostUpgradeItem extends UpgradeItem {

    private final IUpgradeInfo upgradeInfo;

    public EnergyCostUpgradeItem(UpgradeType type) {
        super(type);
        this.upgradeInfo = new IUpgradeInfo() {
            @Override
            public double getSpeed() {
                return 0;
            }

            @Override
            public double getEnergyCost() {
                return SimplyTech.getInstance().getConfig().getSpeedConfigs().get(type).getEnergyChange();
            }
        };
    }

    @Override
    public IUpgradeInfo getInfo() {
        return upgradeInfo;
    }
}
