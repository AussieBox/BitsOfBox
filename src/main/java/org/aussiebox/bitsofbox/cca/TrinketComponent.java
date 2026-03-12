package org.aussiebox.bitsofbox.cca;

import lombok.Getter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.Vec3d;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.BOBConstants;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class TrinketComponent implements AutoSyncedComponent, ClientTickingComponent, ServerTickingComponent {
    public static final ComponentKey<TrinketComponent> KEY = ComponentRegistry.getOrCreate(BOB.id("trinket_component"), TrinketComponent.class);
    private final PlayerEntity player;

    @Getter
    private boolean gliding = false;
    @Getter
    private boolean flying = false;
    @Getter
    private boolean canFly = false;
    @Getter
    private double pyrrhianBeltFlightTime;
    @Getter
    private boolean wasLastFlying = false;
    @Getter
    private int nonGroundedTime;

    public TrinketComponent(PlayerEntity player) {
        this.player = player;
    }

    public void setGliding(boolean state) {
        this.gliding = state;
        this.sync();
    }

    public void setFlying(boolean state) {
        this.wasLastFlying = this.flying;
        this.flying = state;
        this.sync();
    }

    public void setCanFly(boolean state) {
        this.canFly = state;
        this.sync();
    }

    public void changeFlightTime(double time) {
        this.pyrrhianBeltFlightTime = Math.clamp(this.pyrrhianBeltFlightTime + time, 0, BOBConstants.pyrrhianBeltFlightTimeMaximum);
        this.sync();
    }

    @Override
    public void serverTick() {
        if (pyrrhianBeltFlightTime <= 0) setCanFly(false);
        else setCanFly(true);

        // Switch to gliding when cooldown runs out
        if (!canFly && flying) {
            setFlying(false);
            if (wasLastFlying) setGliding(true);
        }

        if (player.isOnGround() || player.isSwimming() || player.hasVehicle() || player.isInCreativeMode() || player.isSpectator()) {
            setFlying(false);
            setGliding(false);
        }

        // Keep track of time off ground
        if (!player.isOnGround()) nonGroundedTime++;
        else nonGroundedTime = 0;

        // Manage flight time
        if (flying) {
            Vec3d movement = player.getMovement();
            changeFlightTime(-Math.abs(movement.length()));

            setGliding(false);
        } else {
            changeFlightTime(BOBConstants.pyrrhianBeltFlightTimeMaximum/100);
        }

//        if (this.player instanceof ServerPlayerEntity serverPlayer) {
//            PlayerAbilities abilities = serverPlayer.getAbilities();
//            PlayerAbilities newAbilities = serverPlayer.getAbilities();
//
//            if (BOBUtil.playerHasTrinket(this.player, ModItems.PYRRHIAN_BELT)) {
//                if (this.pyrrhianBeltFlightTime > 0 && !abilities.allowFlying) {
//                    newAbilities.allowFlying = true;
//                    serverPlayer.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(newAbilities));
//                } else if ((!player.isInCreativeMode() && !player.isSpectator()) && abilities.allowFlying) {
//                    newAbilities.allowFlying = false;
//                    serverPlayer.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(newAbilities));
//                }
//
//                if (shouldBeFlying) {
//                    newAbilities.flying = true;
//                    serverPlayer.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(newAbilities));
//
//                    shouldBeFlying = false;
//                }
//
//                if (this.pyrrhianBeltFlightTime <= 0 && (!player.isInCreativeMode() && !player.isSpectator())) {
//                    newAbilities.flying = false;
//                    serverPlayer.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(newAbilities));
//                }
//
//                if (abilities.flying) {
//                    Vec3d movement = serverPlayer.getMovement();
//                    this.changeFlightTime(-Math.abs(movement.length()));
//
//                    setGliding(false);
//                    setWasFlyingLastTick(true);
//                } else {
//                    this.changeFlightTime(BOBConstants.pyrrhianBeltFlightTimeMaximum/100);
//
//                    if (wasFlyingLastTick) setGliding(true);
//                    setWasFlyingLastTick(false);
//                }
//
//            } else if ((!player.isInCreativeMode() && !player.isSpectator()) && abilities.allowFlying) {
//                newAbilities.allowFlying = false;
//                newAbilities.flying = false;
//                serverPlayer.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(newAbilities));
//            }
//
//            if (serverPlayer.isOnGround()) setGliding(false);
//        }
    }

    @Override
    public void clientTick() {
//        if (BOBUtil.playerHasTrinket(this.player, ModItems.PYRRHIAN_BELT)) {
//            if (player.jumping && this.pyrrhianBeltFlightTime > 0 && player.getAbilities().allowFlying && !player.getAbilities().flying) {
//                shouldBeFlying = true;
//            }
//        }
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.pyrrhianBeltFlightTime = tag.contains("pyrrhianBeltFlightTime") ? tag.getDouble("pyrrhianBeltFlightTime") : 0;
        this.canFly = tag.contains("canFly") && tag.getBoolean("canFly");
        this.flying = tag.contains("flying") && tag.getBoolean("flying");
        this.gliding = tag.contains("gliding") && tag.getBoolean("gliding");
        this.wasLastFlying = tag.contains("wasLastFlying") && tag.getBoolean("wasLastFlying");
        this.nonGroundedTime = tag.contains("nonGroundedTime") ? tag.getInt("nonGroundedTime") : 0;
    }

    @Override
    public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup wrapperLookup) {
        tag.putDouble("pyrrhianBeltFlightTime", this.pyrrhianBeltFlightTime);
        tag.putBoolean("canFly", this.canFly);
        tag.putBoolean("flying", this.flying);
        tag.putBoolean("gliding", this.gliding);
        tag.putBoolean("wasLastFlying", this.wasLastFlying);
        tag.putInt("nonGroundedTime", this.nonGroundedTime);
    }
}
