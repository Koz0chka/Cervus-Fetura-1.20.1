package net.koz0chka.cervusfetura.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.fabricmc.fabric.impl.object.builder.FabricEntityType;
import net.koz0chka.cervusfetura.CervusFetura;
import net.koz0chka.cervusfetura.entity.custom.DeerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<DeerEntity> DEER = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(CervusFetura.MOD_ID, "deer"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, DeerEntity::new)
                    .dimensions(EntityDimensions.fixed(1f, 1f)).build());

    public static void registerModEntities() {
        CervusFetura.LOGGER.info("Registering Entities for " + CervusFetura.MOD_ID);
    }
};