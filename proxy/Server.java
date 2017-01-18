package com.dyn.achievements.proxy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTPClient;

import com.dyn.DYNServerMod;
import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.Requirements;
import com.dyn.achievements.achievement.Requirements.BreakRequirement;
import com.dyn.achievements.achievement.Requirements.LocationRequirement;
import com.dyn.achievements.achievement.Requirements.PickupRequirement;
import com.dyn.achievements.handlers.AchievementManager;
import com.dyn.achievements.handlers.EventHandler;
import com.dyn.server.database.DBManager;
import com.dyn.server.keys.KeyManager;
import com.dyn.utils.FileUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Server implements Proxy {

	@Override
	public void init() {
		MinecraftForge.EVENT_BUS.register(new EventHandler());

		try {
			DYNServerMod.logger.info("Initializing Achievements");

			JsonObject achievementJson = DBManager.getAchievementDBAsJson();

			File achievementFile = new File(MinecraftServer.getServer().getDataDirectory(),
					"current_achievements_" + LocalDate.now() + ".json");
			FileUtils.createJsonFile(achievementJson, achievementFile);

			if (!DYNServerMod.developmentEnvironment && DYNServerMod.apacheNetLoaded
					&& DYNServerMod.ftpConnectionOpen) {
				// upload to ftp, thread it so it doesnt block
				FMLCommonHandler.instance().getMinecraftServerInstance().addScheduledTask(() -> {
					FTPClient client = new FTPClient();
					client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out)));
					FileInputStream fis = null;

					try {
						DYNServerMod.logger.info("Connecting to FTP");
						client.connect(KeyManager.getFtpKeys().getLeft());
						DYNServerMod.logger.info("Logging in to FTP");
						if (client.login(KeyManager.getFtpKeys().getMiddle(), KeyManager.getFtpKeys().getRight())) {

							// Create an InputStream of the file to be uploaded
							fis = new FileInputStream(achievementFile);
							// Store file to server
							DYNServerMod.logger.info("Uploading " + achievementFile.getName()
									+ " to FTP server at /Minecraft/" + achievementFile.getName());

							if (!client.storeFile("/Minecraft/" + achievementFile.getName(), fis)) {
								DYNServerMod.logger.error("Failed to upload file");
							} else {
								DYNServerMod.logger.info("Deleting old file");
								client.deleteFile("/Minecraft/current_achievements.json");
								DYNServerMod.logger.info("Renaming to current_achievements.json");
								client.rename("/Minecraft/" + achievementFile.getName(),
										"/Minecraft/current_achievements.json");
							}
							fis.close();
						} else {
							DYNServerMod.logger.error("Could not log in to ftp client");
						}
					} catch (IOException e) {
						DYNServerMod.logger.error("Failed during achievement upload", e);
					} finally {
						try {
							if (fis != null) {
								fis.close();
							}
							client.disconnect();
						} catch (IOException e) {
							DYNServerMod.logger.error("Failed tring to close stream or ftp client", e);
						}
					}
				});
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