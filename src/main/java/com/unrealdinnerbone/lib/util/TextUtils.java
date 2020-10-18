package com.unrealdinnerbone.lib.util;

import com.unrealdinnerbone.lib.api.SimplyReference;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.RegistryObject;

public class TextUtils {

    public static ITextComponent createTranslateTextComponent(RegistryObject<?> registryObject) {
        return new TranslationTextComponent(SimplyReference.MOD_ID + "." + registryObject.getId().toString().replace(":", "."));
    }
}
