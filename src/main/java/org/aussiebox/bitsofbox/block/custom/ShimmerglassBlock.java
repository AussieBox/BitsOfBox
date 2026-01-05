package org.aussiebox.bitsofbox.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
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

        if (entityContext.getEntity().getType() == ModEntities.DragonflameCactusEntityType)
            if (Objects.equals(Objects.requireNonNull(((FluidityTridentEntity) entityContext.getEntity()).getOwner()).toString(), shimmerglass.getOwner().toString()))
                return VoxelShapes.empty();

        if (Objects.equals(entityContext.getEntity().getUuid().toString(), shimmerglass.getOwner().toString())) return VoxelShapes.empty();

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
