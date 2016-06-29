package com.dyn.achievements.handlers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.RequirementType;
import com.dyn.achievements.achievement.Requirements;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/***
 * An event handler class for achievements.
 *
 * @author Dominic Amato
 *
 */
public class AchievementManager {

	private static Map<String, AchievementPage> achievementPageTextures = new HashMap<String, AchievementPage>();
	private static List<AchievementPlus> achievements = new ArrayList<AchievementPlus>();
	private static Map<String, AchievementPlus> achievementNames = new HashMap<String, AchievementPlus>();
	private static Map<Integer, AchievementPlus> achievementIds = new HashMap<Integer, AchievementPlus>();
	private static Map<RequirementType, List<AchievementPlus>> achievementsType = new HashMap<RequirementType, List<AchievementPlus>>();
	private static Map<RequirementType, ListMultimap<String, AchievementPlus>> entityNames = new HashMap<RequirementType, ListMultimap<String, AchievementPlus>>();
	private static Map<EntityPlayer, Map<String, Requirements>> playerAchievements = new HashMap<EntityPlayer, Map<String, Requirements>>();

	/***
	 * Adds page of achievements.
	 *
	 * @param pageName
	 *            Name of achievement page
	 * @param achievements
	 *            ArrayList of achievements
	 */
	public static void addAchievementPage(String pageName, int id) {
		List<AchievementPlus> achievementsInMap = new ArrayList<AchievementPlus>();
		for (AchievementPlus achievement : AchievementManager.getAllAchievements()) {
			if (achievement.getMapId() == id) {
				achievementsInMap.add(achievement);
			}
		}
		if (achievementsInMap.size() > 0) {
			AchievementPage page = new AchievementPage(pageName,
					achievementsInMap.toArray(new Achievement[achievements.size()]));
			AchievementPage.registerAchievementPage(page);
			achievementPageTextures.put(pageName, page);
		}
	}

	public static AchievementPlus findAchievementById(int id) {
		return achievementIds.get(id);
	}

	/***
	 * Finds achievement by given name.
	 *
	 * @param name
	 *            String of achievement name
	 * @return achievement object
	 */
	public static AchievementPlus findAchievementByName(String name) {
		return achievementNames.get(name);
	}

	/***
	 * Find all achievements of given type.
	 *
	 * @param type
	 *            AchievementType object
	 * @return ArrayList of achievements
	 */
	public static List<AchievementPlus> findAchievementByType(RequirementType type) {
		return achievementsType.get(type);
	}

	/***
	 * Finds achievements by name.
	 *
	 * @param name
	 *            String of achievement name
	 * @return list of achievement objects containing name
	 */
	public static List<AchievementPlus> findAchievementsByName(String name) {
		List<AchievementPlus> achList = new ArrayList<AchievementPlus>();
		for (AchievementPlus achs : achievementNames.values()) {
			if (achs.getName().toLowerCase().contains(name.toLowerCase())) {
				achList.add(achs);
			}
		}
		return achList;
	}

	/***
	 * Gets list of all achievements.
	 *
	 * @return ArrayList of achievements
	 */
	public static List<AchievementPlus> getAllAchievements() {
		return achievements;
	}

	@SideOnly(Side.SERVER)
	public static Map<String, Requirements> getPlayerAchievementProgress(String username) {
		for (EntityPlayer p : playerAchievements.keySet()) {
			if (p.getDisplayNameString().equals(username)) {
				return playerAchievements.get(p);
			}
		}
		return null;
	}

	@SideOnly(Side.SERVER)
	public static boolean getPlayersAchievementsRequirementStatus(EntityPlayer player, AchievementPlus ach,
			BaseRequirement req) {
		try {
			EntityPlayer keyPlayer = null;
			for (EntityPlayer p : playerAchievements.keySet()) {
				if (p.getDisplayNameString().equals(player.getDisplayNameString())) {
					keyPlayer = p;
				}
			}
			Requirements reqs = playerAchievements.get(keyPlayer).get(ach.getName());
			if (reqs != null) {
				ArrayList<BaseRequirement> achReqs = reqs.getRequirements();
				for (int j = 0; j < achReqs.size(); j++) {
					if (achReqs.get(j).equals(req)) {
						return achReqs.get(j).hasBeenMet();
					}
				}
			}
		} catch (NullPointerException e) {
			// this should be impossible but just incase...
			e.printStackTrace();
		}
		return false;
	}

	@SideOnly(Side.SERVER)
	public static boolean getPlayersAchievementsRequirementStatus(EntityPlayer player, AchievementPlus ach,
			RequirementType type, int req_id) {
		try {
			EntityPlayer keyPlayer = null;
			for (EntityPlayer p : playerAchievements.keySet()) {
				if (p.getDisplayNameString().equals(player.getDisplayNameString())) {
					keyPlayer = p;
				}
			}
			Requirements reqs = playerAchievements.get(keyPlayer).get(ach.getName());
			if (reqs != null) {
				ArrayList<BaseRequirement> achReqs = reqs.getRequirementsByType(type);
				for (int j = 0; j < achReqs.size(); j++) {
					if (achReqs.get(j).getRequirementID() == req_id) {
						return achReqs.get(j).hasBeenMet();
					}
				}
			}
		} catch (NullPointerException e) {
			// this should be impossible but just incase...
			e.printStackTrace();
		}
		return false;
	}

	public static Map<RequirementType, ListMultimap<String, AchievementPlus>> getRequirementEntityNames() {
		return entityNames;
	}

	@SideOnly(Side.SERVER)
	public static void incrementPlayersAchievementsTotal(EntityPlayer player, AchievementPlus ach,
			BaseRequirement req) {
		try {
			EntityPlayer keyPlayer = null;
			for (EntityPlayer p : playerAchievements.keySet()) {
				if (p.getDisplayNameString().equals(player.getDisplayNameString())) {
					keyPlayer = p;
				}
			}
			Requirements reqs = playerAchievements.get(keyPlayer).get(ach.getName());
			if (reqs != null) {
				ArrayList<BaseRequirement> achReqs = reqs.getRequirements();
				for (int j = 0; j < achReqs.size(); j++) {
					if (achReqs.get(j).equals(req)) {
						achReqs.get(j).incrementTotal();
					}
				}
			}
		} catch (NullPointerException e) {
			// this should be impossible but just incase
			e.printStackTrace();
		}

	}

	@SideOnly(Side.SERVER)
	public static void incrementPlayersAchievementsTotal(EntityPlayer player, AchievementPlus ach, RequirementType type,
			int req_id) {
		try {
			EntityPlayer keyPlayer = null;
			for (EntityPlayer p : playerAchievements.keySet()) {
				if (p.getDisplayNameString().equals(player.getDisplayNameString())) {
					keyPlayer = p;
				}
			}
			Requirements reqs = playerAchievements.get(keyPlayer).get(ach.getName());
			if (reqs != null) {
				ArrayList<BaseRequirement> achReqs = reqs.getRequirementsByType(type);
				for (int j = 0; j < achReqs.size(); j++) {
					if (achReqs.get(j).getRequirementID() == req_id) {
						achReqs.get(j).incrementTotal();
					}
				}
			}
		} catch (NullPointerException e) {
			// this should be impossible but just incase
			e.printStackTrace();
		}

	}

	private static void parseRequirementEntityNames(AchievementPlus achievement) {
		boolean[] vals = achievement.getRequirements().getRequirementTypes();
		if (vals[0]) {
			if (entityNames.get(RequirementType.CRAFT) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(RequirementType.CRAFT, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.CRAFT)) {
				entityNames.get(RequirementType.CRAFT).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[1]) {
			if (entityNames.get(RequirementType.SMELT) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(RequirementType.SMELT, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.SMELT)) {
				entityNames.get(RequirementType.SMELT).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[2]) {
			if (entityNames.get(RequirementType.PICKUP) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(RequirementType.PICKUP, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.PICKUP)) {
				entityNames.get(RequirementType.PICKUP).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[4]) {
			if (entityNames.get(RequirementType.KILL) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(RequirementType.KILL, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.KILL)) {
				entityNames.get(RequirementType.KILL).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[5]) {
			if (entityNames.get(RequirementType.BREW) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(RequirementType.BREW, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.BREW)) {
				entityNames.get(RequirementType.BREW).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[6]) {
			if (entityNames.get(RequirementType.PLACE) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(RequirementType.PLACE, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.PLACE)) {
				entityNames.get(RequirementType.PLACE).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[7]) {
			if (entityNames.get(RequirementType.BREAK) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(RequirementType.BREAK, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.BREAK)) {
				entityNames.get(RequirementType.BREAK).put(r.getRequirementEntityName(), achievement);
			}
		}
	}

	/***
	 * Registers Achievements. Add achievement to achievements ArrayList. Put
	 * achievement and name in achievementName HashMap.
	 *
	 * @see registerAchievementRequirementTypes
	 * @param achievement
	 *            of type AchievementPlus
	 */
	public static void registerAchievement(AchievementPlus achievement) {

		achievements.add(achievement);

		achievementNames.put(achievement.getName(), achievement);
		achievementIds.put(achievement.getId(), achievement);
		achievement.registerStat();

		registerAchievementRequirementTypes(achievement);
		parseRequirementEntityNames(achievement);
	}

	/***
	 * Registers achievement by CRAFT, SMELT, PICKUP, and STAT type.
	 *
	 * @param achievement
	 *            AchievementPlus object
	 */
	private static void registerAchievementRequirementTypes(AchievementPlus achievement) {
		/** < if the key doesn't exist make it */
		boolean[] vals = achievement.getRequirements().getRequirementTypes();
		if (vals[0]) {
			if (achievementsType.get(RequirementType.CRAFT) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.CRAFT, ach);
			}
			achievementsType.get(RequirementType.CRAFT).add(achievement);
		}
		if (vals[1]) {
			if (achievementsType.get(RequirementType.SMELT) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.SMELT, ach);
			}
			achievementsType.get(RequirementType.SMELT).add(achievement);
		}
		if (vals[2]) {
			if (achievementsType.get(RequirementType.PICKUP) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.PICKUP, ach);
			}
			achievementsType.get(RequirementType.PICKUP).add(achievement);
		}
		if (vals[3]) {
			if (achievementsType.get(RequirementType.STAT) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.STAT, ach);
			}
			achievementsType.get(RequirementType.STAT).add(achievement);
		}
		if (vals[4]) {
			if (achievementsType.get(RequirementType.KILL) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.KILL, ach);
			}
			achievementsType.get(RequirementType.KILL).add(achievement);
		}
		if (vals[5]) {
			if (achievementsType.get(RequirementType.BREW) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.BREW, ach);
			}
			achievementsType.get(RequirementType.BREW).add(achievement);
		}
		if (vals[6]) {
			if (achievementsType.get(RequirementType.PLACE) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.PLACE, ach);
			}
			achievementsType.get(RequirementType.PLACE).add(achievement);
		}
		if (vals[7]) {
			if (achievementsType.get(RequirementType.BREAK) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.BREAK, ach);
			}
			achievementsType.get(RequirementType.BREAK).add(achievement);
		}
		if (vals[8]) {
			if (achievementsType.get(RequirementType.MENTOR) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.MENTOR, ach);
			}
			achievementsType.get(RequirementType.MENTOR).add(achievement);
		}
		if (vals[9]) {
			if (achievementsType.get(RequirementType.LOCATION) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(RequirementType.LOCATION, ach);
			}
			achievementsType.get(RequirementType.LOCATION).add(achievement);
		}
	}

	@SideOnly(Side.SERVER)
	public static void setPlayersAchievementsTotal(EntityPlayer player, AchievementPlus ach, BaseRequirement req,
			int amount) {
		try {
			EntityPlayer keyPlayer = null;
			for (EntityPlayer p : playerAchievements.keySet()) {
				if (p.getDisplayNameString().equals(player.getDisplayNameString())) {
					keyPlayer = p;
				}
			}
			Requirements reqs = playerAchievements.get(keyPlayer).get(ach.getName());
			if (reqs != null) {
				ArrayList<BaseRequirement> achReqs = reqs.getRequirements();
				for (int j = 0; j < achReqs.size(); j++) {
					if (achReqs.get(j).equals(req)) {
						achReqs.get(j).setAquiredTo(amount);
					}
				}
			}
		} catch (NullPointerException e) {
			// this should be impossible but just incase
			e.printStackTrace();
		}

	}

	@SideOnly(Side.SERVER)
	public static void setPlayersAchievementsTotal(EntityPlayer player, AchievementPlus ach, RequirementType type,
			int req_id, int amount) {
		try {
			EntityPlayer keyPlayer = null;
			for (EntityPlayer p : playerAchievements.keySet()) {
				if (p.getDisplayNameString().equals(player.getDisplayNameString())) {
					keyPlayer = p;
				}
			}
			Requirements reqs = playerAchievements.get(keyPlayer).get(ach.getName());
			if (reqs != null) {
				ArrayList<BaseRequirement> achReqs = reqs.getRequirementsByType(type);
				for (int j = 0; j < achReqs.size(); j++) {
					if (achReqs.get(j).getRequirementID() == req_id) {
						achReqs.get(j).setAquiredTo(amount);
					}
				}
			}
		} catch (NullPointerException e) {
			// this should be impossible but just incase
			e.printStackTrace();
		}

	}

	@SideOnly(Side.SERVER)
	public static void setupPlayerAchievements(EntityPlayer player) {
		if (playerAchievements.containsKey(player)) {
			return;
		} else {
			for (EntityPlayer p : playerAchievements.keySet()) {
				if (p.getDisplayNameString().equals(player.getDisplayNameString())) {
					return;
				}
			}
			Map<String, Requirements> map = new HashMap<String, Requirements>();
			for (AchievementPlus achs : getAllAchievements()) {
				map.put(achs.getName(), Requirements.getCopy(achs.getRequirements()));
			}
			playerAchievements.put(player, map);
		}
	}

	@SideOnly(Side.SERVER)
	public static void writeOutPlayerAchievements() {
		ServerConfigurationManager configMan = MinecraftServer.getServer().getConfigurationManager();
		for (EntityPlayer keys : playerAchievements.keySet()) {
			File out = new File(MinecraftServer.getServer().getFolderName() + "/Achievements/",
					keys.getDisplayNameString() + ".txt");
			out.mkdirs();
			if (out.exists()) {
				out.delete();
			}
			try {
				out.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(out))) {
				for (String achKeys : playerAchievements.get(keys).keySet()) {
					writer.write(achKeys);
					writer.newLine();
					writer.write("Unlocked: " + configMan.getPlayerStatsFile(keys)
							.hasAchievementUnlocked(findAchievementByName(achKeys)));
					writer.newLine();
					Requirements reqs = playerAchievements.get(keys).get(achKeys);
					boolean[] types = reqs.getRequirementTypes();
					for (int i = 0; i < types.length; i++) {
						switch (i) {
						case 0:
							if (types[i]) {
								writer.write("\tcraft_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(RequirementType.CRAFT);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\titem " + t.getRequirementEntityName());
									writer.newLine();
									writer.write("\t\tamount " + t.getTotalNeeded());
									writer.newLine();
									writer.write("\t\ttotal " + t.getTotalAquired());
									writer.newLine();
								}
							}
							break;
						case 1:
							if (types[i]) {
								writer.write("\tsmelt_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(RequirementType.SMELT);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\titem " + t.getRequirementEntityName());
									writer.newLine();
									writer.write("\t\tamount " + t.getTotalNeeded());
									writer.newLine();
									writer.write("\t\ttotal " + t.getTotalAquired());
									writer.newLine();
								}
							}
							break;
						case 2:
							if (types[i]) {
								writer.write("\tpick_up_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(RequirementType.PICKUP);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\titem " + t.getRequirementEntityName());
									writer.newLine();
									writer.write("\t\tamount " + t.getTotalNeeded());
									writer.newLine();
									writer.write("\t\ttotal " + t.getTotalAquired());
									writer.newLine();
								}
							}
							break;
						case 3:
							if (types[i]) {
								writer.write("\tstat_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(RequirementType.STAT);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\titem " + t.getRequirementEntityName());
									writer.newLine();
									writer.write("\t\tamount " + t.getTotalNeeded());
									writer.newLine();
									writer.write("\t\ttotal " + t.getTotalAquired());
									writer.newLine();
								}
							}
							break;
						case 4:
							if (types[i]) {
								writer.write("\tkill_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(RequirementType.KILL);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\titem " + t.getRequirementEntityName());
									writer.newLine();
									writer.write("\t\tamount " + t.getTotalNeeded());
									writer.newLine();
									writer.write("\t\ttotal " + t.getTotalAquired());
									writer.newLine();
								}
							}
							break;
						case 5:
							if (types[i]) {
								writer.write("\tbrew_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(RequirementType.BREW);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\titem " + t.getRequirementEntityName());
									writer.newLine();
									writer.write("\t\tamount " + t.getTotalNeeded());
									writer.newLine();
									writer.write("\t\ttotal " + t.getTotalAquired());
									writer.newLine();
								}
							}
							break;
						case 6:
							if (types[i]) {
								writer.write("\tplace_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(RequirementType.PLACE);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\titem " + t.getRequirementEntityName());
									writer.newLine();
									writer.write("\t\tamount " + t.getTotalNeeded());
									writer.newLine();
									writer.write("\t\ttotal " + t.getTotalAquired());
									writer.newLine();
								}
							}
							break;
						case 7:
							if (types[i]) {
								writer.write("\tbreak_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(RequirementType.BREAK);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\titem " + t.getRequirementEntityName());
									writer.newLine();
									writer.write("\t\tamount " + t.getTotalNeeded());
									writer.newLine();
									writer.write("\t\ttotal " + t.getTotalAquired());
									writer.newLine();
								}
							}
							break;
						case 8:
							if (types[i]) {
								writer.write("\tmentor_requirements");
								writer.newLine();
							}
							break;
						case 9:
							if (types[i]) {
								writer.write("\tlocation_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs
										.getRequirementsByType(RequirementType.LOCATION);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\tname " + t.getRequirementEntityName()
											+ (t.getTotalAquired() > 0 ? "-[X]" : "-[ ]"));
									writer.newLine();
								}
							}
							break;
						default:
							break;
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}