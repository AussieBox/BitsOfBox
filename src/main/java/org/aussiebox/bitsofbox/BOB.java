package org.aussiebox.bitsofbox;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import org.aussiebox.bitsofbox.attach.ModAttachmentTypes;
import org.aussiebox.bitsofbox.block.ModBlocks;
import org.aussiebox.bitsofbox.blockentity.ModBlockEntities;
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

    public static final TreeDecoratorType<AttachedToLogsTreeDecorator> ATTACHED_TO_LOGS_TREE_DECORATOR = TreeDecoratorTypeInvoker.bitsofbox$callRegister("bitsofbox:attached_to_logs", AttachedToLogsTreeDecorator.CODEC);

    @Override
    public void onInitialize() {

        BOB.LOGGER.info("OH GOD LOOK OUT THERE'S LOGGER SPAM COMING UP AAA");
        BOB.LOGGER.info("-------------------------------------------------");
        ModBlocks.init();
        ModItems.init();
        ModEntities.init();
        ModAttachmentTypes.init();
        ModDataComponentTypes.init();
        ModBlockEntities.registerModBlockEntities();
        BOB.LOGGER.info("-------------------------------------------------");
        BOB.LOGGER.info("phew hopefully that wasn't too bad lol");
        BOB.LOGGER.info("i mean at least you know the mod should actually work now");

        DragonflameCactusGeneration.generateCacti();

    }
}
