package com.dyn.achievements;

import java.io.InputStreamReader;
import java.net.URL;

import org.apache.logging.log4j.Logger;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.handlers.AchievementHandler;
import com.dyn.achievements.proxy.Proxy;
import com.dyn.achievements.reference.MetaData;
import com.dyn.achievements.reference.Reference;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class AchievementsMod {

	@Mod.Instance(Reference.MOD_ID)
	public static AchievementsMod instance;
	
	@Mod.Metadata(Reference.MOD_ID)
	public ModMetadata metadata;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static Proxy proxy;

	public static Logger logger;

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		AchievementHandler.addAchievementPage("DYN Achievements", AchievementHandler.getAllAchievements());

	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		this.metadata = MetaData.init(this.metadata);
		
		logger = event.getModLog();

		Configuration configs = new Configuration(event.getSuggestedConfigurationFile());
		try {
			configs.load();
		} catch (RuntimeException e) {
			logger.warn(e);
		}

		try { // Download the JSON into a json list
			URL url = new URL("https://dl.dropboxusercontent.com/u/33377940/test.json");
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(new JsonReader(new InputStreamReader(url.openStream())));
			JsonObject overallObject = element.getAsJsonObject();
			JsonArray jsonA = overallObject.get("achievements").getAsJsonArray();
			for (JsonElement ach : jsonA) {
				AchievementPlus.JsonToAchievement(ach.getAsJsonObject());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Json Parsing Failed");
		}

		AchievementHandler.sortAndAssignMaps();

		proxy.init();
	}
}
