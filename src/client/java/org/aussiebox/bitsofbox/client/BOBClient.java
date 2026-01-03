package org.aussiebox.bitsofbox.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.render.RenderLayer;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.BOBConstants;
import org.aussiebox.bitsofbox.block.ModBlocks;
import org.aussiebox.bitsofbox.client.entity.DragonflameCactusEntityModel;
import org.aussiebox.bitsofbox.client.entity.DragonflameCactusEntityRenderer;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
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
                BOB.id("fluidity_mode"),
                (stack, world, entity, seed) -> {
                    BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);
                    if (mode == BOBConstants.FluidityMode.AXE) return 1.0F;
                    if (mode == BOBConstants.FluidityMode.PICKAXE) return 2.0F;
                    if (mode == BOBConstants.FluidityMode.TRIDENT) return 3.0F;
                    return 0.0F;
                }
        );
    }
}
