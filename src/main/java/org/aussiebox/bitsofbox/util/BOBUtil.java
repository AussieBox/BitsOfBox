package org.aussiebox.bitsofbox.util;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import org.aussiebox.bitsofbox.BOB;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

}
