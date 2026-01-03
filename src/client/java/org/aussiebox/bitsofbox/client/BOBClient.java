package org.aussiebox.bitsofbox.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.block.ModBlocks;
import org.aussiebox.bitsofbox.client.entity.DragonflameCactusEntityModel;
import org.aussiebox.bitsofbox.client.entity.DragonflameCactusEntityRenderer;
import org.aussiebox.bitsofbox.entity.ModEntities;
import org.aussiebox.bitsofbox.item.ModItems;

public class BOBClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(DragonflameCactusEntityModel.CACTUS, DragonflameCactusEntityModel::getTexturedModelData);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRAGONFLAME_CACTUS_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRAGONFLAME_CACTUS_BLOCK, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.DragonflameCactusEntityType, DragonflameCactusEntityRenderer::new);

        registerModelPredicates();

    }

    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(
                ModItems.WOODEN_FLUIDITY,
                BOB.id("throwing"),
                (stack, world, entity, seed) -> {

                    if (entity == null) return 0.0F;
                    if (entity.getActiveItem() == stack) return 1.0F;

                    return 0.0F;
                }
        );
        ModelPredicateProviderRegistry.register(
                ModItems.STONE_FLUIDITY,
                BOB.id("throwing"),
                (stack, world, entity, seed) -> {

                    if (entity == null) return 0.0F;
                    if (entity.getActiveItem() == stack) return 1.0F;

                    return 0.0F;
                }
        );
        ModelPredicateProviderRegistry.register(
                ModItems.COPPER_FLUIDITY,
                BOB.id("throwing"),
                (stack, world, entity, seed) -> {

                    if (entity == null) return 0.0F;
                    if (entity.getActiveItem() == stack) return 1.0F;

                    return 0.0F;
                }
        );
        ModelPredicateProviderRegistry.register(
                ModItems.GOLD_FLUIDITY,
                BOB.id("throwing"),
                (stack, world, entity, seed) -> {

                    if (entity == null) return 0.0F;
                    if (entity.getActiveItem() == stack) return 1.0F;

                    return 0.0F;
                }
        );
        ModelPredicateProviderRegistry.register(
                ModItems.IRON_FLUIDITY,
                BOB.id("throwing"),
                (stack, world, entity, seed) -> {

                    if (entity == null) return 0.0F;
                    if (entity.getActiveItem() == stack) return 1.0F;

                    return 0.0F;
                }
        );
        ModelPredicateProviderRegistry.register(
                ModItems.DIAMOND_FLUIDITY,
                BOB.id("throwing"),
                (stack, world, entity, seed) -> {

                    if (entity == null) return 0.0F;
                    if (entity.getActiveItem() == stack) return 1.0F;

                    return 0.0F;
                }
        );
        ModelPredicateProviderRegistry.register(
                ModItems.NETHERITE_FLUIDITY,
                BOB.id("throwing"),
                (stack, world, entity, seed) -> {

                    if (entity == null) return 0.0F;
                    if (entity.getActiveItem() == stack) return 1.0F;

                    return 0.0F;
                }
        );
    }
}
