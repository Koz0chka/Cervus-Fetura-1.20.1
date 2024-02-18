package net.koz0chka.cervusfetura.entity.client;

import net.koz0chka.cervusfetura.CervusFetura;
import net.koz0chka.cervusfetura.entity.custom.DeerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class DeerRenderer extends MobEntityRenderer<DeerEntity, cervusfetura_deer<DeerEntity>> {
    private static final Identifier TEXTURE = new Identifier(CervusFetura.MOD_ID, "textures/entity/deer.png");

    public DeerRenderer(EntityRendererFactory.Context context) {
        super(context, new cervusfetura_deer<>(context.getPart(ModModelLayers.DEER)), 0.6f);
    }

    @Override
    public Identifier getTexture(DeerEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(DeerEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if(mobEntity.isBaby()){
            matrixStack.scale(0.5f,0.5f,0.5f);
        } else {
            matrixStack.scale(1f,1f,1f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
