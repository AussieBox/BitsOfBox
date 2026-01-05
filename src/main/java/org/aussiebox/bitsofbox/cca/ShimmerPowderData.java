package org.aussiebox.bitsofbox.cca;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.aussiebox.bitsofbox.BOB;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class ShimmerPowderData implements AutoSyncedComponent, ServerTickingComponent {
    public static final ComponentKey<ShimmerPowderData> KEY = ComponentRegistry.getOrCreate(BOB.id("shimmer_powder_data"), ShimmerPowderData.class);
    private final PlayerEntity player;
    public int obtainmentsToday = 0;

    public ShimmerPowderData(PlayerEntity player) {
        this.player = player;
    }

    public void obtainPowder() {
        obtainmentsToday++;
        this.sync();
    }

    @Override
    public void serverTick() {
        if (player.getWorld().getTimeOfDay() == 0) {
            obtainmentsToday = 0;
            this.sync();
        }
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.obtainmentsToday = tag.contains("obtainments_today") ? tag.getInt("obtainments_today") : 0;
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
        tag.putInt("obtainments_today", this.obtainmentsToday);
    }
}
