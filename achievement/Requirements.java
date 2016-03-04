package com.dyn.achievements.achievement;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;

public class Requirements {
	public abstract class BaseRequirement {
		private int aquired;
		private int amount;
		private int id;
		private int item_id;
		private int sub_id;

		BaseRequirement() {
			this.aquired = 0;
			this.amount = 0;
			this.item_id = 0;
			this.sub_id = 0;
			this.id = 0;
		}

		BaseRequirement(BaseRequirement br) {
			this.aquired = br.aquired;
			this.amount = br.amount;
			this.item_id = br.item_id;
			this.sub_id = br.sub_id;
			this.id = br.id;
		}

		public abstract String getRequirementEntityName();

		public int getTotalNeeded() {
			return this.amount;
		}

		public int getTotalAquired() {
			return this.aquired;
		}

		public int getRequirementItemID() {
			return this.item_id;
		}

		public int getRequirementSubItemID(){
			return this.sub_id;
		}
		
		public int getRequirementID(){
			return this.id;
		}
		
		public void setItemId(int id){
			this.item_id = id;
		}
		
		public void setSubItemId(int id){
			this.sub_id = id;
		}
		
		public void setRequirementId(int id){
			this.id = id;
		}

		public void incrementTotal() {
			this.aquired++;
		}

		public void incrementTotalBy(int amt) {
			this.aquired += amt;
		}

		public void setAquiredTo(int total) {
			this.aquired = total;
		}

		public void setAmountNeeded(int total) {
			this.amount = total;
		}
	}

	public class CraftRequirement extends BaseRequirement {
		public ItemStack item;

		public CraftRequirement() {
			super();
			this.item = null;
		}

		public CraftRequirement(CraftRequirement cr) {
			super(cr);
			this.item = cr.item;
		}

		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}

	public class SmeltRequirement extends BaseRequirement {
		public ItemStack item;

		public SmeltRequirement() {
			super();
			this.item = null;
		}

		public SmeltRequirement(SmeltRequirement sr) {
			super(sr);
			this.item = sr.item;
		}

		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}

	public class KillRequirement extends BaseRequirement {
		public String entityType;

		public KillRequirement() {
			super();
			this.entityType = "";
		}

		public KillRequirement(KillRequirement kr) {
			super(kr);
			this.entityType = kr.entityType;
		}

		@Override
		public String getRequirementEntityName() {
			return this.entityType;
		}
	}

	public class PickupRequirement extends BaseRequirement {
		public ItemStack item;

		public PickupRequirement() {
			super();
			this.item = null;
		}

		public PickupRequirement(PickupRequirement pr) {
			super(pr);
			this.item = pr.item;
		}

		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}
	
	public class PlaceRequirement extends BaseRequirement {
		public ItemStack item;

		public PlaceRequirement() {
			super();
			this.item = null;
		}

		public PlaceRequirement(PlaceRequirement pr) {
			super(pr);
			this.item = pr.item;
		}

		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}
	
	public class BreakRequirement extends BaseRequirement {
		public ItemStack item;

		public BreakRequirement() {
			super();
			this.item = null;
		}

		public BreakRequirement(BreakRequirement pr) {
			super(pr);
			this.item = pr.item;
		}

		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}
	
	public class BrewRequirement extends BaseRequirement {
		public ItemStack item;

		public BrewRequirement() {
			super();
			this.item = null;
		}

		public BrewRequirement(BrewRequirement pr) {
			super(pr);
			this.item = pr.item;
		}

		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}

	public class StatRequirement extends BaseRequirement {
		public StatBase eventStat;

		public StatRequirement() {
			super();
			this.eventStat = null;
		}

		public StatRequirement(StatRequirement sr) {
			super(sr);
			this.eventStat = sr.eventStat;
		}

		@Override
		public String getRequirementEntityName() {
			return this.eventStat.toString();
		}
	}
	
	public class MentorRequirement extends BaseRequirement {
		public String info;

		public MentorRequirement() {
			super();
			this.info = "";
		}

		public MentorRequirement(MentorRequirement sr) {
			super(sr);
			this.info = sr.info;
		}

		@Override
		public String getRequirementEntityName() {
			return this.info;
		}
	}

	private ArrayList<BaseRequirement> requirements = new ArrayList();

	public Requirements() {

	}
	
	public static Requirements getCopy(Requirements r){
		Requirements copy = new Requirements();
		for (BaseRequirement br : r.getRequirements()) {
			if (br instanceof CraftRequirement) {
				copy.addRequirement(copy.new CraftRequirement((CraftRequirement) br));
			}
			if (br instanceof SmeltRequirement) {
				copy.addRequirement(copy.new SmeltRequirement((SmeltRequirement) br));
			}
			if (br instanceof PickupRequirement) {
				copy.addRequirement(copy.new PickupRequirement((PickupRequirement) br));
			}
			if (br instanceof StatRequirement) {
				copy.addRequirement(copy.new StatRequirement((StatRequirement) br));
			}
			if (br instanceof KillRequirement) {
				copy.addRequirement(copy.new KillRequirement((KillRequirement) br));
			}
			if (br instanceof BrewRequirement) {
				copy.addRequirement(copy.new BrewRequirement((BrewRequirement) br));
			}
			if (br instanceof PlaceRequirement) {
				copy.addRequirement(copy.new PlaceRequirement((PlaceRequirement) br));
			}
			if (br instanceof BreakRequirement) {
				copy.addRequirement(copy.new BreakRequirement((BreakRequirement) br));
			}
			if (br instanceof MentorRequirement) {
				copy.addRequirement(copy.new MentorRequirement((MentorRequirement) br));
			}
		}
		return copy;
	}
	

	/***
	 * Add requirement to requirements ArrayList.
	 * 
	 * @param req
	 *            BaseRequirement
	 */
	public void addRequirement(BaseRequirement req) {
		this.requirements.add(req);
	}

	/***
	 * Get requirements ArrayList.
	 * 
	 * @return requirements ArrayList
	 */
	public ArrayList<BaseRequirement> getRequirements() {
		return this.requirements;
	}

	/***
	 * Get boolean of all types from requirements ArrayList.
	 * 
	 * @return boolean[] of types in requirements
	 */
	public boolean[] getRequirementTypes() {
		boolean hasCraft = false;
		boolean hasSmelt = false;
		boolean hasPickup = false;
		boolean hasStat = false;
		boolean hasKill = false;
		boolean hasBrew = false;
		boolean hasPlace = false;
		boolean hasBreak = false;
		boolean isMentor = false;
		for (BaseRequirement r : this.requirements) {
			if (r instanceof CraftRequirement)
				hasCraft = true;

			if (r instanceof SmeltRequirement)
				hasSmelt = true;

			if (r instanceof PickupRequirement)
				hasPickup = true;

			if (r instanceof StatRequirement)
				hasStat = true;

			if (r instanceof KillRequirement)
				hasKill = true;
			
			if (r instanceof BrewRequirement)
				hasBrew = true;
			
			if (r instanceof PlaceRequirement)
				hasPlace = true;
			
			if (r instanceof BreakRequirement)
				hasBreak = true;

			if (r instanceof MentorRequirement)
				isMentor = true;
		}
		boolean[] types = { hasCraft, hasSmelt, hasPickup, hasStat, hasKill, hasBrew, hasPlace, hasBreak, isMentor };
		return types;
	}

	/***
	 * Get list of requirements by specified type.
	 * 
	 * @param type
	 *            AchievementType
	 * @return ArrayList<BaseRequirement> of given type
	 */
	public ArrayList<BaseRequirement> getRequirementsByType(AchievementType type) {
		ArrayList<BaseRequirement> typereq = new ArrayList();
		for (BaseRequirement r : this.requirements) {
			switch (type) {
			case CRAFT:
				if (r instanceof CraftRequirement)
					typereq.add(r);
				break;
			case SMELT:
				if (r instanceof SmeltRequirement)
					typereq.add(r);
				break;
			case PICKUP:
				if (r instanceof PickupRequirement)
					typereq.add(r);
				break;
			case STAT:
				if (r instanceof StatRequirement)
					typereq.add(r);
				break;
			case KILL:
				if (r instanceof KillRequirement)
					typereq.add(r);
				break;
			case BREW:
				if (r instanceof BrewRequirement)
					typereq.add(r);
				break;
			case PLACE:
				if (r instanceof PlaceRequirement)
					typereq.add(r);
				break;
			case BREAK:
				if (r instanceof BreakRequirement)
					typereq.add(r);
				break;
			case MENTOR:
				if (r instanceof MentorRequirement)
					typereq.add(r);
				break;
			default:
				break;
			}
		}
		return typereq;
	}
}
