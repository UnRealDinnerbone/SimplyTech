package com.unrealdinnerbone.lib.util;

import java.util.Optional;
import java.util.function.Consumer;

public class OptionalUtils {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static <T> void ifPresentOrElse(Optional<T> optional, Consumer<T> onPresent, Runnable onNotThere) {
        if(optional.isPresent()) {
            onPresent.accept(optional.get());
        }else {
            onNotThere.run();
        }
    }
}
