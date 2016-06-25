package com.dyn.achievements.gui;

import java.util.ArrayList;
import java.util.List;

import com.dyn.DYNServerConstants;
import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.handlers.AchievementManager;
import com.dyn.betterachievements.gui.GuiBetterAchievements;
import com.rabbit.gui.background.DefaultBackground;
import com.rabbit.gui.component.control.PictureButton;
import com.rabbit.gui.component.control.TextBox;
import com.rabbit.gui.component.display.Picture;
import com.rabbit.gui.component.display.TextLabel;
import com.rabbit.gui.component.grid.Grid;
import com.rabbit.gui.component.grid.ScrollableGrid;
import com.rabbit.gui.component.grid.entries.GridEntry;
import com.rabbit.gui.component.grid.entries.PictureButtonGridEntry;
import com.rabbit.gui.render.TextAlignment;
import com.rabbit.gui.show.Show;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Search extends Show {

	private ScrollableGrid achievementGrid;
	ResourceLocation imgTexture;

	public Search() {
		setBackground(new DefaultBackground());
		title = "Achievement Gui";
	}

	@Override
	public void setup() {
		super.setup();

		registerComponent(
				new TextLabel(width / 3, (int) (height * .1), width / 3, 20, "Achievements", TextAlignment.CENTER));

		// the side buttons
		registerComponent(new PictureButton((int) (width * .03), (int) (height * .2), 30, 30,
				DYNServerConstants.ACHIEVEMENT_IMAGE).setIsEnabled(true).addHoverText("Achievement Maps")
						.doesDrawHoverText(true).setClickListener(but -> Minecraft.getMinecraft()
								.displayGuiScreen(new GuiBetterAchievements(getStage(), 0))));

		/*
		 * this.registerComponent(new PictureButton((int) (this.width * .03),
		 * (int) (this.height * .35), 30, 30, new ResourceLocation("minecraft",
		 * "textures/items/ruby.png")).setIsEnabled(true) .addHoverText(
		 * "Student Rosters").doesDrawHoverText(true) .setClickListener(but ->
		 * this.getStage().display(new Roster())));
		 *
		 * this.registerComponent(new PictureButton((int) (this.width * .03),
		 * (int) (this.height * .5), 30, 30, new ResourceLocation("minecraft",
		 * "textures/items/cookie.png")).setIsEnabled(true) .addHoverText(
		 * "Manage Students").doesDrawHoverText(true) .setClickListener(but ->
		 * this.getStage().display(new ManageStudents())));
		 *
		 * this.registerComponent(new PictureButton((int) (this.width * .03),
		 * (int) (this.height * .65), 30, 30, new ResourceLocation("minecraft",
		 * "textures/items/emerald.png")).setIsEnabled(true) .addHoverText(
		 * "Give Items").doesDrawHoverText(true) .setClickListener(but ->
		 * this.getStage().display(new GiveItem())));
		 *
		 * this.registerComponent(new PictureButton((int) (this.width * .03),
		 * (int) (this.height * .8), 30, 30, new ResourceLocation("minecraft",
		 * "textures/items/ender_eye.png")).setIsEnabled(true) .addHoverText(
		 * "Award Achievements").doesDrawHoverText(true) .setClickListener(but
		 * -> this.getStage().display(new GiveAchievement())));
		 */

		List<GridEntry> entries = new ArrayList<GridEntry>();

		for (AchievementPlus a : AchievementManager.getAllAchievements()) {
			List<String> hoverText = new ArrayList<String>();
			hoverText.add(a.getName());
			hoverText.add(a.getDescription());
			if (a.getTexture() == null) {
				imgTexture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
			} else {
				imgTexture = a.getTexture();
			}
			entries.add(new PictureButtonGridEntry(25, 25, imgTexture).doesDrawHoverText(true).setHoverText(hoverText)
					.setClickListener((PictureButtonGridEntry entry, Grid grid, int mouseX, int mouseY) -> getStage()
							.display(new Info(a))));
		}

		achievementGrid = new ScrollableGrid((int) (width / 5.8), (int) (height * .25), (int) (width * .65),
				(int) (height * .62), 45, 45, entries);
		achievementGrid.setVisibleBackground(false);
		registerComponent(achievementGrid);

		registerComponent(new TextBox((int) (width * .2), (int) (height * .15), width / 2, 20, "Search for Achievement")
				.setId("achsearch")
				.setTextChangedListener((TextBox textbox, String previousText) -> textChanged(textbox, previousText)));
		// The background
		registerComponent(new Picture(width / 8, (int) (height * .05), (int) (width * (6.0 / 8.0)), (int) (height * .9),
				new ResourceLocation("dyn", "textures/gui/background3.png")));
	}

	private void textChanged(TextBox textbox, String previousText) {
		if (textbox.getId() == "achsearch") {
			achievementGrid.clear();
			if (textbox.getText().isEmpty()) {
				for (AchievementPlus a : AchievementManager.getAllAchievements()) {
					List<String> hoverText = new ArrayList<String>();
					hoverText.add(a.getName());
					hoverText.add(a.getDescription());
					if (a.getTexture() == null) {
						imgTexture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
					} else {
						imgTexture = a.getTexture();
					}
					achievementGrid.add(new PictureButtonGridEntry(25, 25, imgTexture).doesDrawHoverText(true)
							.setHoverText(hoverText).setClickListener((PictureButtonGridEntry entry, Grid grid,
									int mouseX, int mouseY) -> getStage().display(new Info(a))));
				}
			} else {
				for (AchievementPlus a : AchievementManager.findAchievementsByName(textbox.getText())) {
					List<String> hoverText = new ArrayList<String>();
					hoverText.add(a.getName());
					hoverText.add(a.getDescription());
					if (a.getTexture() == null) {
						imgTexture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
					} else {
						imgTexture = a.getTexture();
					}
					achievementGrid.add(new PictureButtonGridEntry(25, 25, imgTexture).doesDrawHoverText(true)
							.setHoverText(hoverText).setClickListener((PictureButtonGridEntry entry, Grid grid,
									int mouseX, int mouseY) -> getStage().display(new Info(a))));
				}
			}
		}
	}
}
