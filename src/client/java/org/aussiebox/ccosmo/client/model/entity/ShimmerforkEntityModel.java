// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package org.aussiebox.ccosmo.client.model.entity;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.aussiebox.ccosmo.CCOSMO;
import org.aussiebox.ccosmo.entity.ShimmerforkEntity;

public class ShimmerforkEntityModel extends EntityModel<ShimmerforkEntity> {
	public static final Identifier TEXTURE = CCOSMO.id("textures/item/shimmer_tools/base/trident_hand.png");
	public static final EntityModelLayer LAYER = new EntityModelLayer(CCOSMO.id("shimmerfork"), "shimmerfork");
	private final ModelPart root;

	public ShimmerforkEntityModel(ModelPart root) {
		this.root = root;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData trident = modelPartData.addChild("trident", ModelPartBuilder.create().uv(16, 14).cuboid(0.0F, -2.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 20).cuboid(11.0F, -13.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 11).cuboid(12.0F, -14.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 20).cuboid(13.0F, -15.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 20).cuboid(14.0F, -16.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 11).cuboid(15.0F, -17.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 9).cuboid(16.0F, -19.0F, -1.0F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 4).cuboid(19.0F, -23.0F, -1.0F, 4.0F, 4.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 15).cuboid(22.0F, -24.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(24, 3).cuboid(23.0F, -23.0F, -1.0F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 15).cuboid(23.0F, -25.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 9).cuboid(24.0F, -26.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(20, 23).cuboid(25.0F, -27.0F, -1.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 4).cuboid(17.0F, -27.0F, -1.0F, 3.0F, 8.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 23).cuboid(20.0F, -27.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 23).cuboid(21.0F, -28.0F, -1.0F, 1.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(24, 0).cuboid(22.0F, -28.0F, -1.0F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(19.0F, -20.0F, -1.0F, 8.0F, 3.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 13).cuboid(24.0F, -21.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(8, 13).cuboid(25.0F, -22.0F, -1.0F, 3.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 23).cuboid(26.0F, -23.0F, -1.0F, 2.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 20).cuboid(10.0F, -12.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 17).cuboid(9.0F, -11.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 6).cuboid(8.0F, -10.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 3).cuboid(7.0F, -9.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(18, 0).cuboid(6.0F, -8.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 17).cuboid(1.0F, -3.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(6, 17).cuboid(2.0F, -4.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 14).cuboid(3.0F, -5.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 23).cuboid(4.0F, -6.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F))
		.uv(12, 17).cuboid(5.0F, -7.0F, -1.0F, 2.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -3.0F, 0.5F, 0.0F, 0.0F, 2.3562F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	@Override
	public void setAngles(ShimmerforkEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
		this.root.render(matrices, vertices, light, overlay, color);
	}
}