package com.unrealdinnerbone.simplytech.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {

    private final ForgeConfigSpec.Builder builder;

    public CommonConfig(ForgeConfigSpec.Builder builder) {
        this.builder = builder;
    }

    public ForgeConfigSpec build() {
        return builder.build();
    }

}
