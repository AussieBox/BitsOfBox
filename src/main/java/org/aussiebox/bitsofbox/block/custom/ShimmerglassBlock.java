package org.aussiebox.bitsofbox.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.blockentity.ModBlockEntities;
import org.aussiebox.bitsofbox.blockentity.ShimmerglassBlockEntity;
import org.aussiebox.bitsofbox.entity.FluidityTridentEntity;
import org.aussiebox.bitsofbox.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ShimmerglassBlock extends BlockWithEntity {
    public static final MapCodec<ShimmerglassBlock> CODEC = createCodec(ShimmerglassBlock::new);

    public ShimmerglassBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean hasSidedTransparency(BlockState state) {
        return true;
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState adjacentState, Direction direction) {
        if (adjacentState.isIn(TagKey.of(RegistryKeys.BLOCK, BOB.id("glass")))) return true;
        else return adjacentState.isOpaque();
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (!(world.getBlockEntity(pos) instanceof ShimmerglassBlockEntity shimmerglass)) return;
        if (!Objects.equals(entity.getUuid().toString(), shimmerglass.getOwner().toString())) {
            entity.handleFallDamage(fallDistance, 1.2F, entity.getDamageSources().fall());
            return;
        }

        if (entity.isSneaking()) entity.handleFallDamage(fallDistance, 0.0F, entity.getDamageSources().fall());
        else entity.handleFallDamage(fallDistance, 0.5F, entity.getDamageSources().fall());
    }

    public static int getLuminance(BlockState state) {
        return 3;
    }

    public static boolean getEmissive(BlockState state, BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof ShimmerglassBlockEntity entity) {
            return entity.getTicksAliveLeft() != -1;
        }

        return false;
    }

    @Override
    protected float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof ShimmerglassBlockEntity blockEntity && blockEntity.getTicksAliveLeft() != -1) return 0.0F;
        return super.calcBlockBreakingDelta(state, player, world, pos);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ShimmerglassBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        if (!(world.getBlockEntity(pos) instanceof ShimmerglassBlockEntity blockEntity)) return;
        if (placer != null) blockEntity.setOwner(placer);
        blockEntity.makePermanent();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        EntityShapeContext entityContext = (EntityShapeContext) context;

        if (entityContext == null) return VoxelShapes.fullCube();
        if (entityContext.getEntity() == null) return VoxelShapes.fullCube();
        if (!(world.getBlockEntity(pos) instanceof ShimmerglassBlockEntity shimmerglass)) return VoxelShapes.fullCube();

        if (entityContext.getEntity().getType() == ModEntities.FluidityTridentEntityType)
            if (Objects.equals(Objects.requireNonNull(((FluidityTridentEntity) entityContext.getEntity()).getOwner()).toString(), shimmerglass.getOwner().toString()))
                return VoxelShapes.empty();

        if (Objects.equals(entityContext.getEntity().getUuid().toString(), shimmerglass.getOwner().toString())) {
            if (entityContext.getEntity().getY() >= pos.getY()+1 && pos.isWithinDistance(entityContext.getEntity().getPos(), 1) && !entityContext.getEntity().isSneaking()) return VoxelShapes.fullCube();
            return VoxelShapes.empty();
        }

        return VoxelShapes.fullCube();
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, ModBlockEntities.SHIMMERGLASS_BLOCK_ENTITY, ShimmerglassBlockEntity::tick);
    }
}
