package org.aussiebox.bitsofbox.component;

import com.mojang.serialization.Codec;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.BOBConstants;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<Integer> DRAGONFLAME_CACTUS_FUSE =
            register("dragonflame_cactus_fuse", builder -> builder.codec(Codec.INT));

    public static final ComponentType<BOBConstants.FluidityMode> FLUIDITY_MODE =
            register("fluidity_mode", builder -> builder.codec(BOBConstants.FluidityMode.CODEC));

    public static final ComponentType<Integer> FLUIDITY_CHARGES =
            register("fluidity_charges", builder -> builder.codec(Codec.INT));

    public static final ComponentType<Integer> FLUIDITY_MAX_CHARGES =
            register("fluidity_max_charges", builder -> builder.codec(Codec.INT));


    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(BOB.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void init() {
        BOB.LOGGER.info("Registering Component Types for " + BOB.MOD_ID);
    }
}
