package org.aussiebox.bitsofbox.cca;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.BOBConstants;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
import org.aussiebox.bitsofbox.item.custom.FluidityItem;
import org.aussiebox.bitsofbox.util.BOBUtil;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class ShimmerPowderData implements AutoSyncedComponent, ServerTickingComponent {
    public static final ComponentKey<ShimmerPowderData> KEY = ComponentRegistry.getOrCreate(BOB.id("shimmer_powder_data"), ShimmerPowderData.class);
    private final PlayerEntity player;
    public int obtainmentsToday = 0;
    public int shimmerseepTicks = 2400;

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
        }
        shimmerseepTicks--;
        if (shimmerseepTicks <= 0) {
            shimmerseepTicks = 2400;
            PlayerInventory inventory = player.getInventory();
            for (int i = 0; i < inventory.size(); i++) {
                ItemStack stack = inventory.getStack(i);

                if (!BOBUtil.stackHasEnchantment(player.getWorld(), stack, BOBConstants.SHIMMERSEEP_ENCHANT)) continue;

                int charges = stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0);
                if (charges < stack.getOrDefault(ModDataComponentTypes.FLUIDITY_MAX_CHARGES, 5))
                    stack.set(ModDataComponentTypes.FLUIDITY_CHARGES, charges+1);
            }
            inventory.markDirty();
            player.playSoundToPlayer(BOBConstants.SHIMMERSEEP_CHARGE_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);
            if (player.getMainHandStack().getItem() instanceof FluidityItem)
                player.playSoundToPlayer(BOBConstants.FLUIDITY_CHARGE_SOUND, SoundCategory.PLAYERS, 1.0F, 1.0F);
        }
        this.sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.obtainmentsToday = tag.contains("obtainmentsToday") ? tag.getInt("obtainmentsToday") : 0;
        this.shimmerseepTicks = tag.contains("shimmerseepTicks") ? tag.getInt("shimmerseepTicks") : 0;
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
        tag.putInt("obtainmentsToday", this.obtainmentsToday);
        tag.putInt("shimmerseepTicks", this.shimmerseepTicks);
    }
}
