package com.dyn.achievements;

import com.dyn.DYNServerMod;
import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.Requirements;
import com.dyn.achievements.achievement.Requirements.BreakRequirement;
import com.dyn.achievements.achievement.Requirements.LocationRequirement;
import com.dyn.achievements.achievement.Requirements.PickupRequirement;
import com.dyn.achievements.handlers.AchievementManager;
import com.dyn.achievements.proxy.Proxy;
import com.dyn.achievements.reference.MetaData;
import com.dyn.achievements.reference.Reference;
import com.dyn.server.database.DBManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.util.ResourceLocation;
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
		try {

			JsonArray jsonA = DBManager.getAchievementDBAsJson().get("achievements").getAsJsonArray();
			for (JsonElement ach : jsonA) {
				AchievementPlus.JsonToAchievement(ach.getAsJsonObject());
			}
		} catch (Exception e) {
			DYNServerMod.logger.error("Could not create Achievements", e);
			Requirements r = new Requirements();
			BreakRequirement br = r.new BreakRequirement();
			br.setAmountNeeded(2);
			br.setFromItemId(35, 4);
			br.setRequirementId(1);
			r.addRequirement(br);
			PickupRequirement pr = r.new PickupRequirement();
			pr.setAmountNeeded(2);
			pr.setFromItemId(35, 5);
			pr.setRequirementId(1);
			r.addRequirement(pr);
			LocationRequirement lr = r.new LocationRequirement();
			lr.setRequirementId(1);
			lr.name = "Testing Radius";
			lr.x1 = 0;
			lr.z1 = 0;
			lr.y1 = 64;
			lr.r = 5;
			r.addRequirement(lr);
			LocationRequirement lr2 = r.new LocationRequirement();
			lr2.setRequirementId(2);
			lr2.name = "Testing Box";
			lr2.x1 = 5;
			lr2.z1 = 5;
			lr2.y1 = 20;
			lr2.x2 = 10;
			lr2.z2 = 10;
			lr2.y2 = 128;
			r.addRequirement(lr2);
			new AchievementPlus(r, "No Connection", "The Achievement file could not be found", 0, 0, 0, 0, 0, 0, 0,
					AchievementManager.findAchievementByName(null), false,
					new ResourceLocation("Minecraft", "textures/items/barrier.png").toString());
		}
		proxy.init();
	}
}
