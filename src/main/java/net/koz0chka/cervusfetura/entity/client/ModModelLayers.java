package net.koz0chka.cervusfetura.entity.client;

import net.koz0chka.cervusfetura.CervusFetura;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer DEER =
            new EntityModelLayer(new Identifier(CervusFetura.MOD_ID, "deer"), "main");
}
