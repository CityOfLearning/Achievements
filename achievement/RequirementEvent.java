package com.dyn.achievements.achievement;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.Achievement;
import net.minecraftforge.event.entity.player.AchievementEvent;

public class RequirementEvent extends AchievementEvent {

	public static class Increment extends RequirementEvent {
		public Increment(EntityPlayer player, Achievement achievement, Requirements.BaseRequirement requirement) {
			super(player, achievement, requirement);
		}
	}

	public static class Met extends RequirementEvent {
		public Met(EntityPlayer player, Achievement achievement, Requirements.BaseRequirement requirement) {
			super(player, achievement, requirement);
		}
	}

	private final Requirements.BaseRequirement requirement;

	RequirementEvent(EntityPlayer player, Achievement achievement, Requirements.BaseRequirement requirement) {
		super(player, achievement);
		this.requirement = requirement;
	}

	public Requirements.BaseRequirement getRequirement() {
		return requirement;
	}

}
