package com.dyn.achievements.achievement;

import java.util.ArrayList;
import java.util.List;

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
		private List<Integer> zone_ids;

		BaseRequirement() {
			aquired = 0;
			amount = 0;
			item_id = 0;
			sub_id = 0;
			id = 0;
			zone_ids = new ArrayList<>();
		}

		BaseRequirement(BaseRequirement br) {
			aquired = br.aquired;
			amount = br.amount;
			item_id = br.item_id;
			sub_id = br.sub_id;
			id = br.id;
			zone_ids = br.getZoneIds();
		}

		public void addZoneId(int zone_id) {
			zone_ids.add(zone_id);
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof BaseRequirement)) {
				return false;
			}
			BaseRequirement br = (BaseRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			return true;
		}

		public abstract String getRequirementEntityName();

		public int getRequirementID() {
			return id;
		}

		public int getRequirementItemID() {
			return item_id;
		}

		public int getRequirementSubItemID() {
			return sub_id;
		}

		public int getTotalAquired() {
			return aquired;
		}

		public int getTotalNeeded() {
			return amount;
		}

		public List<Integer> getZoneIds() {
			return zone_ids;
		}

		public boolean hasBeenMet() {
			return aquired >= amount;
		}

		public void incrementTotal() {
			aquired++;
		}

		public void incrementTotalBy(int amt) {
			aquired += amt;
		}

		public void setAmountNeeded(int total) {
			amount = total;
		}

		public void setAquiredTo(int total) {
			aquired = total;
		}

		public void setItemId(int id) {
			item_id = id;
		}

		public void setRequirementId(int id) {
			this.id = id;
		}

		public void setSubItemId(int id) {
			sub_id = id;
		}

		@Override
		public String toString() {
			return String.format("id: %d, ", id);
		}
	}

	public class BreakRequirement extends BaseRequirement {
		public ItemStack item;

		public BreakRequirement() {
			super();
			item = null;
		}

		public BreakRequirement(BreakRequirement pr) {
			super(pr);
			item = pr.item;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof BreakRequirement)) {
				return false;
			}
			BreakRequirement br = (BreakRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}

		@Override
		public String toString() {
			return "Break: " + super.toString() + " - " + getRequirementEntityName();
		}
	}

	public class BrewRequirement extends BaseRequirement {
		public ItemStack item;

		public BrewRequirement() {
			super();
			item = null;
		}

		public BrewRequirement(BrewRequirement pr) {
			super(pr);
			item = pr.item;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof BrewRequirement)) {
				return false;
			}
			BrewRequirement br = (BrewRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}

		@Override
		public String toString() {
			return "Brew: " + super.toString() + " - " + getRequirementEntityName();
		}
	}

	public class CraftRequirement extends BaseRequirement {
		public ItemStack item;

		public CraftRequirement() {
			super();
			item = null;
		}

		public CraftRequirement(CraftRequirement cr) {
			super(cr);
			item = cr.item;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof CraftRequirement)) {
				return false;
			}
			CraftRequirement br = (CraftRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}

		@Override
		public String toString() {
			return "Craft: " + super.toString() + " - " + getRequirementEntityName();
		}
	}

	public class KillRequirement extends BaseRequirement {
		public String entityType;

		public KillRequirement() {
			super();
			entityType = "";
		}

		public KillRequirement(KillRequirement kr) {
			super(kr);
			entityType = kr.entityType;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof KillRequirement)) {
				return false;
			}
			KillRequirement br = (KillRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return entityType;
		}

		@Override
		public String toString() {
			return "Kill: " + super.toString() + " - " + getRequirementEntityName();
		}
	}

	public class LocationRequirement extends BaseRequirement {

		public String name;
		public int x1;
		public int y1;
		public int z1;
		public int r;
		public int x2;
		public int y2;
		public int z2;

		public LocationRequirement() {
			super();
			name = "";
			x1 = 0;
			y1 = 0;
			z1 = 0;
			r = -1;
			x2 = 0;
			y2 = 0;
			z2 = 0;
			setAmountNeeded(1);
		}

		public LocationRequirement(LocationRequirement lr) {
			super(lr);
			name = lr.name;
			x1 = lr.x1;
			y1 = lr.y1;
			z1 = lr.z1;
			r = lr.r;
			x2 = lr.x2;
			y2 = lr.y2;
			z2 = lr.z2;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof LocationRequirement)) {
				return false;
			}
			LocationRequirement br = (LocationRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			if ((x1 != br.x1) || (y1 != br.y1) || (z1 != br.z1) || (r != br.r) || (x2 != br.x2) || (y2 != br.y2)
					|| (z2 != br.z2)) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return name;
		}

		@Override
		public String toString() {
			if (r > 0) {
				return "Location: " + super.toString() + " - " + getRequirementEntityName()
						+ String.format(" <x: %d> <y: %d> <z: %d> <r: %d>", x1, y1, z1, r);
			} else {
				return "Location: " + super.toString() + " - " + getRequirementEntityName() + String
						.format(" <x1: %d> <y1: %d> <z1: %d> <x2: %d> <y2: %d> <z2: %d>", x1, y1, z1, x2, y2, z2);
			}
		}
	}

	public class MentorRequirement extends BaseRequirement {
		public String info;

		public MentorRequirement() {
			super();
			info = "";
		}

		public MentorRequirement(MentorRequirement sr) {
			super(sr);
			info = sr.info;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof MentorRequirement)) {
				return false;
			}
			MentorRequirement br = (MentorRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return info;
		}

		@Override
		public String toString() {
			return "Mentor: " + super.toString() + " - " + getRequirementEntityName();
		}
	}

	public class PickupRequirement extends BaseRequirement {
		public ItemStack item;

		public PickupRequirement() {
			super();
			item = null;
		}

		public PickupRequirement(PickupRequirement pr) {
			super(pr);
			item = pr.item;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof PickupRequirement)) {
				return false;
			}
			PickupRequirement br = (PickupRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}

		@Override
		public String toString() {
			return "Pickup: " + super.toString() + " - " + getRequirementEntityName();
		}
	}

	public class PlaceRequirement extends BaseRequirement {
		public ItemStack item;

		public PlaceRequirement() {
			super();
			item = null;
		}

		public PlaceRequirement(PlaceRequirement pr) {
			super(pr);
			item = pr.item;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof PlaceRequirement)) {
				return false;
			}
			PlaceRequirement br = (PlaceRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}

		@Override
		public String toString() {
			return "Place: " + super.toString() + " - " + getRequirementEntityName();
		}
	}

	public class SmeltRequirement extends BaseRequirement {
		public ItemStack item;

		public SmeltRequirement() {
			super();
			item = null;
		}

		public SmeltRequirement(SmeltRequirement sr) {
			super(sr);
			item = sr.item;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof SmeltRequirement)) {
				return false;
			}
			SmeltRequirement br = (SmeltRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return item.getDisplayName();
		}

		public void setFromItemId(int id, int subItemId) {
			item = new ItemStack(Item.getItemById(id), 1, subItemId);
			setItemId(id);
			setSubItemId(subItemId);
		}

		@Override
		public String toString() {
			return "Smelt: " + super.toString() + " - " + getRequirementEntityName();
		}
	}

	public class StatRequirement extends BaseRequirement {
		public StatBase eventStat;

		public StatRequirement() {
			super();
			eventStat = null;
		}

		public StatRequirement(StatRequirement sr) {
			super(sr);
			eventStat = sr.eventStat;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof StatRequirement)) {
				return false;
			}
			StatRequirement br = (StatRequirement) o;
			if (br.getRequirementID() != getRequirementID()) {
				return false;
			}
			if (br.getRequirementItemID() != getRequirementItemID()) {
				return false;
			}
			if (br.getRequirementSubItemID() != getRequirementSubItemID()) {
				return false;
			}
			if (br.getTotalNeeded() != getTotalNeeded()) {
				return false;
			}
			if (br.getRequirementEntityName() != getRequirementEntityName()) {
				return false;
			}
			return true;
		}

		@Override
		public String getRequirementEntityName() {
			return eventStat.toString();
		}

		@Override
		public String toString() {
			return "Stat: " + super.toString() + " - " + getRequirementEntityName();
		}
	}

	public static Requirements getCopy(Requirements r) {
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
			if (br instanceof LocationRequirement) {
				copy.addRequirement(copy.new LocationRequirement((LocationRequirement) br));
			}
		}
		return copy;
	}

	private ArrayList<BaseRequirement> requirements = new ArrayList<>();

	public Requirements() {

	}

	/***
	 * Add requirement to requirements ArrayList.
	 *
	 * @param req
	 *            BaseRequirement
	 */
	public void addRequirement(BaseRequirement req) {
		requirements.add(req);
	}

	/***
	 * Get requirements ArrayList.
	 *
	 * @return requirements ArrayList
	 */
	public ArrayList<BaseRequirement> getRequirements() {
		return requirements;
	}

	/***
	 * Get list of requirements by specified type.
	 *
	 * @param type
	 *            AchievementType
	 * @return ArrayList<BaseRequirement> of given type
	 */
	public ArrayList<BaseRequirement> getRequirementsByType(RequirementType type) {
		ArrayList<BaseRequirement> typereq = new ArrayList<>();
		for (BaseRequirement r : requirements) {
			switch (type) {
			case CRAFT:
				if (r instanceof CraftRequirement) {
					typereq.add(r);
				}
				break;
			case SMELT:
				if (r instanceof SmeltRequirement) {
					typereq.add(r);
				}
				break;
			case PICKUP:
				if (r instanceof PickupRequirement) {
					typereq.add(r);
				}
				break;
			case STAT:
				if (r instanceof StatRequirement) {
					typereq.add(r);
				}
				break;
			case KILL:
				if (r instanceof KillRequirement) {
					typereq.add(r);
				}
				break;
			case BREW:
				if (r instanceof BrewRequirement) {
					typereq.add(r);
				}
				break;
			case PLACE:
				if (r instanceof PlaceRequirement) {
					typereq.add(r);
				}
				break;
			case BREAK:
				if (r instanceof BreakRequirement) {
					typereq.add(r);
				}
				break;
			case MENTOR:
				if (r instanceof MentorRequirement) {
					typereq.add(r);
				}
				break;
			case LOCATION:
				if (r instanceof LocationRequirement) {
					typereq.add(r);
				}
				break;
			default:
				break;
			}
		}
		return typereq;
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
		boolean isLoc = false;
		for (BaseRequirement r : requirements) {
			if (r instanceof CraftRequirement) {
				hasCraft = true;
			}

			if (r instanceof SmeltRequirement) {
				hasSmelt = true;
			}

			if (r instanceof PickupRequirement) {
				hasPickup = true;
			}

			if (r instanceof StatRequirement) {
				hasStat = true;
			}

			if (r instanceof KillRequirement) {
				hasKill = true;
			}

			if (r instanceof BrewRequirement) {
				hasBrew = true;
			}

			if (r instanceof PlaceRequirement) {
				hasPlace = true;
			}

			if (r instanceof BreakRequirement) {
				hasBreak = true;
			}

			if (r instanceof MentorRequirement) {
				isMentor = true;
			}
			if (r instanceof LocationRequirement) {
				isLoc = true;
			}
		}
		boolean[] types = { hasCraft, hasSmelt, hasPickup, hasStat, hasKill, hasBrew, hasPlace, hasBreak, isMentor,
				isLoc };
		return types;
	}
}
