package org.aussiebox.bitsofbox.client.mixin;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.aussiebox.bitsofbox.BOB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1))
    private void onInit(CallbackInfo ci) {
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_axe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_pickaxe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_trident")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_trident_hand")));

        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_axe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_pickaxe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_trident")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_trident_hand")));

        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_axe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_pickaxe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_trident")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_trident_hand")));

        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_axe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_pickaxe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_trident")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_trident_hand")));

        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_axe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_pickaxe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_trident")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_trident_hand")));

        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_axe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_pickaxe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_trident")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_trident_hand")));

        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_axe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_pickaxe")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_trident")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_trident_hand")));
    }
}
