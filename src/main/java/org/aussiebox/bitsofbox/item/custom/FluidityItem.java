package org.aussiebox.bitsofbox.item.custom;

import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.BOBConstants;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
import org.aussiebox.bitsofbox.entity.ModEntities;
import org.aussiebox.bitsofbox.entity.PickarangEntity;
import org.aussiebox.bitsofbox.item.ModItems;
import org.aussiebox.bitsofbox.item.ModToolMaterials;

import java.util.Arrays;
import java.util.List;

public class FluidityItem extends MiningToolItem {

    public FluidityItem(Item.Settings settings) {
        super(ToolMaterials.WOOD, BlockTags.AIR, settings);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        if (miner.getMainHandStack().get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT && !miner.isInCreativeMode()) return false;
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT) {
            if (!user.isSneaking()) {
                if (stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0) <= 0) return TypedActionResult.fail(stack);
                user.setCurrentHand(hand);
                return TypedActionResult.consume(stack);
            }
        }

        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.PICKAXE) {
            if (!user.isSneaking()) {
                if (stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0) <= 0) return TypedActionResult.fail(stack);
                stack.set(ModDataComponentTypes.FLUIDITY_CHARGES, stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 1) - 1);

                PickarangEntity entity = new PickarangEntity(ModEntities.PickarangEntityType, user, world);
                entity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 0.0F);
                entity.setItem(stack);

                world.spawnEntity(entity);
                stack.decrement(1);

                return TypedActionResult.success(stack);
            }
        }

        if (user.isSneaking()) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT) stack.set(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.AXE);
            else if (mode == BOBConstants.FluidityMode.AXE) stack.set(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.PICKAXE);
            else if (mode == BOBConstants.FluidityMode.PICKAXE) stack.set(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.TRIDENT);

            int index = 0;
            if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT) index = 0;
            if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.AXE) index = 1;
            if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.PICKAXE) index = 2;

            double attackDamage = Arrays.stream(BOBConstants.fluidityAttackDamages().get(stack.getItem())).toList().get(index);
            double attackSpeed = Arrays.stream(BOBConstants.fluidityAttackSpeeds().get(stack.getItem())).toList().get(index);

            stack.remove(DataComponentTypes.ATTRIBUTE_MODIFIERS);
            stack.set(DataComponentTypes.ATTRIBUTE_MODIFIERS, AttributeModifiersComponent.builder()
                    .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(
                            Item.BASE_ATTACK_DAMAGE_MODIFIER_ID,
                            attackDamage,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ),
                            AttributeModifierSlot.MAINHAND)
                    .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(
                            Item.BASE_ATTACK_SPEED_MODIFIER_ID,
                            attackSpeed,
                            EntityAttributeModifier.Operation.ADD_VALUE
                    ),
                            AttributeModifierSlot.MAINHAND)
                    .build()
            );
            stack.remove(DataComponentTypes.TOOL);
            if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.AXE) stack.set(DataComponentTypes.TOOL, getMaterial(stack).createComponent(BlockTags.AXE_MINEABLE));
            if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.PICKAXE) stack.set(DataComponentTypes.TOOL, getMaterial(stack).createComponent(BlockTags.PICKAXE_MINEABLE));

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }

    public ToolMaterial getMaterial(ItemStack stack) {
        if (stack.isOf(ModItems.WOODEN_FLUIDITY)) return ToolMaterials.WOOD;
        if (stack.isOf(ModItems.STONE_FLUIDITY)) return ToolMaterials.STONE;
        if (stack.isOf(ModItems.COPPER_FLUIDITY)) return ModToolMaterials.COPPER;
        if (stack.isOf(ModItems.GOLD_FLUIDITY)) return ToolMaterials.GOLD;
        if (stack.isOf(ModItems.IRON_FLUIDITY)) return ToolMaterials.IRON;
        if (stack.isOf(ModItems.DIAMOND_FLUIDITY)) return ToolMaterials.DIAMOND;
        if (stack.isOf(ModItems.NETHERITE_FLUIDITY)) return ToolMaterials.NETHERITE;

        return null;
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT)
            return true;

        return false;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT)
            if (stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0) <= 0) return UseAction.NONE;
            else return UseAction.SPEAR;

        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT)
            if (stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0) <= 0) return 0;
            else return 72000;

        return 0;
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        super.onStoppedUsing(stack, world, user, remainingUseTicks);

        if (!(user instanceof PlayerEntity playerEntity)) return;
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) != BOBConstants.FluidityMode.TRIDENT) return;

        int i = this.getMaxUseTime(stack, user) - remainingUseTicks;
        if (i < 10) return;

        playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        float strength = BOBConstants.fluidityRiptideStrengths().get(stack.getItem()); // Riptide Strength

        stack.set(ModDataComponentTypes.FLUIDITY_CHARGES, stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 1) - 1);

        float yaw = playerEntity.getYaw();
        float pitch = playerEntity.getPitch();
        float j = -MathHelper.sin(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        float k = -MathHelper.sin(pitch * ((float)Math.PI / 180F));
        float l = MathHelper.cos(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        float m = MathHelper.sqrt(j * j + k * k + l * l);
        j *= strength / m;
        k *= strength / m;
        l *= strength / m;
        playerEntity.addVelocity(j, k, l);

        int index = 0;
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT) index = 0;
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.AXE) index = 1;
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.PICKAXE) index = 2;
        double attackDamage = Arrays.stream(BOBConstants.fluidityAttackDamages().get(stack.getItem())).toList().get(index);

        playerEntity.useRiptide(20, (float) attackDamage, stack);
        if (playerEntity.isOnGround()) {
            float n = 1.1999999F;
            playerEntity.move(MovementType.SELF, new Vec3d(0.0F, 1.1999999F, 0.0F));
        }

        world.playSoundFromEntity(null, playerEntity, SoundEvents.ITEM_TRIDENT_RIPTIDE_3.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0) < stack.getOrDefault(ModDataComponentTypes.FLUIDITY_MAX_CHARGES, 5) && otherStack.isOf(ModItems.SHIMMER_POWDER) && clickType == ClickType.RIGHT) {

            stack.set(ModDataComponentTypes.FLUIDITY_CHARGES, stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0) + 1);
            otherStack.decrement(1);

            slot.markDirty();
            return true;
        }
        return false;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> list, TooltipType type) {
        super.appendTooltip(stack, context, list, type);

        int maxCharges = stack.getOrDefault(ModDataComponentTypes.FLUIDITY_MAX_CHARGES, 5);
        int charges = stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0);
        int emptySlots = maxCharges - charges;
        int fullSlots = maxCharges - emptySlots;
        int slotsRendered = 0;

        Text bar = Text.empty();

        if (fullSlots > 0) {
            for (int i = 0; i < fullSlots; i++) {
                if (slotsRendered == 0) bar = Text.literal("S-");
                else if (slotsRendered == maxCharges-1) bar = bar.copy().append("E");
                else bar = bar.copy().append("L-");
                slotsRendered++;
            }
        }
        if (emptySlots >= 0) {
            for (int i = 0; i < emptySlots; i++) {
                if (slotsRendered == 0) bar = Text.literal("s-");
                else if (slotsRendered == maxCharges-1) bar = bar.copy().append("e");
                else bar = bar.copy().append("l-");
                slotsRendered++;
            }
        }

        bar = bar.copy().setStyle(Style.EMPTY.withFont(BOB.id("fluidity_bar")));

        list.add(1, bar);

        if (charges == 0) list.add(1, Text.translatable("tip.bitsofbox.fluidity_refill").withColor(0xA024FF));
    }
}
