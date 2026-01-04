package org.aussiebox.bitsofbox;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import org.aussiebox.bitsofbox.attach.ModAttachmentTypes;
import org.aussiebox.bitsofbox.block.ModBlocks;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
import org.aussiebox.bitsofbox.entity.ModEntities;
import org.aussiebox.bitsofbox.item.ModItems;
import org.aussiebox.bitsofbox.mixin.TreeDecoratorTypeInvoker;
import org.aussiebox.bitsofbox.world.DragonflameCactusGeneration;
import org.aussiebox.bitsofbox.world.gen.AttachedToLogsTreeDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BOB implements ModInitializer {

    public static final String MOD_ID = "bitsofbox";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static final TreeDecoratorType<AttachedToLogsTreeDecorator> ATTACHED_TO_LOGS_TREE_DECORATOR = TreeDecoratorTypeInvoker.callRegister("bitsofbox:attached_to_logs", AttachedToLogsTreeDecorator.CODEC);

    @Override
    public void onInitialize() {

        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModAttachmentTypes.init();
        ModDataComponentTypes.init();

        DragonflameCactusGeneration.generateCacti();

        UseItemCallback.EVENT.register((player, world, hand) -> {

            if (!(world instanceof ServerWorld serverWorld)) return TypedActionResult.pass(player.getMainHandStack());

            HitResult raycast = player.raycast(player.getBlockInteractionRange(), 0, false);
            if (!serverWorld.getWorldBorder().contains(raycast.getPos())) {

                ItemStack stack = new ItemStack(ModItems.SHIMMER_POWDER);
                player.giveItemStack(stack);

                return TypedActionResult.success(player.getMainHandStack());
            }

            return TypedActionResult.pass(player.getMainHandStack());
        });

    }
}
