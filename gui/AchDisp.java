package com.dyn.achievements.gui;

import java.util.ArrayList;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.AchievementPlus.AchievementType;
import com.dyn.achievements.achievement.Requirements.BaseRequirement;
import com.rabbit.gui.background.DefaultBackground;
import com.rabbit.gui.component.control.Button;
import com.rabbit.gui.component.display.Picture;
import com.rabbit.gui.component.display.TextLabel;
import com.rabbit.gui.component.list.ScrollableDisplayList;
import com.rabbit.gui.component.list.entries.ListEntry;
import com.rabbit.gui.component.list.entries.StringEntry;
import com.rabbit.gui.render.TextAlignment;
import com.rabbit.gui.show.Show;

import net.minecraft.util.ResourceLocation;

public class AchDisp extends Show {

	AchievementPlus achievement;
	ResourceLocation texture;

	public AchDisp(AchievementPlus achievement, ResourceLocation picture) {
		this.setBackground(new DefaultBackground());
		this.title = "Achievement Gui";
		this.achievement = achievement;
		this.texture = picture;
	}

	@Override
	public void setup() {
		super.setup();

		this.registerComponent(new TextLabel(this.width / 3, (int) (this.height * .15), this.width / 3, 20,
				"Name: " + achievement.getName(), TextAlignment.LEFT));

		this.registerComponent(new TextLabel(this.width / 3, (int) (this.height * .25), this.width / 2, 20,
				"Description: " + achievement.getDescription(), TextAlignment.LEFT).setMultilined(true));

		this.registerComponent(
				new Picture(this.width / 7, (int) (this.height * .15), this.width / 6, this.width / 6, texture));

		ArrayList<ListEntry> ulist = new ArrayList();

		if (achievement.hasRequirementOfType(AchievementType.CRAFT))
			ulist.add(new StringEntry("-Craft-"));
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.CRAFT)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(AchievementType.SMELT))
			ulist.add(new StringEntry("-Smelt-"));
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.SMELT)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(AchievementType.PICKUP))
			ulist.add(new StringEntry("-Pickup-"));
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.PICKUP)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(AchievementType.STAT))
			ulist.add(new StringEntry("-Special-"));
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.STAT)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(AchievementType.KILL))
			ulist.add(new StringEntry("-Kill-"));
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.KILL)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(AchievementType.SPAWN))
			ulist.add(new StringEntry("-Spawn-"));
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(AchievementType.SPAWN)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		this.registerComponent(new TextLabel((int) (this.width * .5), (int) (this.height * .4), this.width / 3, 20,
				"Requirements", TextAlignment.CENTER));

		this.registerComponent(new ScrollableDisplayList((int) (this.width * .5), (int) (this.height * .45),
				this.width / 3, 100, 15, ulist));

		this.registerComponent(new Button(this.width / 6, (int) (this.height * .8), 40, 20, "Back")
				.setClickListener(but -> this.getStage().displayPrevious()));

		// The background
		this.registerComponent(new Picture(this.width / 8, (int) (this.height * .05), (int) (this.width * (6.0 / 8.0)),
				(int) (this.height * .9), new ResourceLocation("dyn", "textures/gui/background3.png")));
	}

}
