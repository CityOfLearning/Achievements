package com.dyn.achievements;

import com.dyn.DYNServerMod;
import com.dyn.achievements.handlers.AchievementManager;
import com.dyn.achievements.proxy.Proxy;
import com.dyn.achievements.reference.MetaData;
import com.dyn.achievements.reference.Reference;
import com.dyn.server.database.DBManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:dyn|server")
public class AchievementsMod {

	@Mod.Instance(Reference.MOD_ID)
	public static AchievementsMod instance;

	@SidedProxy(modId = Reference.MOD_ID, clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static Proxy proxy;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		try {
			JsonObject jsonO = DBManager.getAchievementMapDBAsJson();
			if (jsonO != null) {
				JsonArray jsonA = jsonO.get("achievement_maps").getAsJsonArray();
				for (JsonElement ach : jsonA) {
					JsonObject achObj = ach.getAsJsonObject();
					AchievementManager.addAchievementPage(achObj.get("name").getAsString(),
							achObj.get("texture").isJsonNull() ? null : achObj.get("texture").getAsString(),
							achObj.get("map_id").getAsInt());
				}
			}
		} catch (Exception e) {
			// likely due to offline mode but still can cause crashes
			DYNServerMod.logger.error("Could not get Achievement Maps", e);
		}
	}

	@Mod.EventHandler
	public void init(FMLServerStoppedEvent event) {

	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		MetaData.init(event.getModMetadata());
		proxy.init();
	}
}
