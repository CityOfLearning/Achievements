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
import com.dyn.achievements.achievement.AchievementType;
import com.dyn.achievements.achievement.Requirements;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.dyn.achievements.achievement.Requirements.LocationRequirement;
import com.dyn.betterachievements.registry.AchievementRegistry;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagList;
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
public class AchievementHandler {

	private static Map<String, AchievementPage> achievementPages = new HashMap<String, AchievementPage>();
	private static ArrayList<AchievementPlus> achievements = new ArrayList<AchievementPlus>();
	private static Map<String, AchievementPlus> achievementNames = new HashMap<String, AchievementPlus>();
	private static Map<Integer, AchievementPlus> achievementIds = new HashMap<Integer, AchievementPlus>();
	//private static Map<Integer, List<AchievementPlus>> locationDims = new HashMap<Integer, List<AchievementPlus>>();
	private static Map<Integer, AchievementMap> achievementMaps = new HashMap<Integer, AchievementMap>();
	private static Map<AchievementType, ArrayList<AchievementPlus>> achievementsType = new HashMap<AchievementType, ArrayList<AchievementPlus>>();
	private static Map<AchievementType, ListMultimap<String, AchievementPlus>> itemNames = new HashMap<AchievementType, ListMultimap<String, AchievementPlus>>();
	private static Map<AchievementType, ListMultimap<String, AchievementPlus>> entityNames = new HashMap<AchievementType, ListMultimap<String, AchievementPlus>>();
	private static Map<EntityPlayer, Map<String, Requirements>> playerAchievements = new HashMap<EntityPlayer, Map<String, Requirements>>();

	@SideOnly(Side.SERVER)
	public static Map<String, Requirements> getPlayerAchievementProgress(String username){
		for(EntityPlayer p : playerAchievements.keySet()){
			if(p.getDisplayNameString().equals(username)){
				return playerAchievements.get(p);
			}
		}
		return null;
	}
	
	@SideOnly(Side.SERVER)
	public static void setupPlayerAchievements(EntityPlayer player) {
		if (playerAchievements.containsKey(player)) {
			return;
		} else {
			for(EntityPlayer p : playerAchievements.keySet()){
				if(p.getDisplayNameString().equals(player.getDisplayNameString())){
					return;
				}
			}
			Map<String, Requirements> map = new HashMap<String, Requirements>();
			for(AchievementPlus achs : getAllAchievements()){
				map.put(achs.getName(), Requirements.getCopy(achs.getRequirements()));
			}
			playerAchievements.put(player, map);	
		}
	}

	@SideOnly(Side.SERVER)
	public static void incrementPlayersAchievementsTotal(EntityPlayer player, AchievementPlus ach,
			BaseRequirement req) {
		try{
			EntityPlayer keyPlayer = null;
			for(EntityPlayer p : playerAchievements.keySet()){
				if(p.getDisplayNameString().equals(player.getDisplayNameString())){
					keyPlayer = p;
				}
			}
			Requirements reqs = playerAchievements.get(keyPlayer).get(ach.getName());
			if(reqs != null){
				ArrayList<BaseRequirement> achReqs = reqs.getRequirements();
				for (int j = 0; j < achReqs.size(); j++) {
					if (achReqs.get(j).equals(req)) {
						System.out.println("Incrementing Total for " + ach.getName());
						achReqs.get(j).incrementTotal();
					}
				}
			}
		} catch(NullPointerException e){
			//this should be impossible but just incase
			e.printStackTrace();
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(out))) {
				for (String achKeys : playerAchievements.get(keys).keySet()) {
					writer.write(achKeys);
					writer.newLine();
					writer.write("Unlocked: " + configMan.getPlayerStatsFile(keys).hasAchievementUnlocked(findAchievementByName(achKeys)));
					writer.newLine();
					Requirements reqs = playerAchievements.get(keys).get(achKeys);
					boolean[] types = reqs.getRequirementTypes();
					for (int i = 0; i < 8; i++) {
						switch (i) {
						case 0:
							if (types[i]) {
								writer.write("\tcraft_requirements");
								writer.newLine();
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(AchievementType.CRAFT);
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
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(AchievementType.SMELT);
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
								ArrayList<BaseRequirement> typeReq = reqs
										.getRequirementsByType(AchievementType.PICKUP);
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
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(AchievementType.STAT);
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
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(AchievementType.KILL);
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
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(AchievementType.BREW);
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
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(AchievementType.PLACE);
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
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(AchievementType.BREAK);
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
								ArrayList<BaseRequirement> typeReq = reqs.getRequirementsByType(AchievementType.LOCATION);
								for (BaseRequirement t : typeReq) {
									writer.write("\t\tname " + t.getRequirementEntityName() + (t.getTotalAquired() > 0 ? "-[X]" : "-[ ]"));
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

	/***
	 * Adds page of achievements.
	 *
	 * @param pageName
	 *            Name of achievement page
	 * @param achievements
	 *            ArrayList of achievements
	 */
	public static void addAchievementPage(String pageName, ArrayList<AchievementPlus> achievements) {
		if (achievements.size() > 0) {
			AchievementPage achievementPage = new AchievementPage(pageName,
					achievements.toArray(new Achievement[achievements.size()]));
			AchievementPage.registerAchievementPage(achievementPage);

			achievementPages.put(pageName, achievementPage);
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
	public static ArrayList<AchievementPlus> findAchievementByType(AchievementType type) {
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
	public static ArrayList<AchievementPlus> getAllAchievements() {
		return achievements;
	}

	public static Map<AchievementType, ListMultimap<String, AchievementPlus>> getEntityNames() {
		return entityNames;
	}

	public static Map<AchievementType, ListMultimap<String, AchievementPlus>> getItemNames() {
		return itemNames;
	}

	private static void parseRequirementEntityNames(AchievementPlus achievement) {
		boolean[] vals = achievement.getRequirements().getRequirementTypes();
		if (vals[4]) {
			if (entityNames.get(AchievementType.KILL) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(AchievementType.KILL, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.KILL)) {
				entityNames.get(AchievementType.KILL).put(r.getRequirementEntityName(), achievement);
			}
		}
	}

	private static void parseRequirementItemNames(AchievementPlus achievement) {
		boolean[] vals = achievement.getRequirements().getRequirementTypes();
		if (vals[0]) {
			if (itemNames.get(AchievementType.CRAFT) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				itemNames.put(AchievementType.CRAFT, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.CRAFT)) {
				itemNames.get(AchievementType.CRAFT).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[1]) {
			if (itemNames.get(AchievementType.SMELT) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				itemNames.put(AchievementType.SMELT, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.SMELT)) {
				itemNames.get(AchievementType.SMELT).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[2]) {
			if (itemNames.get(AchievementType.PICKUP) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				itemNames.put(AchievementType.PICKUP, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.PICKUP)) {
				itemNames.get(AchievementType.PICKUP).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[5]) {
			if (itemNames.get(AchievementType.BREW) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				itemNames.put(AchievementType.BREW, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.BREW)) {
				itemNames.get(AchievementType.BREW).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[6]) {
			if (itemNames.get(AchievementType.PLACE) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				itemNames.put(AchievementType.PLACE, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.PLACE)) {
				itemNames.get(AchievementType.PLACE).put(r.getRequirementEntityName(), achievement);
			}
		}
		if (vals[7]) {
			if (itemNames.get(AchievementType.BREAK) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				itemNames.put(AchievementType.BREAK, map);
			}
			for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.BREAK)) {
				itemNames.get(AchievementType.BREAK).put(r.getRequirementEntityName(), achievement);
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

		// we shouldnt crash from this
		/*
		 * if (achievementsName.get(achievement.getName()) != null) { throw new
		 * RuntimeException("The achievement with the name " +
		 * achievement.getName() + " already exists!"); }
		 */
		achievementNames.put(achievement.getName(), achievement);
		achievementIds.put(achievement.getId(), achievement);
		achievement.registerStat();

		registerAchievementRequirementTypes(achievement);
		parseRequirementItemNames(achievement);
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
			if (achievementsType.get(AchievementType.CRAFT) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.CRAFT, ach);
			}
			achievementsType.get(AchievementType.CRAFT).add(achievement);
		}
		if (vals[1]) {
			if (achievementsType.get(AchievementType.SMELT) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.SMELT, ach);
			}
			achievementsType.get(AchievementType.SMELT).add(achievement);
		}
		if (vals[2]) {
			if (achievementsType.get(AchievementType.PICKUP) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.PICKUP, ach);
			}
			achievementsType.get(AchievementType.PICKUP).add(achievement);
		}
		if (vals[3]) {
			if (achievementsType.get(AchievementType.STAT) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.STAT, ach);
			}
			achievementsType.get(AchievementType.STAT).add(achievement);
		}
		if (vals[4]) {
			if (achievementsType.get(AchievementType.KILL) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.KILL, ach);
			}
			achievementsType.get(AchievementType.KILL).add(achievement);
		}
		if (vals[5]) {
			if (achievementsType.get(AchievementType.BREW) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.BREW, ach);
			}
			achievementsType.get(AchievementType.BREW).add(achievement);
		}
		if (vals[6]) {
			if (achievementsType.get(AchievementType.PLACE) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.PLACE, ach);
			}
			achievementsType.get(AchievementType.PLACE).add(achievement);
		}
		if (vals[7]) {
			if (achievementsType.get(AchievementType.BREAK) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.BREAK, ach);
			}
			achievementsType.get(AchievementType.BREAK).add(achievement);
		}
		if (vals[8]) {
			if (achievementsType.get(AchievementType.MENTOR) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.MENTOR, ach);
			}
			achievementsType.get(AchievementType.MENTOR).add(achievement);
		}
		if (vals[9]) {
			if (achievementsType.get(AchievementType.LOCATION) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList<AchievementPlus>();
				achievementsType.put(AchievementType.LOCATION, ach);
			}
			achievementsType.get(AchievementType.LOCATION).add(achievement);
		}
	}

	public static void sortAndAssignMaps() {
		new ArrayList<Object>();
		Map<Integer, List<AchievementPlus>> mapMapping = new HashMap<Integer, List<AchievementPlus>>();
		List<Integer> ids = new ArrayList<Integer>();
		achievementMaps.clear();
		// we need all the possible map ids
		for (AchievementPlus a : achievements) {
			if (!ids.contains(a.getMapId())) {
				mapMapping.put(a.getMapId(), new ArrayList<AchievementPlus>());
				ids.add(a.getMapId());
			}
			mapMapping.get(a.getMapId()).add(a);
		} // map everything to a map id
		for (int id : ids) {
			achievementMaps.put(id, new AchievementMap(id, mapMapping.get(id)));
			achievementMaps.get(id).processMap();
		}
	}
}