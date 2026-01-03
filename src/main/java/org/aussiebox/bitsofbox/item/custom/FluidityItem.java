package org.aussiebox.bitsofbox.item.custom;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
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

public class FluidityItem extends Item {

    public FluidityItem(Item.Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (stack.get(ModDataComponentTypes.FLUIDITY_MODE) == BOBConstants.FluidityMode.TRIDENT) {
            if (!user.isSneaking()) return TypedActionResult.pass(stack);
        }

        if (user.isSneaking()) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT) stack.set(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.AXE);
            else if (mode == BOBConstants.FluidityMode.AXE) stack.set(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.PICKAXE);
            else if (mode == BOBConstants.FluidityMode.PICKAXE) stack.set(ModDataComponentTypes.FLUIDITY_MODE, BOBConstants.FluidityMode.TRIDENT);

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
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
        float f = 3; // Riptide Strength

        float yaw = playerEntity.getYaw();
        float pitch = playerEntity.getPitch();
        float j = -MathHelper.sin(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        float k = -MathHelper.sin(pitch * ((float)Math.PI / 180F));
        float l = MathHelper.cos(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        float m = MathHelper.sqrt(j * j + k * k + l * l);
        j *= f / m;
        k *= f / m;
        l *= f / m;
        playerEntity.addVelocity(j, k, l);
        playerEntity.useRiptide(20, 8.0F, stack);
        if (playerEntity.isOnGround()) {
            float n = 1.1999999F;
            playerEntity.move(MovementType.SELF, new Vec3d(0.0F, 1.1999999F, 0.0F));
        }

        world.playSoundFromEntity(null, playerEntity, (SoundEvent)SoundEvents.ITEM_TRIDENT_RIPTIDE_3, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }
}
