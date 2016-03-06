package com.dyn.achievements.achievement;

import java.util.ArrayList;

import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.dyn.achievements.achievement.Requirements.BreakRequirement;
import com.dyn.achievements.achievement.Requirements.BrewRequirement;
import com.dyn.achievements.achievement.Requirements.CraftRequirement;
import com.dyn.achievements.achievement.Requirements.KillRequirement;
import com.dyn.achievements.achievement.Requirements.MentorRequirement;
import com.dyn.achievements.achievement.Requirements.PickupRequirement;
import com.dyn.achievements.achievement.Requirements.PlaceRequirement;
import com.dyn.achievements.achievement.Requirements.SmeltRequirement;
import com.dyn.achievements.handlers.AchievementHandler;
import com.dyn.login.LoginGUI;
import com.dyn.server.http.PostBadge;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ResourceLocation;

/**
 * AchievementPlus class modifies Achievement class in MineCraft source code.
 * @author Dominic Amato
 * @version 1.0
 * @since 2016-03-06
 */
public class AchievementPlus extends Achievement {

	/**
	 * Requirements for an achievement.
	 */
	private Requirements requirements;
	/**
	 * Name of achievement.
	 */
	private String name;
	/**
	 * Description of achievement.
	 */
	private String desc;
	/**
	 * ID for achievement map.
	 */
	private int ach_id;
	/**
	 * ID for map location.
	 */
	private int map_id;
	/**
	 * ID for world location.
	 */
	private int world_id;
	/**
	 * Parent of current achievement.
	 */
	private AchievementPlus parent;
	/**
	 * X location.
	 */
	private int xCoord;
	/**
	 * Y location.
	 */
	private int yCoord;
	/**
	 * Has the achievement been awarded.
	 */
	private boolean awarded;
	/**
	 * Texture (image) of achievement on map.
	 */
	private ResourceLocation texture;

	/**
	 * Optional but needed to award a badge online.
	 */
	private int badgeId;

	/**
	 * Constructor of AchievementPlus.
	 * @param requirements Requirements for achievement.
	 * @param name Name of achievement.
	 * @param description Description of achievement.
	 * @param xPos X position of achievement.
	 * @param yPos Y position of achievement.
	 * @param badgeId Badge ID of achievement.
	 * @param achievementId ID of achievement
	 * @param mapId Map ID of achievement.
	 * @param worldId World ID of achievement.
	 * @param parent Parent of achievement
	 * @param awarded Has it been awarded to player.
	 * @param texture Texture of achievement.
	 */
	public AchievementPlus(Requirements requirements, String name, String description, int xPos, int yPos, int badgeId,
			int achievementId, int mapId, int worldId, AchievementPlus parent, boolean awarded, ResourceLocation texture) {
		super(name.replace(' ', '_'), name.replace(' ', '_'), xPos, yPos, new ItemStack(Items.experience_bottle), parent);
		LanguageRegistry.instance().addStringLocalization("achievement." + name.replace(' ', '_'), "en_US", name);
		LanguageRegistry.instance().addStringLocalization("achievement." + name.replace(' ', '_') + ".desc", "en_US",
				description);
		this.requirements = requirements;
		this.name = name;
		this.desc = description;
		this.badgeId = badgeId;
		this.awarded = awarded;
		if(awarded){
			//Minecraft.getMinecraft().thePlayer.addStat(this, 1);
		}
		this.ach_id = achievementId;
		this.map_id = mapId;
		this.world_id = worldId;
		this.parent = parent;
		this.xCoord = xPos;
		this.yCoord = yPos;
		this.texture = texture;
		AchievementHandler.registerAchievement(this);
	}

	/**
	 * Get Requirements.
	 * 
	 * @return requirements
	 */
	public Requirements getRequirements() {
		return requirements;
	}
	
	/**
	 * Checks if achievement has parent.
	 * @return Returns true if the player has a parent and false if null.
	 */
	public boolean hasParent() {
		return this.parent != null;
	}
	
	/**
	 * Gets parent of achievement.
	 * @return Returns parent variable.
	 */
	public AchievementPlus getParent() {
		return parent;
	}
	
	/**
	 * Gets the texture achievement.
	 * @return Returns the texture variable.
	 */
	public ResourceLocation getTexture(){
		return texture;
	}
	
	/**
	 * Sets the texture of the achievement.
	 * @param tex Texture to set.
	 */
	public void setTexture(ResourceLocation tex){
		this.texture = tex;
	}

	/***
	 * If requirements of specified type exists it returns true, else false.
	 * 
	 * @param type
	 *            AchievementType
	 * @return boolean
	 */
	public boolean hasRequirementOfType(AchievementType type) {
		return requirements.getRequirementsByType(type).size() > 0;
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
		if(!LoginGUI.DYN_Username.isEmpty()){
			new PostBadge(badgeId, LoginGUI.DYN_Username, "5e4ae1a1ddce5d341bd5c0b6075d9491620c31aed80a901345fdf91fe1757ce1d8b67b99ccaf574198c99ca12c3d288ad07b022d5b70d1c72a3d728a7a27ce23", "dd10c3a735a29a9e8d46822aac0660555a25103c57fa5188b793944fd074f1b6", player, this);
		} else {
			awarded = true;
			player.addStat(this, 1);
		}
	}
	
	/**
	 * Awards achievement to player and uses DYN username.
	 * @param player
	 * @param dynUsername
	 */
	public void awardAchievement(EntityPlayer player, String dynUsername) {
			new PostBadge(badgeId, dynUsername, "5e4ae1a1ddce5d341bd5c0b6075d9491620c31aed80a901345fdf91fe1757ce1d8b67b99ccaf574198c99ca12c3d288ad07b022d5b70d1c72a3d728a7a27ce23", "dd10c3a735a29a9e8d46822aac0660555a25103c57fa5188b793944fd074f1b6", player, this);
	}

	/**
	 * Checks if the achievement has been awarded.
	 * @return Returns true if it has been awarded to the player and false if it hasn't.
	 */
	public boolean isAwarded() {
		return awarded;
	}

	/**
	 * Awards the achievement to the specified player.
	 * @param player
	 */
	public void setAwarded(EntityPlayer player) {
		awarded = true;
		player.addStat(this, 1);
	}
	
	/**
	 * If this is the client it doesn't matter if we add the stat we just need to know that its been achieved.
	 */
	public void setAwarded() {
		awarded = true;
	}

	/**
	 * Gets name of achievement.
	 * @return Returns name variable.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the description of the achievement.
	 * @return Returns description.
	 */
	@Override
	public String getDescription() {
		return this.desc;
	}

	/**
	 * Gets achievement ID.
	 * @return
	 */
	public int getId() {
		return this.ach_id;
	}

	/**
	 * Gets map ID.
	 * @return
	 */
	public int getMapId() {
		return this.map_id;
	}

	/**
	 * Gets world ID.
	 * @return
	 */
	public int getWorldId() {
		return this.world_id;
	}

	/**
	 * Sets world ID of achievement.
	 * @param id
	 */
	public void setWorldId(int id) {
		this.world_id = id;
	}

	/**
	 * Checks if an achievement has meet its requirements.
	 * @return
	 */
	public boolean meetsRequirements() {
		for (BaseRequirement r : requirements.getRequirements()) {
			if (r.getTotalAquired() < r.getTotalNeeded())
				return false;
		}
		return true;
	}

	/**
	 * Parses JSON file to achievements.
	 * @param json
	 * @return Returns the new achievement or null if it could not process the information.
	 */
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
				for(JsonElement jElement : reqType){
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
				for(JsonElement jElement : reqType){
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
				for(JsonElement jElement : reqType){
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
				int counter = 1;
				for(JsonElement jElement : reqType){
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
				int counter = 1;
				for(JsonElement jElement : reqType){
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
				int counter = 1;
				for(JsonElement jElement : reqType){
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
				int counter = 1;
				for(JsonElement jElement : reqType){
					JsonObject reqSubType = jElement.getAsJsonObject();
					BreakRequirement r = requirements.new BreakRequirement();
					r.setFromItemId(reqSubType.get("item_id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setRequirementId(reqSubType.get("id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if (req.has("mentor_requirements")) {
				//this should be an empty array...
					MentorRequirement r = requirements.new MentorRequirement();
					requirements.addRequirement(r);
			}
			if (json.has("badge_id"))
				badgeId = json.get("badge_id").getAsInt();
			if (json.has("parent_name")) {
				parentName = json.get("parent_name").getAsString();
			}
			if(json.has("texture")){
				texture = new ResourceLocation(json.get("texture").getAsString());
			}
			return new AchievementPlus(requirements, name, desc, xCoord, yCoord, badgeId, achId, mapId, worldId,
					AchievementHandler.findAchievementByName(parentName), awarded, texture);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Turns achievements back into JSON.
	 * @return Returns JSON reply.
	 */
	public JsonObject achievementToJson() {
		JsonObject reply = new JsonObject();
		reply.addProperty("name", this.name);
		reply.addProperty("desc", this.desc);
		reply.addProperty("ach_id", this.ach_id);
		reply.addProperty("map_id", this.map_id);
		reply.addProperty("world", this.world_id);
		reply.addProperty("x_coord", this.xCoord);
		reply.addProperty("y_coord", this.yCoord);
		JsonObject req = new JsonObject();
		boolean[] types = requirements.getRequirementTypes();
		for (int i = 0; i < 8; i++) {
			JsonArray reqTypes = new JsonArray();
			switch (i) {
			case 0:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.CRAFT);
					int counter = 1;
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
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
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.SMELT);
					int counter = 1;
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
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
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.PICKUP);
					int counter = 1;
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
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
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.STAT);
					int counter = 1;
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("stat", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqTypes.add(reqSubTypes);
					}
					req.add("stat_requirements", reqTypes);
				}
				break;
			case 4:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.KILL);
					int counter = 1;
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("entity", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("id", t.getRequirementID());
						reqTypes.add(reqSubTypes);
					}
					req.add("kill_requirements", reqTypes);
				}
				break;
			case 5:
				if (types[i]) {
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.BREW);
					int counter = 1;
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
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
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.PLACE);
					int counter = 1;
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
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
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.BREAK);
					int counter = 1;
					for (BaseRequirement t : typeReq) {
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
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
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.MENTOR);
					req.add("mentor_requirements", reqTypes);
				}
				break;
			default:
				break;
			}
		}
		reply.add("requirements", req);
		if (this.texture != null)
			reply.addProperty("texture", this.texture.toString());
		if (this.badgeId > 0)
			reply.addProperty("badge_id", this.badgeId);
		if (this.parent != null)
			reply.addProperty("parent_name", this.parent.getName());

		return reply;
	}
}
