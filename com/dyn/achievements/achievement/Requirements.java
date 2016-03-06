package com.dyn.achievements.achievement;

import java.util.ArrayList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatBase;
/**
 * Requirements for an achievement.
 * @author Dominic Amato
 * @version 1.0
 * @since 2016-03-06
 */
public class Requirements {
	/**
	 * Abstract class for the base requirements of any achievement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */
	public abstract class BaseRequirement {
		/**
		 * Stores how many requirements a player has acquired.
		 */
		private int aquired;
		/**
		 * Amount of requirements needed.
		 */
		private int amount;
		/**
		 * ID of requirement.
		 */
		private int id;
		/**
		 * Item ID number.
		 */
		private int item_id;
		/**
		 * Sub ID number.
		 */
		private int sub_id;

		/**
		 * Constructor of BaseRequirement.
		 * Sets all variables to zero.
		 */
		BaseRequirement() {
			this.aquired = 0;
			this.amount = 0;
			this.item_id = 0;
			this.sub_id = 0;
			this.id = 0;
		}

		/**
		 * Constructor of BaseRequirement.
		 * Takes in a BaseRequirement object.
		 * @param br
		 */
		BaseRequirement(BaseRequirement br) {
			this.aquired = br.aquired;
			this.amount = br.amount;
			this.item_id = br.item_id;
			this.sub_id = br.sub_id;
			this.id = br.id;
		}

		/**
		 * Gets required entity name.
		 * @return
		 */
		public abstract String getRequirementEntityName();

		/**
		 * Gets total requirements needed.
		 * @return Returns amount.
		 */
		public int getTotalNeeded() {
			return this.amount;
		}

		/**
		 * Gets total requirements acquired.
		 * @return Return aquired.
		 */
		public int getTotalAquired() {
			return this.aquired;
		}

		/**
		 * Gets requirement item ID.
		 * @return Returns item_id.
		 */
		public int getRequirementItemID() {
			return this.item_id;
		}

		/**
		 * Gets requirement sub ID.
		 * @return Returns sub_id.
		 */
		public int getRequirementSubItemID(){
			return this.sub_id;
		}
		
		/**
		 * Gets requirement ID.
		 * @return Returns id.
		 */
		public int getRequirementID(){
			return this.id;
		}
		
		/**
		 * Sets item ID.
		 * @param id
		 */
		public void setItemId(int id){
			this.item_id = id;
		}
		
		/**
		 * Sets item sub ID.
		 * @param id
		 */
		public void setSubItemId(int id){
			this.sub_id = id;
		}
		/**
		 * Sets requirement ID.
		 * @param id
		 */
		public void setRequirementId(int id){
			this.id = id;
		}

		/**
		 * Increments total requirements acquired.
		 */
		public void incrementTotal() {
			this.aquired++;
		}

		/**
		 * Increments total requirements acquired by input value.
		 * @param amt
		 */
		public void incrementTotalBy(int amt) {
			this.aquired += amt;
		}

		/**
		 * Sets total requirements acquired to input value.
		 * @param total
		 */
		public void setAquiredTo(int total) {
			this.aquired = total;
		}

		/**
		 * Sets amount needed to input value.
		 * @param total
		 */
		public void setAmountNeeded(int total) {
			this.amount = total;
		}
	}

	/**
	 * CraftRequirement class extends BaseRequirement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */
	public class CraftRequirement extends BaseRequirement {
		/**
		 * Number of items in stack for requirement.
		 */
		public ItemStack item;

		/**
		 * CraftRequirement Constructor.
		 */
		public CraftRequirement() {
			super();
			this.item = null;
		}

		/**
		 * CraftRequirement Constructor that sets item to CraftRequirement parameter.
		 * @param cr
		 */
		public CraftRequirement(CraftRequirement cr) {
			super(cr);
			this.item = cr.item;
		}

		/**
		 * Gets entity name of requirement.
		 */
		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		/**
		 * Sets sub item ID from id.
		 * @param id
		 * @param subItemId
		 */
		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}
	/**
	 * SmeltRequirement class extends BaseRequirement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */
	public class SmeltRequirement extends BaseRequirement {
		/**
		 * Number of items in stack for requirement.
		 */
		public ItemStack item;

		/**
		 * SmeltRequirement Constructor.
		 */
		public SmeltRequirement() {
			super();
			this.item = null;
		}

		/**
		 * SmeltRequirement Constructor that sets item to SmeltRequirement parameter.
		 * @param cr
		 */
		public SmeltRequirement(SmeltRequirement sr) {
			super(sr);
			this.item = sr.item;
		}

		/**
		 * Gets entity name of requirement.
		 */
		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		/**
		 * Sets sub item ID from id.
		 * @param id
		 * @param subItemId
		 */
		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}

	/**
	 * KillRequirement class extends BaseRequirement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */
	public class KillRequirement extends BaseRequirement {
		/**
		 * Instance of entity to kill.
		 */
		public String entityType;

		/**
		 * KillRequirement Constructor.
		 */
		public KillRequirement() {
			super();
			this.entityType = "";
		}

		/**
		 * KillRequirement Constructor that sets entity to KillRequirement parameter.
		 * @param cr
		 */
		public KillRequirement(KillRequirement kr) {
			super(kr);
			this.entityType = kr.entityType;
		}

		/**
		 * Gets required entity to kill name.
		 */
		@Override
		public String getRequirementEntityName() {
			return this.entityType;
		}
	}

	/**
	 * PickupRequirement class extends BaseRequirement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */
	public class PickupRequirement extends BaseRequirement {
		/**
		 * Number of items in stack for requirement.
		 */
		public ItemStack item;

		/**
		 * PickupRequirement Constructor.
		 */
		public PickupRequirement() {
			super();
			this.item = null;
		}

		/**
		 * PickupRequirement Constructor that sets item to PickupRequirement parameter.
		 * @param cr
		 */
		public PickupRequirement(PickupRequirement pr) {
			super(pr);
			this.item = pr.item;
		}

		/**
		 * Gets entity name of requirement.
		 */
		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		/**
		 * Sets sub item ID from id.
		 * @param id
		 * @param subItemId
		 */
		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}
	/**
	 * PlaceRequirement class extends BaseRequirement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */
	public class PlaceRequirement extends BaseRequirement {
		/**
		 * Number of items in stack for requirement.
		 */
		public ItemStack item;
		
		/**
		 * PlaceRequirement Constructor.
		 */
		public PlaceRequirement() {
			super();
			this.item = null;
		}

		/**
		 * PlaceRequirement Constructor that sets item to PlaceRequirement parameter.
		 * @param cr
		 */
		public PlaceRequirement(PlaceRequirement pr) {
			super(pr);
			this.item = pr.item;
		}

		/**
		 * Gets entity name of requirement.
		 */
		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}

		/**
		 * Sets sub item ID from id.
		 * @param id
		 * @param subItemId
		 */
		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}
	/**
	 * BreakRequirement class extends BaseRequirement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */
	public class BreakRequirement extends BaseRequirement {
		/**
		 * Number of items in stack for requirement.
		 */
		public ItemStack item;
		/**
		 * BreakRequirement Constructor.
		 */
		public BreakRequirement() {
			super();
			this.item = null;
		}

		/**
		 * BreakRequirement Constructor that sets item to BreakRequirement parameter.
		 * @param cr
		 */
		public BreakRequirement(BreakRequirement pr) {
			super(pr);
			this.item = pr.item;
		}
		/**
		 * Gets entity name of requirement.
		 */
		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}
		/**
		 * Sets sub item ID from id.
		 * @param id
		 * @param subItemId
		 */
		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}
	/**
	 * BrewRequirement class extends BaseRequirement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */	
	public class BrewRequirement extends BaseRequirement {
		/**
		 * Number of items in stack for requirement.
		 */
		public ItemStack item;
		/**
		 * BrewRequirement Constructor.
		 */
		public BrewRequirement() {
			super();
			this.item = null;
		}
		/**
		 * BrewRequirement Constructor that sets item to BrewRequirement parameter.
		 * @param cr
		 */
		public BrewRequirement(BrewRequirement pr) {
			super(pr);
			this.item = pr.item;
		}
		/**
		 * Gets entity name of requirement.
		 */
		@Override
		public String getRequirementEntityName() {
			return this.item.getDisplayName();
		}
		/**
		 * Sets sub item ID from id.
		 * @param id
		 * @param subItemId
		 */
		public void setFromItemId(int id, int subItemId) {
			this.item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}
	}
	/**
	 * StatRequirement class extends BaseRequirement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */	
	public class StatRequirement extends BaseRequirement {
		/**
		 * Current stat.
		 */
		public StatBase eventStat;

		/**
		 * StatRequirement Constructor.
		 */
		public StatRequirement() {
			super();
			this.eventStat = null;
		}
		/**
		 * StatRequirement Constructor that sets event stat to the StatRequirement parameter.
		 * @param cr
		 */
		public StatRequirement(StatRequirement sr) {
			super(sr);
			this.eventStat = sr.eventStat;
		}
		/**
		 * Gets entity name of requirement.
		 */
		@Override
		public String getRequirementEntityName() {
			return this.eventStat.toString();
		}
	}
	/**
	 * MentorRequirement class extends BaseRequirement.
	 * @author Dominic Amato
	 * @version 1.0
	 * @since 2016-03-06
	 */	
	public class MentorRequirement extends BaseRequirement {
		/**
		 * Info of mentor requirement
		 */
		public String info;
		/**
		 * MentorRequirement Constructor.
		 */
		public MentorRequirement() {
			super();
			this.info = "";
		}
		/**
		 * MentorRequirement Constructor sets info from MentorRequirement parameter.
		 */
		public MentorRequirement(MentorRequirement sr) {
			super(sr);
			this.info = sr.info;
		}
		/**
		 * Gets entity name of requirement.
		 */
		@Override
		public String getRequirementEntityName() {
			return this.info;
		}
	}

	/**
	 * List of all requirements for an achievement.
	 */
	private ArrayList<BaseRequirement> requirements = new ArrayList();

	public Requirements() {

	}
	/**
	 * Gets a copy of a requirement. 
	 * @param r
	 * @return Returns copy of requirements for an achievement.
	 */
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
