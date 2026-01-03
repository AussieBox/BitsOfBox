package org.aussiebox.bitsofbox.item.custom;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.aussiebox.bitsofbox.BOBConstants;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;

import java.util.Arrays;

public class FluidityItem extends MiningToolItem {

    public FluidityItem(Item.Settings settings) {
        super(ToolMaterials.WOOD, BlockTags.AIR, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT) {
            if (!user.isSneaking()) {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(stack);
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

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public ToolMaterial getMaterial() {
        // TODO: Add copper material type
        return super.getMaterial();
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
            return UseAction.SPEAR;

        return UseAction.NONE;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT)
            return 72000;

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
}
