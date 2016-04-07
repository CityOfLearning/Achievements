package com.dyn.achievements.achievement;

import java.util.ArrayList;

import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.dyn.achievements.achievement.Requirements.BreakRequirement;
import com.dyn.achievements.achievement.Requirements.BrewRequirement;
import com.dyn.achievements.achievement.Requirements.CraftRequirement;
import com.dyn.achievements.achievement.Requirements.KillRequirement;
import com.dyn.achievements.achievement.Requirements.LocationRequirement;
import com.dyn.achievements.achievement.Requirements.MentorRequirement;
import com.dyn.achievements.achievement.Requirements.PickupRequirement;
import com.dyn.achievements.achievement.Requirements.PlaceRequirement;
import com.dyn.achievements.achievement.Requirements.SmeltRequirement;
import com.dyn.achievements.handlers.AchievementManager;
import com.dyn.login.LoginGUI;
import com.dyn.server.http.PostBadge;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

/***
 * AchievementPlus class modifies Achievement class in MineCraft source code.
 *
 * @author Dominic Amato
 *
 */
public class AchievementPlus extends Achievement {

	public static AchievementPlus JsonToAchievement(JsonObject json) {
		Requirements requirements = new Requirements();

		// optional but needed to award a badge online;
		int badgeId = 0;
		String parentName = "";
		ResourceLocation texture = null;
		boolean awarded = false;
		try {
			String name = json.get("name").getAsString();
			String desc = json.get("desc").getAsString();
			int achId = json.get("ach_id").getAsInt();
			int mapId = json.get("map_id").getAsInt();
			int worldId = json.get("world").getAsInt();
			int xCoord = json.get("x_coord").getAsInt();
			int yCoord = json.get("y_coord").getAsInt();

			JsonObject req = (JsonObject) json.get("requirements");
			if (req.has("craft_requirements")) {
				JsonArray reqType = req.get("craft_requirements").getAsJsonArray();
				for (JsonElement jElement : reqType) {
					JsonObject reqSubType = jElement.getAsJsonObject();
					CraftRequirement r = requirements.new CraftRequirement();
					r.setFromItemId(reqSubType.get("item_id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setRequirementId(reqSubType.get("id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if (req.has("smelt_requirements")) {
				JsonArray reqType = req.get("smelt_requirements").getAsJsonArray();
				for (JsonElement jElement : reqType) {
					JsonObject reqSubType = jElement.getAsJsonObject();
					SmeltRequirement r = requirements.new SmeltRequirement();
					r.setFromItemId(reqSubType.get("item_id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setRequirementId(reqSubType.get("id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if (req.has("pick_up_requirements")) {
				JsonArray reqType = req.get("pick_up_requirements").getAsJsonArray();
				for (JsonElement jElement : reqType) {
					JsonObject reqSubType = jElement.getAsJsonObject();
					PickupRequirement r = requirements.new PickupRequirement();
					r.setFromItemId(reqSubType.get("item_id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setRequirementId(reqSubType.get("id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if (req.has("kill_requirements")) {
				JsonArray reqType = req.get("kill_requirements").getAsJsonArray();
				for (JsonElement jElement : reqType) {
					JsonObject reqSubType = jElement.getAsJsonObject();
					KillRequirement r = requirements.new KillRequirement();
					r.entityType = reqSubType.get("entity").getAsString();
					r.setRequirementId(reqSubType.get("id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if (req.has("brew_requirements")) {
				JsonArray reqType = req.get("brew_requirements").getAsJsonArray();
				for (JsonElement jElement : reqType) {
					JsonObject reqSubType = jElement.getAsJsonObject();
					BrewRequirement r = requirements.new BrewRequirement();
					r.setFromItemId(reqSubType.get("item_id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setRequirementId(reqSubType.get("id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if (req.has("place_requirements")) {
				JsonArray reqType = req.get("place_requirements").getAsJsonArray();
				for (JsonElement jElement : reqType) {
					JsonObject reqSubType = jElement.getAsJsonObject();
					PlaceRequirement r = requirements.new PlaceRequirement();
					r.setFromItemId(reqSubType.get("item_id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setRequirementId(reqSubType.get("id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if (req.has("break_requirements")) {
				JsonArray reqType = req.get("break_requirements").getAsJsonArray();
				for (JsonElement jElement : reqType) {
					JsonObject reqSubType = jElement.getAsJsonObject();
					BreakRequirement r = requirements.new BreakRequirement();
					r.setFromItemId(reqSubType.get("item_id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setRequirementId(reqSubType.get("id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if (req.has("location_requirements")) {
				JsonArray reqType = req.get("location_requirements").getAsJsonArray();
				for (JsonElement jElement : reqType) {
					JsonObject reqSubType = jElement.getAsJsonObject();
					LocationRequirement r = requirements.new LocationRequirement();
					r.setRequirementId(reqSubType.get("id").getAsInt());
					r.setAmountNeeded(1);
					r.name = reqSubType.get("name").getAsString();
					r.x1 = reqSubType.get("x").getAsInt();
					r.y1 = reqSubType.get("y").getAsInt();
					r.z1 = reqSubType.get("z").getAsInt();
					if (reqSubType.has("radius")) {
						r.r = reqSubType.get("radius").getAsInt();
					} else {
						r.x2 = reqSubType.get("x2").getAsInt();
						r.y2 = reqSubType.get("y2").getAsInt();
						r.z2 = reqSubType.get("z2").getAsInt();
					}

					requirements.addRequirement(r);
				}
			}
			if (req.has("mentor_requirements")) {
				// this should be an empty array...
				MentorRequirement r = requirements.new MentorRequirement();
				requirements.addRequirement(r);
			}
			if (json.has("badge_id")) {
				badgeId = json.get("badge_id").getAsInt();
			}
			if (json.has("parent_name")) {
				parentName = json.get("parent_name").getAsString();
			}
			if (json.has("texture")) {
				texture = new ResourceLocation(json.get("texture").getAsString());
			}
			return new AchievementPlus(requirements, name, desc, xCoord, yCoord, badgeId, achId, mapId, worldId,
					AchievementManager.findAchievementByName(parentName), awarded, texture);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private Requirements requirements;
	private String name;
	private String desc;
	private int ach_id;
	private int map_id;
	private int world_id;
	private AchievementPlus parent;
	private int xCoord;
	private int yCoord;
	private boolean awarded;

	private ResourceLocation texture;

	// optional but needed to award a badge online;
	private int badgeId;

	public AchievementPlus(Requirements requirements, String name, String description, int xPos, int yPos, int badgeId,
			int achievementId, int mapId, int worldId, AchievementPlus parent, boolean awarded,
			ResourceLocation texture) {
		super(name.replace(' ', '_'), name.replace(' ', '_'), xPos, yPos, new ItemStack(Items.experience_bottle),
				parent);
		LanguageRegistry.instance().addStringLocalization("achievement." + name.replace(' ', '_'), "en_US", name);
		LanguageRegistry.instance().addStringLocalization("achievement." + name.replace(' ', '_') + ".desc", "en_US",
				description);
		this.requirements = requirements;
		this.name = name;
		desc = description;
		this.badgeId = badgeId;
		this.awarded = awarded;
		if (awarded) {
			// Minecraft.getMinecraft().thePlayer.addStat(this, 1);
		}
		ach_id = achievementId;
		map_id = mapId;
		world_id = worldId;
		this.parent = parent;
		xCoord = xPos;
		yCoord = yPos;
		this.texture = texture;
		AchievementManager.registerAchievement(this);
	}

	public JsonObject achievementToJson() {
		JsonObject reply = new JsonObject();
		reply.addProperty("name", name);
		reply.addProperty("desc", desc);
		reply.addProperty("ach_id", ach_id);
		reply.addProperty("map_id", map_id);
		reply.addProperty("world", world_id);
		reply.addProperty("x_coord", xCoord);
		reply.addProperty("y_coord", yCoord);
		reply.addProperty("achieved", awarded);
		JsonObject req = new JsonObject();
		boolean[] types = requirements.getRequirementTypes();
		for (int i = 0; i < 8; i++) {
			JsonArray reqTypes = new JsonArray();
			switch (i) {
			case 0:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(RequirementType.CRAFT);
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("total", t.getTotalAquired());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqSubTypes.addProperty("item_id", t.getRequirementItemID());
						reqSubTypes.addProperty("sub_id", t.getRequirementSubItemID());
						reqTypes.add(reqSubTypes);
					}
					req.add("craft_requirements", reqTypes);
				}
				break;
			case 1:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(RequirementType.SMELT);
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("total", t.getTotalAquired());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqSubTypes.addProperty("item_id", t.getRequirementItemID());
						reqSubTypes.addProperty("sub_id", t.getRequirementSubItemID());
						reqTypes.add(reqSubTypes);
					}
					req.add("smelt_requirements", reqTypes);
				}
				break;
			case 2:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(RequirementType.PICKUP);
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("total", t.getTotalAquired());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqSubTypes.addProperty("item_id", t.getRequirementItemID());
						reqSubTypes.addProperty("sub_id", t.getRequirementSubItemID());
						reqTypes.add(reqSubTypes);
					}
					req.add("pick_up_requirements", reqTypes);
				}
				break;
			case 3:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(RequirementType.STAT);
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("stat", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("total", t.getTotalAquired());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqTypes.add(reqSubTypes);
					}
					req.add("stat_requirements", reqTypes);
				}
				break;
			case 4:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(RequirementType.KILL);
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("entity", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("total", t.getTotalAquired());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqTypes.add(reqSubTypes);
					}
					req.add("kill_requirements", reqTypes);
				}
				break;
			case 5:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(RequirementType.BREW);
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("total", t.getTotalAquired());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqSubTypes.addProperty("item_id", t.getRequirementItemID());
						reqSubTypes.addProperty("sub_id", t.getRequirementSubItemID());
						reqTypes.add(reqSubTypes);
					}
					req.add("brew_requirements", reqTypes);
				}
				break;
			case 6:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(RequirementType.PLACE);
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("total", t.getTotalAquired());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqSubTypes.addProperty("item_id", t.getRequirementItemID());
						reqSubTypes.addProperty("sub_id", t.getRequirementSubItemID());
						reqTypes.add(reqSubTypes);
					}
					req.add("place_requirements", reqTypes);
				}
				break;
			case 7:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(RequirementType.BREAK);
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("total", t.getTotalAquired());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqSubTypes.addProperty("item_id", t.getRequirementItemID());
						reqSubTypes.addProperty("sub_id", t.getRequirementSubItemID());
						reqTypes.add(reqSubTypes);
					}
					req.add("break_requirements", reqTypes);
				}
				break;
			case 8:
				if (types[i]) {
					requirements.getRequirementsByType(RequirementType.MENTOR);
					req.add("mentor_requirements", reqTypes);
				}
				break;
			case 9:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(RequirementType.LOCATION);
					for (BaseRequirement t : typeReq) {
						LocationRequirement lr = (LocationRequirement) t;
						JsonObject reqSubTypes = new JsonObject();
						requirements.new LocationRequirement();

						reqSubTypes.addProperty("name", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("total", t.getTotalAquired());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqSubTypes.addProperty("x", lr.x1);
						reqSubTypes.addProperty("y", lr.y1);
						reqSubTypes.addProperty("z", lr.z1);
						if (lr.r >= 0) {
							reqSubTypes.addProperty("radius", lr.r);
						} else {
							reqSubTypes.addProperty("x2", lr.x2);
							reqSubTypes.addProperty("y2", lr.y2);
							reqSubTypes.addProperty("z2", lr.z2);
						}
						reqTypes.add(reqSubTypes);
					}
					req.add("break_requirements", reqTypes);
				}
				break;
			default:
				break;
			}
		}
		reply.add("requirements", req);
		if (texture != null) {
			reply.addProperty("texture", texture.toString());
		}
		if (badgeId > 0) {
			reply.addProperty("badge_id", badgeId);
		}
		if (parent != null) {
			reply.addProperty("parent_name", parent.getName());
		}

		return reply;
	}

	/***
	 * Awards achievement to player.
	 *
	 * @param world
	 *            World
	 * @param player
	 *            EntityPlayer
	 * @param itemStack
	 *            ItemStack
	 */
	public void awardAchievement(EntityPlayer player) {
		if (!LoginGUI.DYN_Username.isEmpty()) {
			new PostBadge(badgeId, LoginGUI.DYN_Username,
					"5e4ae1a1ddce5d341bd5c0b6075d9491620c31aed80a901345fdf91fe1757ce1d8b67b99ccaf574198c99ca12c3d288ad07b022d5b70d1c72a3d728a7a27ce23",
					"dd10c3a735a29a9e8d46822aac0660555a25103c57fa5188b793944fd074f1b6", player, this);
		} else {
			awarded = true;
			player.addStat(this, 1);
		}
	}

	public void awardAchievement(EntityPlayer player, String dynUsername) {
		new PostBadge(badgeId, dynUsername,
				"5e4ae1a1ddce5d341bd5c0b6075d9491620c31aed80a901345fdf91fe1757ce1d8b67b99ccaf574198c99ca12c3d288ad07b022d5b70d1c72a3d728a7a27ce23",
				"dd10c3a735a29a9e8d46822aac0660555a25103c57fa5188b793944fd074f1b6", player, this);
	}

	@Override
	public String getDescription() {
		return desc;
	}

	public int getId() {
		return ach_id;
	}

	public int getMapId() {
		return map_id;
	}

	public String getName() {
		return name;
	}

	public AchievementPlus getParent() {
		return parent;
	}

	/***
	 * Get Requirements.
	 *
	 * @return requirements
	 */
	public Requirements getRequirements() {
		return requirements;
	}

	public ResourceLocation getTexture() {
		return texture;
	}

	public int getWorldId() {
		return world_id;
	}

	public boolean hasParent() {
		return parent != null;
	}

	/***
	 * If requirements of specified type exists it returns true, else false.
	 *
	 * @param type
	 *            AchievementType
	 * @return boolean
	 */
	public boolean hasRequirementOfType(RequirementType type) {
		return requirements.getRequirementsByType(type).size() > 0;
	}

	public boolean isAwarded() {
		return awarded;
	}

	public boolean meetsRequirements() {
		for (BaseRequirement r : requirements.getRequirements()) {
			if (r.getTotalAquired() < r.getTotalNeeded()) {
				return false;
			}
		}
		return true;
	}

	// if this is the client it doesnt matter if we add the stat we just need to
	// know that its been achieved
	public void setAwarded() {
		awarded = true;
	}

	public void setAwarded(EntityPlayer player) {
		awarded = true;
		player.addStat(this, 1);
	}

	public void setTexture(ResourceLocation tex) {
		texture = tex;
	}

	public void setWorldId(int id) {
		world_id = id;
	}
}