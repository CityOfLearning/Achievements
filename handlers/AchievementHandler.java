package com.dyn.achievements.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.AchievementType;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

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
	private static Map<Integer, AchievementMap> achievementMaps = new HashMap<Integer, AchievementMap>();
	private static Map<AchievementType, ArrayList<AchievementPlus>> achievementsType = new HashMap<AchievementType, ArrayList<AchievementPlus>>();
	private static Map<AchievementType, ListMultimap<String, AchievementPlus>> itemNames = new HashMap<AchievementType, ListMultimap<String, AchievementPlus>>();
	private static Map<AchievementType, ListMultimap<String, AchievementPlus>> entityNames = new HashMap<AchievementType, ListMultimap<String, AchievementPlus>>();

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
			for (BaseRequirement r : achievement.getRequirements()
					.getRequirementsByType(AchievementType.PICKUP)) {
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