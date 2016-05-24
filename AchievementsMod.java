package com.dyn.achievements;

import java.io.InputStreamReader;
import java.net.URL;

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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppedEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class AchievementsMod {

	@Mod.Instance(Reference.MOD_ID)
	public static AchievementsMod instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static Proxy proxy;

	@Mod.Metadata(Reference.MOD_ID)
	public ModMetadata metadata;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		// this will have to change when more pages need to be made
		AchievementManager.addAchievementPage("DYN Achievements", AchievementManager.getAllAchievements());
	}

	@Mod.EventHandler
	public void init(FMLServerStoppedEvent event) {

	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		metadata = MetaData.init(metadata);

		Configuration configs = new Configuration(event.getSuggestedConfigurationFile());
		try {
			configs.load();
		} catch (RuntimeException e) {
			DYNServerMod.logger.warn(e);
		}

		try { // Download the JSON into a json list
				// URL url = new URL("Not a URL: sometimes we need to fail");
			URL url = new URL("https://dl.dropboxusercontent.com/u/33377940/achievements.json");
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(new JsonReader(new InputStreamReader(url.openStream())));
			JsonObject overallObject = element.getAsJsonObject();
			JsonArray jsonA = overallObject.get("achievements").getAsJsonArray();
			for (JsonElement ach : jsonA) {
				AchievementPlus.JsonToAchievement(ach.getAsJsonObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
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
					new ResourceLocation("Minecraft", "textures/items/barrier.png"));
		}
		proxy.init();
	}
}
