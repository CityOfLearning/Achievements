package com.dyn.achievements.gui;

import java.util.ArrayList;
import java.util.List;

import com.dyn.achievements.achievement.AchievementPlus;
import com.dyn.achievements.handlers.AchievementHandler;
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
		this.setBackground(new DefaultBackground());
		this.title = "Achievement Gui";
	}

	@Override
	public void setup() {
		super.setup();

		this.registerComponent(new TextLabel(this.width / 3, (int) (this.height * .1), this.width / 3, 20,
				"Achievements", TextAlignment.CENTER));

		// the side buttons
		this.registerComponent(new PictureButton((int) (this.width * .03), (int) (this.height * .2), 30, 30,
				new ResourceLocation("minecraft", "textures/items/nether_star.png")).setIsEnabled(true)
						.addHoverText("Achievement Maps").doesDrawHoverText(true).setClickListener(but -> Minecraft
								.getMinecraft().displayGuiScreen(new GuiBetterAchievements(this.getStage(), 0))));

		/*
		 * this.registerComponent(new PictureButton((int) (this.width * .03),
		 * (int) (this.height * .35), 30, 30, new ResourceLocation("minecraft",
		 * "textures/items/ruby.png")).setIsEnabled(true) .addHoverText(
		 * "Setup Student Roster").doesDrawHoverText(true) .setClickListener(but
		 * -> this.getStage().display(new Roster())));
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

		for (AchievementPlus a : AchievementHandler.getAllAchievements()) {
			List<String> hoverText = new ArrayList<String>();
			hoverText.add(a.getName());
			hoverText.add(a.getDescription());
			if (a.getTexture() == null) {
				this.imgTexture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
			} else {
				this.imgTexture = a.getTexture();
			}
			entries.add(new PictureButtonGridEntry(25, 25, this.imgTexture).doesDrawHoverText(true)
					.setHoverText(hoverText).setClickListener((PictureButtonGridEntry entry, Grid grid, int mouseX,
							int mouseY) -> this.getStage().display(new Info(a))));
		}

		this.achievementGrid = new ScrollableGrid((int) (this.width / 5.8), (int) (this.height * .25),
				(int) (this.width * .65), (int) (this.height * .62), 45, 45, entries);
		this.achievementGrid.setVisibleBackground(false);
		this.registerComponent(this.achievementGrid);

		this.registerComponent(new TextBox((int) (this.width * .2), (int) (this.height * .15), this.width / 2, 20,
				"Search for Achievement").setId("achsearch").setTextChangedListener(
						(TextBox textbox, String previousText) -> this.textChanged(textbox, previousText)));
		// The background
		this.registerComponent(new Picture(this.width / 8, (int) (this.height * .05), (int) (this.width * (6.0 / 8.0)),
				(int) (this.height * .9), new ResourceLocation("dyn", "textures/gui/background3.png")));
	}

	private void textChanged(TextBox textbox, String previousText) {
		if (textbox.getId() == "achsearch") {
			this.achievementGrid.clear();
			if (textbox.getText().isEmpty()) {
				for (AchievementPlus a : AchievementHandler.getAllAchievements()) {
					List<String> hoverText = new ArrayList<String>();
					hoverText.add(a.getName());
					hoverText.add(a.getDescription());
					if (a.getTexture() == null) {
						this.imgTexture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
					} else {
						this.imgTexture = a.getTexture();
					}
					this.achievementGrid.add(new PictureButtonGridEntry(25, 25, this.imgTexture).doesDrawHoverText(true)
							.setHoverText(hoverText).setClickListener((PictureButtonGridEntry entry, Grid grid,
									int mouseX, int mouseY) -> this.getStage().display(new Info(a))));
				}
			} else {
				for (AchievementPlus a : AchievementHandler.findAchievementsByName(textbox.getText())) {
					List<String> hoverText = new ArrayList<String>();
					hoverText.add(a.getName());
					hoverText.add(a.getDescription());
					if (a.getTexture() == null) {
						this.imgTexture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
					} else {
						this.imgTexture = a.getTexture();
					}
					this.achievementGrid.add(new PictureButtonGridEntry(25, 25, this.imgTexture).doesDrawHoverText(true)
							.setHoverText(hoverText).setClickListener((PictureButtonGridEntry entry, Grid grid,
									int mouseX, int mouseY) -> this.getStage().display(new Info(a))));
				}
			}
		}
	}
}
