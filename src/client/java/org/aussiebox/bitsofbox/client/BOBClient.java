package org.aussiebox.bitsofbox.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;
import org.aussiebox.bitsofbox.block.ModBlocks;
import org.aussiebox.bitsofbox.client.entity.DragonflameCactusEntityModel;
import org.aussiebox.bitsofbox.client.entity.DragonflameCactusEntityRenderer;
import org.aussiebox.bitsofbox.entity.ModEntities;

public class BOBClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(DragonflameCactusEntityModel.CACTUS, DragonflameCactusEntityModel::getTexturedModelData);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRAGONFLAME_CACTUS_PLANT, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DRAGONFLAME_CACTUS_BLOCK, RenderLayer.getCutout());

        EntityRendererRegistry.register(ModEntities.DragonflameCactusEntityType, DragonflameCactusEntityRenderer::new);

//        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) -> {
//            if (itemStack.isOf(ModItems.DRAGONFLAME_CACTUS)) {
//                if (itemStack.contains(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE)) {
//                    int fuseTicks = itemStack.get(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE);
//                    String fuseSeconds = new DecimalFormat("0.00").format((double) fuseTicks/20);
//                    list.add(1, Text.translatable("item.wingcrafter.dragonflame_cactus.tooltip.fuse.1").withColor(0xAAAAAA)
//                            .append(Text.literal(String.valueOf(fuseTicks)).withColor(0xFFAAAAAA))
//                            .append(Text.translatable("item.wingcrafter.dragonflame_cactus.tooltip.fuse.2").withColor(0xFFAAAAAA))
//                            .append(Text.literal(fuseSeconds).withColor(0xFFAAAAAA))
//                            .append(Text.translatable("item.wingcrafter.dragonflame_cactus.tooltip.fuse.3").withColor(0xFFAAAAAA)));
//                }
//            }
//        });

    }
}
