package org.aussiebox.bitsofbox.entity;

import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.aussiebox.bitsofbox.BOBConstants;
import org.aussiebox.bitsofbox.block.ModBlocks;
import org.aussiebox.bitsofbox.blockentity.ShimmerglassBlockEntity;
import org.aussiebox.bitsofbox.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FluidityTridentEntity extends PersistentProjectileEntity {
    private static final TrackedData<Byte> LOYALTY;
    private static final TrackedData<Boolean> ENCHANTED;
    private static final TrackedData<Integer> BLOCK_CHANGES_REMAINING;
    private boolean dealtDamage;
    public int returnTimer;

    public FluidityTridentEntity(EntityType<? extends FluidityTridentEntity> entityType, World world) {
        super(entityType, world);
    }

    public FluidityTridentEntity(World world, LivingEntity owner, ItemStack stack) {
        super(ModEntities.FluidityTridentEntityType, owner, world, stack, null);
        this.setStack(stack.copy());
        this.dataTracker.set(LOYALTY, (byte)5);
        this.dataTracker.set(ENCHANTED, true);
        this.dataTracker.set(BLOCK_CHANGES_REMAINING, BOBConstants.fluidityBlockChangeMaximums().get(this.getItemStack().getItem()));
    }

    public FluidityTridentEntity(World world, double x, double y, double z, ItemStack stack) {
        super(ModEntities.FluidityTridentEntityType, x, y, z, world, stack, stack);
        this.setStack(stack.copy());
        this.dataTracker.set(LOYALTY, (byte)5);
        this.dataTracker.set(ENCHANTED, true);
        this.dataTracker.set(BLOCK_CHANGES_REMAINING, BOBConstants.fluidityBlockChangeMaximums().get(this.getItemStack().getItem()));
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(LOYALTY, (byte)5);
        builder.add(ENCHANTED, true);
        builder.add(BLOCK_CHANGES_REMAINING, 10);
    }

    @Override
    public void tick() {

        Entity entity = this.getOwner();
        int i = this.dataTracker.get(LOYALTY);
        if (i > 0 && (this.dealtDamage || this.isNoClip()) && entity != null) {
            if (!this.isOwnerAlive()) {
                if (!this.getWorld().isClient && this.pickupType == PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.discard();
            } else {
                this.setNoClip(true);
                Vec3d vec3d = entity.getEyePos().subtract(this.getPos());
                this.setPos(this.getX(), this.getY() + vec3d.y * 0.015 * (double)i, this.getZ());
                if (this.getWorld().isClient) {
                    this.lastRenderY = this.getY();
                }

                double d = 0.05 * (double)i;
                this.setVelocity(this.getVelocity().multiply(0.95).add(vec3d.normalize().multiply(d)));
                if (this.returnTimer == 0) {
                    this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
                }

                ++this.returnTimer;
            }
        }

        super.tick();
        if (this.dataTracker.get(BLOCK_CHANGES_REMAINING) > 0) this.setOnGround(false);
    }

    private boolean isOwnerAlive() {
        Entity entity = this.getOwner();
        if (entity != null && entity.isAlive()) {
            return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
        } else {
            return false;
        }
    }

    public boolean isEnchanted() {
        return this.dataTracker.get(ENCHANTED);
    }

    @Nullable
    protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
        return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        float f = 8.0F;
        Entity entity2 = this.getOwner();
        DamageSource damageSource = this.getDamageSources().trident(this, entity2 == null ? this : entity2);
        World var7 = this.getWorld();
        if (var7 instanceof ServerWorld serverWorld) {
            f = EnchantmentHelper.getDamage(serverWorld, Objects.requireNonNull(this.getWeaponStack()), entity, damageSource, f);
        }

        this.dealtDamage = true;
        if (entity.damage(damageSource, f)) {
            if (entity.getType() == EntityType.ENDERMAN) {
                return;
            }

            var7 = this.getWorld();
            if (var7 instanceof ServerWorld serverWorld) {
                EnchantmentHelper.onTargetDamaged(serverWorld, entity, damageSource, this.getWeaponStack());
            }

            if (entity instanceof LivingEntity livingEntity) {
                this.knockback(livingEntity, damageSource);
                this.onHit(livingEntity);
            }
        }

        this.setVelocity(this.getVelocity().multiply(-0.01, -0.1, -0.01));
        this.playSound(SoundEvents.ITEM_TRIDENT_HIT, 1.0F, 1.0F);
    }

    @Override
    protected void onBlockHitEnchantmentEffects(ServerWorld world, BlockHitResult blockHitResult, ItemStack weaponStack) {

        Vec3d vec3d = blockHitResult.getBlockPos().clampToWithin(blockHitResult.getPos());
        Entity var6 = this.getOwner();
        LivingEntity var10002;
        if (var6 instanceof LivingEntity livingEntity) {
            var10002 = livingEntity;
        } else {
            var10002 = null;
        }

        EnchantmentHelper.onHitBlock(world, weaponStack, var10002, this, null, vec3d, world.getBlockState(blockHitResult.getBlockPos()), (item) -> this.kill());
    }

    @Override
    public ItemStack getWeaponStack() {
        return this.getItemStack();
    }

    @Override
    protected boolean tryPickup(PlayerEntity player) {
        return super.tryPickup(player) || this.isNoClip() && this.isOwner(player) && player.getInventory().insertStack(this.asItemStack());
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(ModItems.WOODEN_FLUIDITY);
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.ITEM_TRIDENT_HIT_GROUND;
    }

    @Override
    public void onPlayerCollision(PlayerEntity player) {
        if (this.isOwner(player) || this.getOwner() == null) {
            super.onPlayerCollision(player);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        super.readCustomDataFromNbt(tag);
        this.dealtDamage = tag.getBoolean("DealtDamage");
        this.dataTracker.set(LOYALTY, this.getLoyalty(this.getItemStack()));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        super.writeCustomDataToNbt(tag);
        tag.putBoolean("DealtDamage", this.dealtDamage);
    }

    private byte getLoyalty(ItemStack stack) {
        World var3 = this.getWorld();
        if (var3 instanceof ServerWorld serverWorld) {
            return (byte) MathHelper.clamp(EnchantmentHelper.getTridentReturnAcceleration(serverWorld, stack, this), 0, 127);
        } else {
            return 0;
        }
    }

    @Override
    public void age() {
        int i = this.dataTracker.get(LOYALTY);
        if (this.pickupType != PickupPermission.ALLOWED || i <= 0) {
            super.age();
        }
    }

    @Override
    protected float getDragInWater() {
        return 0.99F;
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        BlockPos blockPos = blockHitResult.getBlockPos();
        if (this.getWorld().getBlockState(blockPos).getCollisionShape(getWorld(), blockPos) == VoxelShapes.empty()) return;
        if (this.getWorld().getBlockState(blockPos).isOf(ModBlocks.SHIMMERGLASS)) return;

        if (this.dataTracker.get(BLOCK_CHANGES_REMAINING) == 0) {
            super.onBlockHit(blockHitResult);
            this.getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, blockPos, GameEvent.Emitter.of(this, this.getWorld().getBlockState(blockPos)));
            return;
        }

        BlockState previousBlockState = this.getEntityWorld().getBlockState(blockPos);
        this.getEntityWorld().setBlockState(blockPos, ModBlocks.SHIMMERGLASS.getDefaultState());

        if (this.getEntityWorld().getBlockEntity(blockPos) instanceof ShimmerglassBlockEntity blockEntity) {
            blockEntity.setPreviousBlockState(previousBlockState);
            if (this.getOwner() != null) blockEntity.setOwner(this.getOwner());
            blockEntity.resetTicksAliveLeft();
        }

        this.dataTracker.set(BLOCK_CHANGES_REMAINING, this.dataTracker.get(BLOCK_CHANGES_REMAINING)-1);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK) onBlockHit((BlockHitResult) hitResult);
        else super.onCollision(hitResult);
    }

    static {
        LOYALTY = DataTracker.registerData(FluidityTridentEntity.class, TrackedDataHandlerRegistry.BYTE);
        ENCHANTED = DataTracker.registerData(FluidityTridentEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        BLOCK_CHANGES_REMAINING = DataTracker.registerData(FluidityTridentEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
}
