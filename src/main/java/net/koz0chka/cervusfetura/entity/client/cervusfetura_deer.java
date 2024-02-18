// Made with Blockbench 4.9.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports

package net.koz0chka.cervusfetura.entity.client;

import net.koz0chka.cervusfetura.entity.custom.DeerEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class cervusfetura_deer<T extends DeerEntity> extends SinglePartEntityModel<T> {
	private final ModelPart body;

	public cervusfetura_deer(ModelPart root) {
		this.body = root.getChild("body");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -18.0F, -9.0F, 6.0F, 6.0F, 18.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData left_front_leg = body.addChild("left_front_leg", ModelPartBuilder.create().uv(16, 38).cuboid(3.0F, -18.0F, -10.0F, 2.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(50, 2).cuboid(3.0F, -20.0F, -8.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(42, 0).cuboid(3.0F, -10.0F, -8.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(8, 43).cuboid(1.0F, -12.0F, -8.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData right_front_leg = body.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 12).cuboid(-5.0F, -20.0F, -8.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(30, 0).cuboid(-5.0F, -18.0F, -10.0F, 2.0F, 8.0F, 8.0F, new Dilation(0.0F))
		.uv(18, 24).cuboid(-5.0F, -10.0F, -8.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(0, 43).cuboid(-3.0F, -12.0F, -8.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData left_back_leg = body.addChild("left_back_leg", ModelPartBuilder.create().uv(36, 38).cuboid(3.0F, -18.0F, 3.0F, 2.0F, 8.0F, 4.0F, new Dilation(0.0F))
		.uv(44, 50).cuboid(3.0F, -16.0F, 7.0F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(48, 36).cuboid(1.0F, -12.0F, 9.0F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F))
		.uv(10, 10).cuboid(3.0F, -10.0F, 5.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData right_back_leg = body.addChild("right_back_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -7.75F, -4.25F, 2.0F, 8.0F, 4.0F, new Dilation(0.0F))
		.uv(8, 0).cuboid(-1.5F, 0.25F, -2.25F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F))
		.uv(36, 50).cuboid(-1.5F, -5.75F, -0.25F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F))
		.uv(48, 14).cuboid(0.5F, -1.75F, 1.75F, 2.0F, 12.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -10.25F, 7.25F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(41, 25).cuboid(-1.0F, -2.5F, -1.5F, 2.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -18.5F, 9.5F));

		ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 24).cuboid(-3.0F, -6.5F, -3.0F, 6.0F, 13.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -21.5F, -8.0F));

		ModelPartData head = neck.addChild("head", ModelPartBuilder.create().uv(24, 24).cuboid(-2.0F, -2.5F, -4.5F, 4.0F, 5.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.0F, -2.5F));

		ModelPartData right_ear = head.addChild("right_ear", ModelPartBuilder.create(), ModelTransform.pivot(-3.842F, -2.0603F, 4.9142F));

		ModelPartData cube_r1 = right_ear.addChild("cube_r1", ModelPartBuilder.create().uv(51, 28).cuboid(-2.5F, -1.5F, -2.0F, 3.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.342F, -0.9397F, -1.4142F, 2.3562F, 0.0F, -1.2217F));

		ModelPartData left_ear = head.addChild("left_ear", ModelPartBuilder.create(), ModelTransform.pivot(3.842F, -2.0603F, 4.9142F));

		ModelPartData cube_r2 = left_ear.addChild("cube_r2", ModelPartBuilder.create().uv(52, 50).cuboid(-0.5F, -1.5F, -2.0F, 3.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-0.342F, -0.9397F, -1.4142F, 2.3562F, 0.0F, 1.2217F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return body;
	}

	@Override
	public void setAngles(DeerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

	}
}