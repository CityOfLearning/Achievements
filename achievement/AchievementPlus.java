package com.dyn.achievements.achievement;

import java.util.ArrayList;

import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.dyn.achievements.achievement.Requirements.CraftRequirement;
import com.dyn.achievements.achievement.Requirements.KillRequirement;
import com.dyn.achievements.achievement.Requirements.PickupRequirement;
import com.dyn.achievements.achievement.Requirements.SmeltRequirement;
import com.dyn.achievements.achievement.Requirements.SpawnRequirement;
import com.dyn.server.http.PostBadge;
import com.google.gson.JsonObject;

import net.minecraft.entity.player.EntityPlayer;

/***
 * AchievementPlus class modifies Achievement class in MineCraft source code.
 * 
 * @author Dominic Amato
 *
 */
public class AchievementPlus{

	private Requirements requirements;
	private String name;
	private String desc;
	
	//optional but needed to award a badge online;
	private int badgeId;
	
	private boolean awarded;

	public AchievementPlus(){
		requirements = new Requirements();
		name = "";
		desc = "";
		awarded = false;
		badgeId = 0;
	}
	
	public AchievementPlus(Requirements requirements, String name, String description, int badgeId) {
		this.requirements = requirements;
		this.name = name;
		this.desc = description;
		this.badgeId = badgeId;
		awarded = false;
		AchievementHandler.registerAchievement(this);

	}

	/***
	 * Get Requirements.
	 * @return requirements
	 */
	public Requirements getRequirements() {
		return requirements;
	}

	/***
	 * If requirements of specified type exists it returns true, else false.
	 * @param type AchievementType
	 * @return boolean 
	 */
	public boolean hasRequirementOfType(AchievementType type) {
		return requirements.getRequirementsByType(type).size() > 0;
	}

	/***
	 * Awards achievement to player.
	 * @param world World
	 * @param player EntityPlayer
	 * @param itemStack ItemStack
	 */
	public void awardAchievement(EntityPlayer player) {
		new PostBadge(badgeId, "", "", "");
		awarded = true;
	}
	
	public boolean isAwarded(){
		return awarded;
	}
	
	public void setAwarded(boolean state){
		awarded = state;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription() {
		return this.desc;
	}
	
	public void meetsRequirements(EntityPlayer player){
		for(BaseRequirement r : requirements.getRequirements()){
			if(r.getTotalAquired() < r.getTotalNeeded())
				return;
		}
		System.out.println("Awarding Achievement to player: " + player.getDisplayName());
		awardAchievement(player);
	}
	
	public void JsonToAchievement(JsonObject json){
		try{
			this.name = json.get("name").getAsString();
			this.desc = json.get("desc").getAsString();
			/*this.xCoord = json.get("x_coord").getAsInt();
			this.yCoord = json.get("y_coord").getAsInt();*/
			JsonObject req = (JsonObject) json.get("requirements");
			if(req.has("craft_requirements")){
				JsonObject reqType = (JsonObject) req.get("craft_requirements");
				int counter =1;
				while(reqType.has("craft_req_"+Integer.toString(counter))){
					JsonObject reqSubType = (JsonObject) reqType.get("craft_req_"+Integer.toString(counter++));
					CraftRequirement r = requirements.new CraftRequirement();
					r.setFromItemId(reqSubType.get("id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if(req.has("smelt_requirements")){
				JsonObject reqType = (JsonObject) req.get("smelt_requirements");
				int counter =1;
				while(reqType.has("smelt_req_"+Integer.toString(counter))){
					JsonObject reqSubType = (JsonObject) reqType.get("smelt_req_"+Integer.toString(counter++));
					SmeltRequirement r = requirements.new SmeltRequirement();
					r.setFromItemId(reqSubType.get("id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if(req.has("pick_up_requirements")){
				JsonObject reqType = (JsonObject) req.get("pick_up_requirements");
				int counter =1;
				while(reqType.has("pickup_req_"+Integer.toString(counter))){
					JsonObject reqSubType = (JsonObject) reqType.get("pickup_req_"+Integer.toString(counter++));
					PickupRequirement r = requirements.new PickupRequirement();
					r.setFromItemId(reqSubType.get("id").getAsInt(), reqSubType.get("sub_id").getAsInt());
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if(req.has("kill_requirements")){
				JsonObject reqType = (JsonObject) req.get("kill_requirements");
				int counter =1;
				while(reqType.has("kill_req_"+Integer.toString(counter))){
					JsonObject reqSubType = (JsonObject) reqType.get("kill_req_"+Integer.toString(counter++));
					KillRequirement r = requirements.new KillRequirement();
					r.entityType = reqSubType.get("entity").getAsString();
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if(req.has("spawn_requirements")){
				JsonObject reqType = (JsonObject) req.get("spawn_requirements");
				int counter =1;
				while(reqType.has("spawn_req_"+Integer.toString(counter))){
					JsonObject reqSubType = (JsonObject) reqType.get("spawn_req_"+Integer.toString(counter++));
					SpawnRequirement r = requirements.new SpawnRequirement();
					r.entityType = reqSubType.get("entity").getAsString();
					r.setAmountNeeded(reqSubType.get("amount").getAsInt());
					requirements.addRequirement(r);
				}
			}
			if(json.has("badge_id"))
				this.badgeId = json.get("badge_id").getAsInt();
			AchievementHandler.registerAchievement(this);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	public JsonObject achievementToJson(){
		JsonObject reply = new JsonObject();
		reply.addProperty("name", this.name);
		reply.addProperty("desc", this.desc);
		/*reply.addProperty("x_coord", this.xCoord);
		reply.addProperty("y_coord", this.yCoord);*/
		JsonObject req = new JsonObject();
		boolean[] types = requirements.getRequirementTypes();
		for(int i =0;i<6;i++){
			JsonObject reqTypes = new JsonObject();
			switch (i){
			case 0:
				if(types[i]){
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.CRAFT);
					int counter = 1;
					for(BaseRequirement t : typeReq){
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("id", t.getRequirementItemID());
						reqSubTypes.addProperty("sub_id", t.getRequirementSubItemID());
						reqTypes.add("craft_req_"+Integer.toString(counter++), reqSubTypes);
					}
					req.add("craft_requirements", reqTypes);
				}
				break;
			case 1:
				if(types[i]){
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.SMELT);
					int counter = 1;
					for(BaseRequirement t : typeReq){
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("id", t.getRequirementItemID());
						reqSubTypes.addProperty("sub_id", t.getRequirementSubItemID());
						reqTypes.add("smelt_req_"+Integer.toString(counter++), reqSubTypes);
					}
					req.add("smelt_requirements", reqTypes);
				}
				break;
			case 2:
				if(types[i]){
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.PICKUP);
					int counter = 1;
					for(BaseRequirement t : typeReq){
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("item", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqSubTypes.addProperty("id", t.getRequirementItemID());
						reqSubTypes.addProperty("sub_id", t.getRequirementSubItemID());
						reqTypes.add("pickup_req_"+Integer.toString(counter++), reqSubTypes);
					}
					req.add("pick_up_requirements", reqTypes);
				}
				break;
			case 3:
				if(types[i]){
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.STAT);
					int counter = 1;
					for(BaseRequirement t : typeReq){
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("stat", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqTypes.add("stat_req_"+Integer.toString(counter++), reqSubTypes);
					}
					req.add("stat_requirements", reqTypes);
				}
				break;
			case 4:
				if(types[i]){
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.KILL);
					int counter = 1;
					for(BaseRequirement t : typeReq){
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("entity", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqTypes.add("kill_req_"+Integer.toString(counter++), reqSubTypes);
					}
					req.add("kill_requirements", reqTypes);
				}
				break;
			case 5:
				if(types[i]){
					ArrayList<BaseRequirement> typeReq = requirements.getRequirementsByType(AchievementType.SPAWN);
					int counter = 1;
					for(BaseRequirement t : typeReq){
						JsonObject reqSubTypes = new JsonObject();
						reqSubTypes.addProperty("entity", t.getRequirementEntityName());
						reqSubTypes.addProperty("amount", t.getTotalNeeded());
						reqTypes.add("spawn_req_"+Integer.toString(counter++), reqSubTypes);
					}
					req.add("spawn_requirements", reqTypes);
				}
				break;
			default:
				break;
			}
		}
		reply.add("requirements", req);
		if(this.badgeId>0)
			reply.addProperty("badge_id", this.badgeId);
	
		return reply;
	}
	
	public enum AchievementType {
		CRAFT, SMELT, PICKUP, STAT, KILL, SPAWN, BREW, OTHER
	}
}
