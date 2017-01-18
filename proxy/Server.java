package com.dyn.achievements.proxy;

import java.io.File;

import com.dyn.DYNServerMod;
import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.Requirements;
import com.dyn.achievements.achievement.Requirements.BreakRequirement;
import com.dyn.achievements.achievement.Requirements.LocationRequirement;
import com.dyn.achievements.achievement.Requirements.PickupRequirement;
import com.dyn.achievements.handlers.AchievementManager;
import com.dyn.achievements.handlers.EventHandler;
import com.dyn.server.database.DBManager;
import com.dyn.utils.FTPHelper;
import com.dyn.utils.FileUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;

public class Server implements Proxy {

	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new EventHandler());

		try {
			DYNServerMod.logger.info("Initializing Achievements");

			JsonObject achievementJson = DBManager.getAchievementDBAsJson();

			File achievementFile = new File(MinecraftServer.getServer().getDataDirectory(),
					"current_achievements.json"); // _" + LocalDate.now() + "
			FileUtils.createJsonFile(achievementJson, achievementFile);

			if (!DYNServerMod.developmentEnvironment && DYNServerMod.apacheNetLoaded
					&& DYNServerMod.ftpConnectionOpen) {
				// upload to ftp, thread it so it doesnt block
				FTPHelper.uploadFile(achievementFile, "/Minecraft/");
			}

			for (JsonElement ach : achievementJson.get("achievements").getAsJsonArray()) {
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
	}

	/**
	 * @see forge.reference.proxy.Proxy#renderGUI()
	 */
	@Override
	public void renderGUI() {
		// Actions on render GUI for the server (logging)

	}

}