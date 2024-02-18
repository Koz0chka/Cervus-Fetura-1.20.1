package net.koz0chka.cervusfetura;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.koz0chka.cervusfetura.entity.ModEntities;
import net.koz0chka.cervusfetura.entity.custom.DeerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CervusFetura implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final String MOD_ID = "cervusfetura";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		FabricDefaultAttributeRegistry.register(ModEntities.DEER, DeerEntity.createDeerAttributes());

		LOGGER.info("Запуск успешен!");
	}
}