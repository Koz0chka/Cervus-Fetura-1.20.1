package net.koz0chka.cervusfetura;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.koz0chka.cervusfetura.entity.ModEntities;
import net.koz0chka.cervusfetura.entity.client.DeerRenderer;
import net.koz0chka.cervusfetura.entity.client.ModModelLayers;
import net.koz0chka.cervusfetura.entity.client.cervusfetura_deer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public class CervusFeturaClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(ModEntities.DEER, DeerRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.DEER, cervusfetura_deer::getTexturedModelData);
    }
}
