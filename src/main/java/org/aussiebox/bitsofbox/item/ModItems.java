package org.aussiebox.bitsofbox.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.BOBConstants;
import org.aussiebox.bitsofbox.block.ModBlocks;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
import org.aussiebox.bitsofbox.item.custom.DragonflameCactusItem;
import org.aussiebox.bitsofbox.item.custom.FluidityItem;

import java.util.function.Function;

public class ModItems {

    public static final Item DRAGONFLAME_CACTUS = registerItem(
            "dragonflame_cactus",
            DragonflameCactusItem::new,
            new Item.Settings()
    );

    public static final Item WOODEN_FLUIDITY = registerItem(
            "wooden_fluidity",
            FluidityItem::new,
            new Item.Settings()
                    .component(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.TRIDENT)
                    .rarity(Rarity.EPIC)
    );

    public static final Item STONE_FLUIDITY = registerItem(
            "stone_fluidity",
            FluidityItem::new,
            new Item.Settings()
                    .component(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.TRIDENT)
                    .rarity(Rarity.EPIC)
    );

    public static final Item COPPER_FLUIDITY = registerItem(
            "copper_fluidity",
            FluidityItem::new,
            new Item.Settings()
                    .component(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.TRIDENT)
                    .rarity(Rarity.EPIC)
    );

    public static final Item GOLD_FLUIDITY = registerItem(
            "gold_fluidity",
            FluidityItem::new,
            new Item.Settings()
                    .component(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.TRIDENT)
                    .rarity(Rarity.EPIC)
    );

    public static final Item IRON_FLUIDITY = registerItem(
            "iron_fluidity",
            FluidityItem::new,
            new Item.Settings()
                    .component(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.TRIDENT)
                    .rarity(Rarity.EPIC)
    );

    public static final Item DIAMOND_FLUIDITY = registerItem(
            "diamond_fluidity",
            FluidityItem::new,
            new Item.Settings()
                    .component(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.TRIDENT)
                    .rarity(Rarity.EPIC)
    );

    public static final Item NETHERITE_FLUIDITY = registerItem(
            "netherite_fluidity",
            FluidityItem::new,
            new Item.Settings()
                    .component(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.TRIDENT)
                    .rarity(Rarity.EPIC)
    );

    public static final RegistryKey<ItemGroup> BOB_ITEMGROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(BOB.MOD_ID, "bitsofbox"));
    public static final ItemGroup BOB_ITEMGROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.DRAGONFLAME_CACTUS))
            .displayName(Text.translatable("itemGroup.bitsofbox.bitsofbox"))
            .build();

    public static Item registerItem(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(BOB.MOD_ID, name));
        Item item = itemFactory.apply(settings);
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static BlockItem registerBlockItem(String name, Function<Item.Settings, BlockItem> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(BOB.MOD_ID, name));
        BlockItem item = itemFactory.apply(settings);
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void init() {
        BOB.LOGGER.info("Registering mod items for " + BOB.MOD_ID);

        Registry.register(Registries.ITEM_GROUP, BOB_ITEMGROUP_KEY, BOB_ITEMGROUP);
        ItemGroupEvents.modifyEntriesEvent(BOB_ITEMGROUP_KEY).register(itemGroup -> {
            itemGroup.add(DRAGONFLAME_CACTUS.getDefaultStack());
            itemGroup.add(ModBlocks.DRAGONFLAME_CACTUS_BLOCK.asItem());
            itemGroup.add(WOODEN_FLUIDITY);
            itemGroup.add(STONE_FLUIDITY);
            itemGroup.add(COPPER_FLUIDITY);
            itemGroup.add(GOLD_FLUIDITY);
            itemGroup.add(IRON_FLUIDITY);
            itemGroup.add(DIAMOND_FLUIDITY);
            itemGroup.add(NETHERITE_FLUIDITY);
        });
    }

}
