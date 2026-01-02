package org.aussiebox.bitsofbox;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringIdentifiable;

public interface BOBConstants {

    enum FluidityMode implements StringIdentifiable {
        AXE("axe"),
        PICKAXE("pickaxe"),
        TRIDENT("trident");

        private final String key;
        public static final Codec<FluidityMode> CODEC = StringIdentifiable.createCodec(FluidityMode::values);

        FluidityMode(String key) {
            this.key = key;
        }

        @Override
        public String asString() {
            return this.key;
        }
    }

}
