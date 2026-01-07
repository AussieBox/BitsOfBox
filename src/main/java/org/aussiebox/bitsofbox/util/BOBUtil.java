package org.aussiebox.bitsofbox.util;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.aussiebox.bitsofbox.BOB;

import java.util.*;

public class BOBUtil {

    public static void grantAdvancement(ServerPlayerEntity player, String path) {
        AdvancementEntry advancement = Objects.requireNonNull(player.getEntityWorld().getServer()).getAdvancementLoader().get(Identifier.of(BOB.MOD_ID, path));
        PlayerAdvancementTracker advancementTracker = player.getAdvancementTracker();
        if (!advancementTracker.getProgress(advancement).isDone()) {
            for (String missing : advancementTracker.getProgress(advancement).getUnobtainedCriteria()) {
                advancementTracker.grantCriterion(advancement, missing);
            }
        }
    }

    public static List<BlockPos> getAllBlockPosInBox(Box box) {
        List<BlockPos> positions = new ArrayList<>();

        for (int x = (int) box.minX; x <= box.maxX; x++) {
            for (int y = (int) box.minY; y <= box.maxY; y++) {
                for (int z = (int) box.minZ; z <= box.maxZ; z++) {
                    positions.add(new BlockPos(x, y, z));
                }
            }
        }

        return positions;
    }

    public static Map<BlockPos, BlockState> getAllBlocksInBox(World world, Box box, boolean includeAir) {
        Map<BlockPos, BlockState> blocks = new HashMap<>();

        for (int x = (int) box.minX; x <= box.maxX; x++) {
            for (int y = (int) box.minY; y <= box.maxY; y++) {
                for (int z = (int) box.minZ; z <= box.maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (world.getBlockState(pos).isOf(Blocks.AIR) || world.getBlockState(pos).isOf(Blocks.CAVE_AIR) || world.getBlockState(pos).isOf(Blocks.VOID_AIR)) {
                        if (includeAir) blocks.putIfAbsent(pos, world.getBlockState(pos));
                    } else {
                        blocks.putIfAbsent(pos, world.getBlockState(pos));
                    }
                }
            }
        }

        return blocks;
    }

}
