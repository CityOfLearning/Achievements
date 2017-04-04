package com.dyn.achievements.proxy;

import java.io.ByteArrayOutputStream;

import org.apache.commons.net.ftp.FTPClient;

import com.dyn.DYNServerMod;
import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.Requirements;
import com.dyn.achievements.achievement.Requirements.BreakRequirement;
import com.dyn.achievements.achievement.Requirements.LocationRequirement;
import com.dyn.achievements.achievement.Requirements.PickupRequirement;
import com.dyn.achievements.handlers.AchievementManager;
import com.dyn.server.database.DBManager;
import com.dyn.server.keys.KeyManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.util.ResourceLocation;

public class Client implements Proxy {

	@Override
	public void init() {
		try {
			JsonParser parser = new JsonParser();

			if (DYNServerMod.apacheNetLoaded) {
				FTPClient client = new FTPClient();

				client.connect(KeyManager.getFtpKeys().getLeft());
				client.login(KeyManager.getFtpKeys().getMiddle(), KeyManager.getFtpKeys().getRight());
				client.enterLocalPassiveMode();

				ByteArrayOutputStream fis = new ByteArrayOutputStream();

				if (!client.retrieveFile("/Minecraft/current_achievements.json", fis)) {
					// throw an exception to fail and make the default null
					// achievement
					fis.close();
					client.disconnect();
					throw new Exception("Failed to retrieve achievement file");
				}
				fis.close();
				client.disconnect();

				JsonObject achievementJson = parser.parse(fis.toString()).getAsJsonObject();
				JsonArray jsonA = achievementJson.get("achievements").getAsJsonArray();
				for (JsonElement ach : jsonA) {
					DYNServerMod.logger.info("Adding Achievement: " + ach.getAsJsonObject().get("name").getAsString());
					AchievementPlus.JsonToAchievement(ach.getAsJsonObject());
				}
			} else {
				// the apache files were not loaded... cuz its dumb
				JsonObject achievementJson = DBManager.getAchievementDBAsJson();

				for (JsonElement ach : achievementJson.get("achievements").getAsJsonArray()) {
					AchievementPlus.JsonToAchievement(ach.getAsJsonObject());
				}
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
		// Render GUI when on call from client
	}
}