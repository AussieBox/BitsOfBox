package org.aussiebox.bitsofbox.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.aussiebox.bitsofbox.BOB;

public class ModEntities {

    public static final EntityType<DragonflameCactusEntity> DragonflameCactusEntityType = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(BOB.MOD_ID, "dragonflame_cactus"),
            EntityType.Builder.<DragonflameCactusEntity>create(DragonflameCactusEntity::new, SpawnGroup.MISC)
                    .dimensions(0.4F, 0.4F).build("dragonflame_cactus")
    );

    public static void init() {
        BOB.LOGGER.info("Registering Mod Entities for " + BOB.MOD_ID);
    }
}
