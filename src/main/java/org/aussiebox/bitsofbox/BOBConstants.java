package org.aussiebox.bitsofbox;

import com.mojang.serialization.Codec;
import net.minecraft.item.Item;
import net.minecraft.util.StringIdentifiable;
import org.aussiebox.bitsofbox.item.ModItems;

import java.util.HashMap;
import java.util.Map;

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

    Map<Item, Float> fluidityRiptideStrengths = new HashMap<>();
    static Map<Item, Float> fluidityRiptideStrengths() {
        fluidityRiptideStrengths.put(ModItems.WOODEN_FLUIDITY, 1.5F);
        fluidityRiptideStrengths.put(ModItems.STONE_FLUIDITY, 1.75F);
        fluidityRiptideStrengths.put(ModItems.COPPER_FLUIDITY, 2.0F);
        fluidityRiptideStrengths.put(ModItems.GOLD_FLUIDITY, 1.75F);
        fluidityRiptideStrengths.put(ModItems.IRON_FLUIDITY, 2.5F);
        fluidityRiptideStrengths.put(ModItems.DIAMOND_FLUIDITY, 3.0F);
        fluidityRiptideStrengths.put(ModItems.NETHERITE_FLUIDITY, 3.5F);
        return fluidityRiptideStrengths;
    }

    Map<Item, Integer> fluidityPickarangReturnTimes = new HashMap<>();
    static Map<Item, Integer> fluidityPickarangReturnTimes() {
        fluidityPickarangReturnTimes.put(ModItems.WOODEN_FLUIDITY, 10);
        fluidityPickarangReturnTimes.put(ModItems.STONE_FLUIDITY, 15);
        fluidityPickarangReturnTimes.put(ModItems.COPPER_FLUIDITY, 20);
        fluidityPickarangReturnTimes.put(ModItems.GOLD_FLUIDITY, 15);
        fluidityPickarangReturnTimes.put(ModItems.IRON_FLUIDITY, 25);
        fluidityPickarangReturnTimes.put(ModItems.DIAMOND_FLUIDITY, 30);
        fluidityPickarangReturnTimes.put(ModItems.NETHERITE_FLUIDITY, 40);
        return fluidityPickarangReturnTimes;
    }

    /// FLUIDITY ATTRIBUTES
    // Array Mode Order: {Trident, Axe, Pickaxe}

    Map<Item, Double[]> fluidityAttackDamages = new HashMap<>();
    static Map<Item, Double[]> fluidityAttackDamages() {
        fluidityAttackDamages.put(ModItems.WOODEN_FLUIDITY, new Double[]{5.0, 5.0, 0.0});
        fluidityAttackDamages.put(ModItems.STONE_FLUIDITY, new Double[]{5.5, 6.0, 1.0});
        fluidityAttackDamages.put(ModItems.COPPER_FLUIDITY, new Double[]{6.0, 6.5, 1.5});
        fluidityAttackDamages.put(ModItems.GOLD_FLUIDITY, new Double[]{5.5, 3.0, 0.0});
        fluidityAttackDamages.put(ModItems.IRON_FLUIDITY, new Double[]{7.0, 7.0, 2.0});
        fluidityAttackDamages.put(ModItems.DIAMOND_FLUIDITY, new Double[]{8.0, 7.0, 3.0});
        fluidityAttackDamages.put(ModItems.NETHERITE_FLUIDITY, new Double[]{9.0, 8.0, 4.0});
        return fluidityAttackDamages;
    }

    Map<Item, Double[]> fluidityAttackSpeeds = new HashMap<>();
    static Map<Item, Double[]> fluidityAttackSpeeds() {
        fluidityAttackSpeeds.put(ModItems.WOODEN_FLUIDITY, new Double[]{-2.9, -3.5, -2.8});
        fluidityAttackSpeeds.put(ModItems.STONE_FLUIDITY, new Double[]{-2.7, -3.5, -2.8});
        fluidityAttackSpeeds.put(ModItems.COPPER_FLUIDITY, new Double[]{-2.5, -3.3, -2.8});
        fluidityAttackSpeeds.put(ModItems.GOLD_FLUIDITY, new Double[]{-3.2, -3.0, -2.8});
        fluidityAttackSpeeds.put(ModItems.IRON_FLUIDITY, new Double[]{-2.5, -3.1, -2.8});
        fluidityAttackSpeeds.put(ModItems.DIAMOND_FLUIDITY, new Double[]{-2.3, -3.0, -2.8});
        fluidityAttackSpeeds.put(ModItems.NETHERITE_FLUIDITY, new Double[]{-2.1, -3.0, -2.8});
        return fluidityAttackSpeeds;
    }
}
