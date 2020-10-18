package com.unrealdinnerbone.simplytech.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ClientConfig {

    private final ForgeConfigSpec.Builder builder;

    public ClientConfig(ForgeConfigSpec.Builder builder) {
        this.builder = builder;
    }

    public ForgeConfigSpec build() {
        return builder.build();
    }

}
