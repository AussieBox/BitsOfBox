package org.aussiebox.bitsofbox.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.aussiebox.bitsofbox.attach.DragonflameCactusFuseAttachedData;
import org.aussiebox.bitsofbox.attach.ModAttachmentTypes;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
import org.aussiebox.bitsofbox.entity.DragonflameCactusEntity;
import org.aussiebox.bitsofbox.item.ModItems;

import java.text.DecimalFormat;
import java.util.List;

public class DragonflameCactusItem extends Item {
    public DragonflameCactusItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack itemStack = super.getDefaultStack();
        itemStack.set(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE, 20);
        return itemStack;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (!world.isClient()) {
            DragonflameCactusEntity dragonflameCactusEntity = new DragonflameCactusEntity(world, user);
            int fuse = itemStack.get(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE);
            dragonflameCactusEntity.setOwner(user);
            dragonflameCactusEntity.setAttached(ModAttachmentTypes.DRAGONFLAME_CACTUS_FUSE_ATTACH, new DragonflameCactusFuseAttachedData(fuse));
            dragonflameCactusEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0F);
            world.spawnEntity(dragonflameCactusEntity);
        }
        itemStack.decrementUnlessCreative(1, user);
        return TypedActionResult.success(itemStack);
    }

    @Override
    public void onCraftByPlayer(ItemStack stack, World world, PlayerEntity player) {
        super.onCraftByPlayer(stack, world, player);
        String craftType = "add";
        ItemStack cactus = this.getDefaultStack();
        for (int i = 1; i <= 9; i++) {
            if (player.currentScreenHandler.getSlot(i).getStack().isOf(ModItems.DRAGONFLAME_CACTUS)) {
                cactus = player.currentScreenHandler.getSlot(i).getStack();
            }
            if (player.currentScreenHandler.getSlot(i).getStack().isOf(Items.GUNPOWDER)) {
                craftType = "subtract";
            }
        }
        if (craftType.equals("add")) {
            stack.set(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE, cactus.get(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE) + 10);
        }
        if (craftType.equals("subtract")) {
            if (cactus.get(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE) - 2 < 1) {
                stack.set(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE, 1);
            } else {
                stack.set(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE, cactus.get(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE) - 2);
            }
        }
        player.getInventory().markDirty();
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> list, TooltipType type) {
        super.appendTooltip(stack, context, list, type);
        if (stack.contains(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE)) {
            int fuseTicks = stack.get(ModDataComponentTypes.DRAGONFLAME_CACTUS_FUSE);
            String fuseSeconds = new DecimalFormat("0.00").format((double) fuseTicks/20);
            list.add(1, Text.translatable("item.bitsofbox.dragonflame_cactus.tooltip.fuse.1").withColor(0xAAAAAA)
                    .append(Text.literal(String.valueOf(fuseTicks)).withColor(0xFFAAAAAA))
                    .append(Text.translatable("item.bitsofbox.dragonflame_cactus.tooltip.fuse.2").withColor(0xFFAAAAAA))
                    .append(Text.literal(fuseSeconds).withColor(0xFFAAAAAA))
                    .append(Text.translatable("item.bitsofbox.dragonflame_cactus.tooltip.fuse.3").withColor(0xFFAAAAAA)));
        }
    }
}
