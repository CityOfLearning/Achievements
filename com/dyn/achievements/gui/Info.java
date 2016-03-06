package com.dyn.achievements.gui;

import java.util.ArrayList;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.AchievementType;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.rabbit.gui.background.DefaultBackground;
import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.display.Picture;
import com.rabbit.gui.component.display.ScrollTextLabel;
import com.rabbit.gui.component.display.TextLabel;
import com.rabbit.gui.component.list.ScrollableDisplayList;
import com.rabbit.gui.component.list.entries.ListEntry;
import com.rabbit.gui.component.list.entries.StringEntry;
import com.rabbit.gui.render.TextAlignment;
import com.rabbit.gui.show.Show;

import net.minecraft.util.ResourceLocation;

public class Info extends Show {

	private AchievementPlus achievement;
	private ResourceLocation texture;

	public Info(AchievementPlus achievement) {
		this.setBackground(new DefaultBackground());
		this.title = "Achievement Gui";
		this.achievement = achievement;
		if (achievement.getTexture() != null) {
			this.texture = achievement.getTexture();
		} else {
			this.texture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
		}

	}

	@Override
	public void setup() {
		super.setup();

		this.registerComponent(new TextLabel(this.width / 3, (int) (this.height * .15), this.width / 3, 20,
				"Name: " + this.achievement.getName(), TextAlignment.LEFT));

		this.registerComponent(new ScrollTextLabel(this.width / 3, (int) (this.height * .24), this.width / 2, 35,
				"Description: " + this.achievement.getDescription(), TextAlignment.LEFT).setMultilined(true));

		this.registerComponent(new Picture((int) (this.width * .15), (int) (this.height * .15), this.width / 6,
				this.width / 6, this.texture));

		ArrayList<ListEntry> ulist = new ArrayList<ListEntry>();

		if (this.achievement.hasRequirementOfType(AchievementType.CRAFT)) {
			ulist.add(new StringEntry("-Craft-"));
		}
		for (BaseRequirement r : this.achievement.getRequirements().getRequirementsByType(AchievementType.CRAFT)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (this.achievement.hasRequirementOfType(AchievementType.SMELT)) {
			ulist.add(new StringEntry("-Smelt-"));
		}
		for (BaseRequirement r : this.achievement.getRequirements().getRequirementsByType(AchievementType.SMELT)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (this.achievement.hasRequirementOfType(AchievementType.PICKUP)) {
			ulist.add(new StringEntry("-Pickup-"));
		}
		for (BaseRequirement r : this.achievement.getRequirements().getRequirementsByType(AchievementType.PICKUP)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (this.achievement.hasRequirementOfType(AchievementType.STAT)) {
			ulist.add(new StringEntry("-Special-"));
		}
		for (BaseRequirement r : this.achievement.getRequirements().getRequirementsByType(AchievementType.STAT)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (this.achievement.hasRequirementOfType(AchievementType.KILL)) {
			ulist.add(new StringEntry("-Kill-"));
		}
		for (BaseRequirement r : this.achievement.getRequirements().getRequirementsByType(AchievementType.KILL)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (this.achievement.hasRequirementOfType(AchievementType.BREW)) {
			ulist.add(new StringEntry("-Brew-"));
		}
		for (BaseRequirement r : this.achievement.getRequirements().getRequirementsByType(AchievementType.BREW)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (this.achievement.hasRequirementOfType(AchievementType.PLACE)) {
			ulist.add(new StringEntry("-Place-"));
		}
		for (BaseRequirement r : this.achievement.getRequirements().getRequirementsByType(AchievementType.PLACE)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}
		if (this.achievement.hasRequirementOfType(AchievementType.BREAK)) {
			ulist.add(new StringEntry("-Break-"));
		}
		for (BaseRequirement r : this.achievement.getRequirements().getRequirementsByType(AchievementType.BREAK)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}
		if (this.achievement.hasRequirementOfType(AchievementType.MENTOR)) {
			ulist.add(new StringEntry("-Mentor-"));
			ulist.add(new StringEntry("Only a mentor can"));
			ulist.add(new StringEntry("give this achievement"));
		}

		this.registerComponent(new TextLabel((int) (this.width * .5), (int) (this.height * .4), this.width / 3, 20,
				"Requirements", TextAlignment.CENTER));

		if (this.achievement.isAwarded()) {
			this.registerComponent(new TextLabel((int) (this.width * .2), (int) (this.height * .4), this.width / 3, 20,
					"Achieved!", TextAlignment.CENTER));
		}

		this.registerComponent(new ScrollableDisplayList((int) (this.width * .5), (int) (this.height * .45),
				this.width / 3, 100, 15, ulist));

		this.registerComponent(new Button(this.width / 6, (int) (this.height * .8), 40, 20, "Back")
				.setClickListener(but -> this.getStage().displayPrevious()));

		// The background
		this.registerComponent(new Picture(this.width / 8, (int) (this.height * .05), (int) (this.width * (6.0 / 8.0)),
				(int) (this.height * .9), new ResourceLocation("dyn", "textures/gui/background3.png")));
	}

}
