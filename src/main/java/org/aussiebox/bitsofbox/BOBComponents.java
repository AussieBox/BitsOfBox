package org.aussiebox.bitsofbox;

import net.minecraft.entity.player.PlayerEntity;
import org.aussiebox.bitsofbox.cca.ShimmerPowderData;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class BOBComponents implements EntityComponentInitializer {

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {

        registry.beginRegistration(PlayerEntity.class, ShimmerPowderData.KEY)
                .respawnStrategy(RespawnCopyStrategy.ALWAYS_COPY)
                .end(ShimmerPowderData::new);

    }

}
