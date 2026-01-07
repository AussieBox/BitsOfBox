package org.aussiebox.bitsofbox.item.custom;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableMap;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.*;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.BOBConstants;
import org.aussiebox.bitsofbox.cca.ShimmerPowderData;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
import org.aussiebox.bitsofbox.entity.FluidityTridentEntity;
import org.aussiebox.bitsofbox.entity.ModEntities;
import org.aussiebox.bitsofbox.entity.PickarangEntity;
import org.aussiebox.bitsofbox.item.ModItems;
import org.aussiebox.bitsofbox.item.ModToolMaterials;
import org.aussiebox.bitsofbox.util.BOBUtil;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FluidityItem extends MiningToolItem {
    protected static final Map<Block, Block> STRIPPED_BLOCKS;

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
                user.incrementStat(Stats.USED.getOrCreateStat(this));
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

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack stack = context.getStack();
        PlayerEntity user = context.getPlayer();
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.AXE) {
            if (user != null && !user.isSneaking()) {
                World world = context.getWorld();
                BlockPos blockPos = context.getBlockPos();
                if (shouldCancelStripAttempt(context)) {
                    return ActionResult.PASS;
                } else {
                    Optional<BlockState> optional = this.tryStrip(world, blockPos, user, world.getBlockState(blockPos));
                    if (optional.isEmpty()) {
                        return ActionResult.PASS;
                    } else {
                        ItemStack itemStack = context.getStack();
                        if (user instanceof ServerPlayerEntity) {
                            Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity)user, blockPos, itemStack);
                        }

                        world.setBlockState(blockPos, optional.get(), 11);
                        world.emitGameEvent(GameEvent.BLOCK_CHANGE, blockPos, GameEvent.Emitter.of(user, optional.get()));

                        boolean hasBorderlinked = BOBUtil.stackHasEnchantment(world, stack, BOBConstants.BORDERLINKED_ENCHANT);
                        if (ShimmerPowderData.KEY.get(user).obtainmentsToday < 3 || hasBorderlinked) {
                            if (world.getRandom().nextBetween(1, hasBorderlinked ? 100 : 32) == 1) {
                                Vec3d pos = BOBUtil.shiftVecTowardsVec(context.getHitPos(), user.getEyePos(), 0.3);
                                ItemEntity itemEntity = new ItemEntity(world, pos.x, pos.y, pos.z, new ItemStack(ModItems.SHIMMER_POWDER));

                                if (!hasBorderlinked) ShimmerPowderData.KEY.get(user).obtainPowder();
                                world.spawnEntity(itemEntity);
                            }
                        }

                        return ActionResult.success(world.isClient);
                    }
                }
            }
        }
        return ActionResult.PASS;
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

    private static boolean shouldCancelStripAttempt(ItemUsageContext context) {
        PlayerEntity playerEntity = context.getPlayer();
        if (playerEntity == null) return false;
        return context.getHand().equals(Hand.MAIN_HAND) && playerEntity.getOffHandStack().isOf(Items.SHIELD) && !playerEntity.shouldCancelInteraction();
    }

    private Optional<BlockState> tryStrip(World world, BlockPos pos, @Nullable PlayerEntity player, BlockState state) {
        Optional<BlockState> optional = this.getStrippedState(state);
        if (optional.isPresent()) {
            world.playSound(player, pos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return optional;
        } else {
            Optional<BlockState> optional2 = Oxidizable.getDecreasedOxidationState(state);
            if (optional2.isPresent()) {
                world.playSound(player, pos, SoundEvents.ITEM_AXE_SCRAPE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                world.syncWorldEvent(player, 3005, pos, 0);
                return optional2;
            } else {
                Optional<BlockState> optional3 = Optional.ofNullable((Block)((BiMap<?, ?>)HoneycombItem.WAXED_TO_UNWAXED_BLOCKS.get()).get(state.getBlock())).map((block) -> block.getStateWithProperties(state));
                if (optional3.isPresent()) {
                    world.playSound(player, pos, SoundEvents.ITEM_AXE_WAX_OFF, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    world.syncWorldEvent(player, 3004, pos, 0);
                    return optional3;
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    private Optional<BlockState> getStrippedState(BlockState state) {
        return Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock())).map((block) -> (BlockState)block.getDefaultState().with(PillarBlock.AXIS, (Direction.Axis)state.get(PillarBlock.AXIS)));
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT;
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
        stack.set(ModDataComponentTypes.FLUIDITY_CHARGES, stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 1) - 1);

        FluidityTridentEntity entity = new FluidityTridentEntity(world, playerEntity, stack);
        entity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F, 1.0F);
        if (playerEntity.isInCreativeMode()) {
            entity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
        }

        world.spawnEntity(entity);
        world.playSoundFromEntity(null, entity, SoundEvents.ITEM_TRIDENT_THROW.value(), SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!playerEntity.isInCreativeMode()) {
            playerEntity.getInventory().removeOne(stack);
        }
    }

    @Override
    public boolean onClicked(ItemStack stack, ItemStack otherStack, Slot slot, ClickType clickType, PlayerEntity player, StackReference cursorStackReference) {
        if (stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0) < stack.getOrDefault(ModDataComponentTypes.FLUIDITY_MAX_CHARGES, 5) && otherStack.isOf(ModItems.SHIMMER_POWDER) && clickType == ClickType.RIGHT) {

            stack.set(ModDataComponentTypes.FLUIDITY_CHARGES, stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0) + 1);
            player.playSoundToPlayer(BOBConstants.FLUIDITY_CHARGE_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);
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
        list.add(2, Text.literal(" "));

        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT) {
            list.add(3, Text.translatable("item.bitsofbox.fluidity.tooltip.ability").withColor(0xFF55FF));
            list.add(4, Text.translatable("item.bitsofbox.fluidity.tooltip.trident.1").withColor(0xAAAAAA));
            list.add(5, Text.translatable("item.bitsofbox.fluidity.tooltip.trident.2").withColor(0xAAAAAA));
            list.add(6, Text.translatable("item.bitsofbox.fluidity.tooltip.trident.3").withColor(0xAAAAAA));
            list.add(7, Text.translatable("item.bitsofbox.fluidity.tooltip.trident.4").withColor(0xAAAAAA));
            list.add(8, Text.translatable("item.bitsofbox.fluidity.tooltip.trident.5").withColor(0xAAAAAA));
            list.add(9, Text.literal(" "));
        }
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.AXE) {
            if (stack.getHolder() != null) {
                if (BOBUtil.stackHasEnchantment(stack.getHolder().getWorld(), stack, BOBConstants.BORDERLINKED_ENCHANT)) {
                    list.add(3, Text.translatable("item.bitsofbox.fluidity.tooltip.axe.borderlinked.1").withColor(0xAAAAAA));
                    list.add(4, Text.translatable("item.bitsofbox.fluidity.tooltip.axe.borderlinked.2").withColor(0xAAAAAA));
                    list.add(5, Text.translatable("item.bitsofbox.fluidity.tooltip.axe.borderlinked.3").withColor(0xAAAAAA));
                } else {
                    list.add(3, Text.translatable("item.bitsofbox.fluidity.tooltip.axe.1").withColor(0xAAAAAA));
                    list.add(4, Text.translatable("item.bitsofbox.fluidity.tooltip.axe.2").withColor(0xAAAAAA));
                    list.add(5, Text.translatable("item.bitsofbox.fluidity.tooltip.axe.3").withColor(0xAAAAAA));
                }
            } else {
                list.add(3, Text.translatable("item.bitsofbox.fluidity.tooltip.axe.1").withColor(0xAAAAAA));
                list.add(4, Text.translatable("item.bitsofbox.fluidity.tooltip.axe.2").withColor(0xAAAAAA));
                list.add(5, Text.translatable("item.bitsofbox.fluidity.tooltip.axe.3").withColor(0xAAAAAA));
            }
            list.add(6, Text.literal(" "));
        }
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.PICKAXE) {
            list.add(3, Text.translatable("item.bitsofbox.fluidity.tooltip.ability").withColor(0xFF55FF));
            list.add(4, Text.translatable("item.bitsofbox.fluidity.tooltip.pickaxe.1").withColor(0xAAAAAA));
            list.add(5, Text.translatable("item.bitsofbox.fluidity.tooltip.pickaxe.2").withColor(0xAAAAAA));
            list.add(6, Text.translatable("item.bitsofbox.fluidity.tooltip.pickaxe.3").withColor(0xAAAAAA));
            list.add(7, Text.translatable("item.bitsofbox.fluidity.tooltip.pickaxe.4").withColor(0xAAAAAA));
            list.add(8, Text.literal(" "));
        }

        if (charges == 0) list.add(1, Text.translatable("tip.bitsofbox.fluidity_refill").withColor(0xA024FF));
    }

    static {
        STRIPPED_BLOCKS = (new ImmutableMap.Builder()).put(Blocks.OAK_WOOD, Blocks.STRIPPED_OAK_WOOD).put(Blocks.OAK_LOG, Blocks.STRIPPED_OAK_LOG).put(Blocks.DARK_OAK_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD).put(Blocks.DARK_OAK_LOG, Blocks.STRIPPED_DARK_OAK_LOG).put(Blocks.ACACIA_WOOD, Blocks.STRIPPED_ACACIA_WOOD).put(Blocks.ACACIA_LOG, Blocks.STRIPPED_ACACIA_LOG).put(Blocks.CHERRY_WOOD, Blocks.STRIPPED_CHERRY_WOOD).put(Blocks.CHERRY_LOG, Blocks.STRIPPED_CHERRY_LOG).put(Blocks.BIRCH_WOOD, Blocks.STRIPPED_BIRCH_WOOD).put(Blocks.BIRCH_LOG, Blocks.STRIPPED_BIRCH_LOG).put(Blocks.JUNGLE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD).put(Blocks.JUNGLE_LOG, Blocks.STRIPPED_JUNGLE_LOG).put(Blocks.SPRUCE_WOOD, Blocks.STRIPPED_SPRUCE_WOOD).put(Blocks.SPRUCE_LOG, Blocks.STRIPPED_SPRUCE_LOG).put(Blocks.WARPED_STEM, Blocks.STRIPPED_WARPED_STEM).put(Blocks.WARPED_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE).put(Blocks.CRIMSON_STEM, Blocks.STRIPPED_CRIMSON_STEM).put(Blocks.CRIMSON_HYPHAE, Blocks.STRIPPED_CRIMSON_HYPHAE).put(Blocks.MANGROVE_WOOD, Blocks.STRIPPED_MANGROVE_WOOD).put(Blocks.MANGROVE_LOG, Blocks.STRIPPED_MANGROVE_LOG).put(Blocks.BAMBOO_BLOCK, Blocks.STRIPPED_BAMBOO_BLOCK).build();
    }
}
