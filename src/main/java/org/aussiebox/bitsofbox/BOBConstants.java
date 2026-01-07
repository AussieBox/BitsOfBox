package org.aussiebox.bitsofbox;

import com.mojang.serialization.Codec;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.StringIdentifiable;
import org.aussiebox.bitsofbox.item.ModItems;

import java.util.HashMap;
import java.util.Map;

public interface BOBConstants {

    RegistryKey<DamageType> PICKARANG_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, BOB.id("pickarang"));
    RegistryKey<DamageType> FLUIDITY_TRIDENT_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, BOB.id("fluidity_trident"));

    RegistryKey<Enchantment> BORDERLINKED_ENCHANT = RegistryKey.of(RegistryKeys.ENCHANTMENT, BOB.id("borderlinked"));
    RegistryKey<Enchantment> SHIMMERSEEP_ENCHANT = RegistryKey.of(RegistryKeys.ENCHANTMENT, BOB.id("shimmerseep"));

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

    Map<Item, Integer> fluidityBlockChangeMaximums = new HashMap<>();
    static Map<Item, Integer> fluidityBlockChangeMaximums() {
        fluidityBlockChangeMaximums.put(ModItems.WOODEN_FLUIDITY, 6);
        fluidityBlockChangeMaximums.put(ModItems.STONE_FLUIDITY, 7);
        fluidityBlockChangeMaximums.put(ModItems.COPPER_FLUIDITY, 9);
        fluidityBlockChangeMaximums.put(ModItems.GOLD_FLUIDITY, 8);
        fluidityBlockChangeMaximums.put(ModItems.IRON_FLUIDITY, 10);
        fluidityBlockChangeMaximums.put(ModItems.DIAMOND_FLUIDITY, 11);
        fluidityBlockChangeMaximums.put(ModItems.NETHERITE_FLUIDITY, 12);
        return fluidityBlockChangeMaximums;
    }

    // Array Order: {Effect Range, Blocks Affected}

    Map<Item, Double[]> fluidityLandEffectData = new HashMap<>();
    static Map<Item, Double[]> fluidityLandEffectData() {
        fluidityLandEffectData.put(ModItems.WOODEN_FLUIDITY, new Double[]{1.0, 2.0});
        fluidityLandEffectData.put(ModItems.STONE_FLUIDITY, new Double[]{1.5, 5.0});
        fluidityLandEffectData.put(ModItems.COPPER_FLUIDITY, new Double[]{2.0, 8.0});
        fluidityLandEffectData.put(ModItems.GOLD_FLUIDITY, new Double[]{1.5, 6.0});
        fluidityLandEffectData.put(ModItems.IRON_FLUIDITY, new Double[]{3.0, 20.0});
        fluidityLandEffectData.put(ModItems.DIAMOND_FLUIDITY, new Double[]{4.0, 35.0});
        fluidityLandEffectData.put(ModItems.NETHERITE_FLUIDITY, new Double[]{5.0, 50.0});
        return fluidityLandEffectData;
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
        fluidityAttackDamages.put(ModItems.WOODEN_FLUIDITY, new Double[]{3.0, 5.0, 0.0});
        fluidityAttackDamages.put(ModItems.STONE_FLUIDITY, new Double[]{3.5, 6.0, 1.0});
        fluidityAttackDamages.put(ModItems.COPPER_FLUIDITY, new Double[]{4.0, 6.5, 1.5});
        fluidityAttackDamages.put(ModItems.GOLD_FLUIDITY, new Double[]{3.0, 3.0, 0.0});
        fluidityAttackDamages.put(ModItems.IRON_FLUIDITY, new Double[]{5.0, 7.0, 2.0});
        fluidityAttackDamages.put(ModItems.DIAMOND_FLUIDITY, new Double[]{6.0, 7.0, 3.0});
        fluidityAttackDamages.put(ModItems.NETHERITE_FLUIDITY, new Double[]{7.0, 8.0, 4.0});
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
