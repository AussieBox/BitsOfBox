package org.aussiebox.bitsofbox.util;

import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.aussiebox.bitsofbox.BOB;

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

}
