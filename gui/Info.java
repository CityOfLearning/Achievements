package com.dyn.achievements.gui;

import java.util.ArrayList;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.achievement.RequirementType;
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
		setBackground(new DefaultBackground());
		title = "Achievement Gui";
		this.achievement = achievement;
		if (achievement.getTexture() != null) {
			texture = achievement.getTexture();
		} else {
			texture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
		}

	}

	@Override
	public void setup() {
		super.setup();

		registerComponent(new TextLabel(width / 3, (int) (height * .15), width / 3, 20,
				"Name: " + achievement.getName(), TextAlignment.LEFT));

		registerComponent(new ScrollTextLabel(width / 3, (int) (height * .24), width / 2, 35,
				"Description: " + achievement.getDescription(), TextAlignment.LEFT).setMultilined(true));

		registerComponent(new Picture((int) (width * .15), (int) (height * .15), width / 6, width / 6, texture));

		ArrayList<ListEntry> ulist = new ArrayList<ListEntry>();

		if (achievement.hasRequirementOfType(RequirementType.CRAFT)) {
			ulist.add(new StringEntry("-Craft-"));
		}
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.CRAFT)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(RequirementType.SMELT)) {
			ulist.add(new StringEntry("-Smelt-"));
		}
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.SMELT)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(RequirementType.PICKUP)) {
			ulist.add(new StringEntry("-Pickup-"));
		}
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.PICKUP)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(RequirementType.STAT)) {
			ulist.add(new StringEntry("-Special-"));
		}
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.STAT)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(RequirementType.KILL)) {
			ulist.add(new StringEntry("-Kill-"));
		}
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.KILL)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(RequirementType.BREW)) {
			ulist.add(new StringEntry("-Brew-"));
		}
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.BREW)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}

		if (achievement.hasRequirementOfType(RequirementType.PLACE)) {
			ulist.add(new StringEntry("-Place-"));
		}
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.PLACE)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}
		if (achievement.hasRequirementOfType(RequirementType.BREAK)) {
			ulist.add(new StringEntry("-Break-"));
		}
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.BREAK)) {
			ulist.add(new StringEntry(
					r.getRequirementEntityName() + " - " + r.getTotalAquired() + "/" + r.getTotalNeeded()));
		}
		if (achievement.hasRequirementOfType(RequirementType.LOCATION)) {
			ulist.add(new StringEntry("-Location-"));
		}
		for (BaseRequirement r : achievement.getRequirements().getRequirementsByType(RequirementType.LOCATION)) {
			ulist.add(new StringEntry((r.getTotalAquired() > 0 ? "[X]-" : "[ ]-") + r.getRequirementEntityName()));
		}
		if (achievement.hasRequirementOfType(RequirementType.MENTOR)) {
			ulist.add(new StringEntry("-Mentor-"));
			ulist.add(new StringEntry("Only a mentor can"));
			ulist.add(new StringEntry("give this achievement"));
		}

		registerComponent(new TextLabel((int) (width * .5), (int) (height * .4), width / 3, 20, "Requirements",
				TextAlignment.CENTER));

		if (achievement.isAwarded()) {
			registerComponent(new TextLabel((int) (width * .2), (int) (height * .4), width / 3, 20, "Achieved!",
					TextAlignment.CENTER));
		}

		registerComponent(
				new ScrollableDisplayList((int) (width * .5), (int) (height * .45), width / 3, 100, 15, ulist));

		registerComponent(new Button(width / 6, (int) (height * .8), 40, 20, "Back")
				.setClickListener(but -> getStage().displayPrevious()));

		// The background
		registerComponent(new Picture(width / 8, (int) (height * .05), (int) (width * (6.0 / 8.0)), (int) (height * .9),
				new ResourceLocation("dyn", "textures/gui/background3.png")));
	}

}