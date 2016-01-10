package com.dyn.achievements.achievement;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

import java.util.*;

import com.dyn.achievements.achievement.AchievementPlus.AchievementType;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

/***
 * An event handler class for achievements.
 * 
 * @author Dominic Amato
 *
 */
public class AchievementHandler {

	public static Map<String, AchievementPage> achievementPages = new HashMap();
	public static ArrayList<AchievementPlus> achievements = new ArrayList();
	public static Map<String, AchievementPlus> achievementsName = new HashMap();
	// currently achievements can have mixed requirements so this doesnt work
	public static Map<AchievementType, ArrayList<AchievementPlus>> achievementsType = new HashMap();
	public static Map<AchievementType, ListMultimap<Integer, AchievementPlus>> itemIds = new HashMap();
	public static Map<AchievementType, ListMultimap<String, AchievementPlus>> entityNames = new HashMap();
	/***
	 * Adds page of achievements.
	 * @param pageName Name of achievement page
	 * @param achievements ArrayList of achievements
	 */
	public static void addAchievementPage(String pageName, ArrayList<AchievementPlus> achievements) {
		if (achievements.size() > 0) {
			AchievementPage achievementPage = new AchievementPage(pageName,
					achievements.toArray(new Achievement[achievements.size()]));
			AchievementPage.registerAchievementPage(achievementPage);

			achievementPages.put(pageName, achievementPage);
		}
	}

	/***
	 * Gets list of all achievements.
	 * @return ArrayList of achievements
	 */
	public static ArrayList<AchievementPlus> getAllAchievements() {
		return achievements;
	}

	/***
	 * Finds achievement by given name.
	 * @param name String of achievement name
	 * @return achievement object
	 */
	public static AchievementPlus findAchievementByName(String name) {
		return achievementsName.get(name);
	}
	
	/***
	 * Finds achievements by name.
	 * @param name String of achievement name
	 * @return list of achievement objects containing name
	 */
	public static List<AchievementPlus> findAchievementsByName(String name) {
		List<AchievementPlus> achList = new ArrayList();
		for (AchievementPlus achs : achievementsName.values()) {
			if(achs.getName().contains(name)){
				achList.add(achs);
			}
		}
		return achList;
	}
	
	/***
	 * Find all achievements of given type.
	 * @param type AchievementType object
	 * @return ArrayList of achievements
	 */
	public static ArrayList<AchievementPlus> findAchievementByType(AchievementType type) {
		return achievementsType.get(type);
	}

	/***
	 * Registers achievement by CRAFT, SMELT, PICKUP, and STAT type.
	 * @param achievement AchievementPlus object
	 */
	private static void registerAchievementRequirementTypes(AchievementPlus achievement) {
		/**< if the key doesn't exist make it */
		boolean[] vals = achievement.getRequirements().getRequirementTypes();
		if (vals[0]) {
			if (achievementsType.get(AchievementType.CRAFT) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList();
				achievementsType.put(AchievementType.CRAFT, ach);
			}
			achievementsType.get(AchievementType.CRAFT).add(achievement);
		}
		if (vals[1]) {
			if (achievementsType.get(AchievementType.SMELT) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList();
				achievementsType.put(AchievementType.SMELT, ach);
			}
			achievementsType.get(AchievementType.SMELT).add(achievement);
		}
		if (vals[2]) {
			if (achievementsType.get(AchievementType.PICKUP) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList();
				achievementsType.put(AchievementType.PICKUP, ach);
			}
			achievementsType.get(AchievementType.PICKUP).add(achievement);
		}
		if (vals[3]) {
			if (achievementsType.get(AchievementType.STAT) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList();
				achievementsType.put(AchievementType.STAT, ach);
			}
			achievementsType.get(AchievementType.STAT).add(achievement);
		}
		if (vals[4]) {
			if (achievementsType.get(AchievementType.KILL) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList();
				achievementsType.put(AchievementType.KILL, ach);
			}
			achievementsType.get(AchievementType.KILL).add(achievement);
		}
		if (vals[5]) {
			if (achievementsType.get(AchievementType.SPAWN) == null) {
				ArrayList<AchievementPlus> ach = new ArrayList();
				achievementsType.put(AchievementType.SPAWN, ach);
			}
			achievementsType.get(AchievementType.SPAWN).add(achievement);
		}
	}
	
	private static void parseRequirementItemIds(AchievementPlus achievement){
		boolean[] vals = achievement.getRequirements().getRequirementTypes();
		if (vals[0]) {
			if (itemIds.get(AchievementType.CRAFT) == null) {
				ListMultimap<Integer, AchievementPlus> map = ArrayListMultimap.create();
				itemIds.put(AchievementType.CRAFT, map);
			}
			for(BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.CRAFT)){
				itemIds.get(AchievementType.CRAFT).put(r.getRequirementItemID(), achievement);
			}
		}
		if (vals[1]) {
			if (itemIds.get(AchievementType.SMELT) == null) {
				ListMultimap<Integer, AchievementPlus> map = ArrayListMultimap.create();
				itemIds.put(AchievementType.SMELT, map);
			}
			for(BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.SMELT)){
				itemIds.get(AchievementType.SMELT).put(r.getRequirementItemID(), achievement);
			}
		}
		if (vals[2]) {
			if (itemIds.get(AchievementType.PICKUP) == null) {
				ListMultimap<Integer, AchievementPlus> map = ArrayListMultimap.create();
				itemIds.put(AchievementType.PICKUP, map);
			}
			for(BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.PICKUP)){
				itemIds.get(AchievementType.PICKUP).put(r.getRequirementItemID(), achievement);
			}
		}
	}
	
	private static void parseRequirementEntityNames(AchievementPlus achievement){
		boolean[] vals = achievement.getRequirements().getRequirementTypes();
		if (vals[4]) {
			if (entityNames.get(AchievementType.KILL) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(AchievementType.KILL, map);
			}
			for(BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.KILL)){
				entityNames.get(AchievementType.KILL).put(r.getRequirementItemEntity(), achievement);
			}
		}
		if (vals[5]) {
			if (entityNames.get(AchievementType.SPAWN) == null) {
				ListMultimap<String, AchievementPlus> map = ArrayListMultimap.create();
				entityNames.put(AchievementType.SPAWN, map);
			}
			for(BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.SPAWN)){
				entityNames.get(AchievementType.SPAWN).put(r.getRequirementItemEntity(), achievement);
			}
		}
	}

	/***
	 * Registers Achievements.
	 * Add achievement to achievements ArrayList.
	 * Put achievement and name in achievementName HashMap.
	 * @see registerAchievementRequirementTypes
	 * @param achievement of type AchievementPlus
	 */
	public static void registerAchievement(AchievementPlus achievement) {

		achievements.add(achievement);

		if (achievementsName.get(achievement.getName()) != null) {
			throw new RuntimeException("The achievement with the name " + achievement.getName() + " already exists!");
		}
		achievementsName.put(achievement.getName(), achievement);

		registerAchievementRequirementTypes(achievement);
		parseRequirementItemIds(achievement);
		parseRequirementEntityNames(achievement);
	}
}