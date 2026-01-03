package org.aussiebox.bitsofbox.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.BOBConstants;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
import org.aussiebox.bitsofbox.item.ModItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItem(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {

        if (stack.getItem() == ModItems.WOODEN_FLUIDITY && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_trident")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.STONE_FLUIDITY && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_trident")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.COPPER_FLUIDITY && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_trident")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.GOLD_FLUIDITY && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_trident")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.IRON_FLUIDITY && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_trident")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.DIAMOND_FLUIDITY && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_trident")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.NETHERITE_FLUIDITY && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_trident")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_pickaxe")));
        }

        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {

        if (stack.getItem() == ModItems.WOODEN_FLUIDITY) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_trident_hand")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "wooden_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.STONE_FLUIDITY) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_trident_hand")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "stone_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.COPPER_FLUIDITY) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_trident_hand")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "copper_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.GOLD_FLUIDITY) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_trident_hand")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "gold_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.IRON_FLUIDITY) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_trident_hand")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "iron_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.DIAMOND_FLUIDITY) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_trident_hand")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "diamond_fluidity_pickaxe")));
        }
        if (stack.getItem() == ModItems.NETHERITE_FLUIDITY) {
            BOBConstants.FluidityMode mode = stack.get(ModDataComponentTypes.FLUIDITY_MODE);

            if (mode == BOBConstants.FluidityMode.TRIDENT)
                return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_trident_hand")));
            if (mode == BOBConstants.FluidityMode.AXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_axe")));
            if (mode == BOBConstants.FluidityMode.PICKAXE)
                return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(BOB.MOD_ID, "netherite_fluidity_pickaxe")));
        }

        return bakedModel;
    }
}
