package org.aussiebox.bitsofbox.attach;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import org.aussiebox.bitsofbox.BOB;

public class ModAttachmentTypes {
    public static final AttachmentType<DragonflameCactusFuseAttachedData> DRAGONFLAME_CACTUS_FUSE_ATTACH = AttachmentRegistry.create(
            Identifier.of(BOB.MOD_ID,"dragonflame_cactus_fuse"),
            builder->builder
                    .initializer(()-> DragonflameCactusFuseAttachedData.DEFAULT)
                    .persistent(DragonflameCactusFuseAttachedData.CODEC)
                    .syncWith(
                            DragonflameCactusFuseAttachedData.PACKET_CODEC,
                            AttachmentSyncPredicate.all()
                    )
    );

    public static void init() {

    }
}