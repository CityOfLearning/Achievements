package com.dyn.achievements.gui;

import java.util.ArrayList;
import java.util.List;

import com.dyn.achievements.achievement.AchievementHandler;
import com.dyn.achievements.achievement.AchievementPlus;
import com.rabbit.gui.background.DefaultBackground;
import com.rabbit.gui.component.control.TextBox;
import com.rabbit.gui.component.display.Picture;
import com.rabbit.gui.component.display.TextLabel;
import com.rabbit.gui.component.grid.Grid;
import com.rabbit.gui.component.grid.ScrollableGrid;
import com.rabbit.gui.component.grid.entries.GridEntry;
import com.rabbit.gui.component.grid.entries.PictureButtonGridEntry;
import com.rabbit.gui.render.TextAlignment;
import com.rabbit.gui.show.Show;

import net.minecraft.util.ResourceLocation;

public class AchHome extends Show {

	private ScrollableGrid achievementGrid;
	
	public AchHome() {
		this.setBackground(new DefaultBackground());
		this.title = "Achievement Gui";
	}

	@Override
	public void setup() {
		super.setup();

		this.registerComponent(new TextLabel(this.width / 3, (int) (this.height * .1), this.width / 3, 20,
				"Achievements", TextAlignment.CENTER));

		List<GridEntry> entries = new ArrayList();

		for (AchievementPlus a : AchievementHandler.getAllAchievements()) {
			List<String> hoverText = new ArrayList();
			hoverText.add(a.getName());
			hoverText.add(a.getDescription());
			ResourceLocation imgTexture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
			entries.add(new PictureButtonGridEntry(25, 25, imgTexture).doesDrawHoverText(true).setHoverText(hoverText)
					.setClickListener((PictureButtonGridEntry entry, Grid grid, int mouseX, int mouseY) -> this
							.getStage().display(new AchDisp(a, imgTexture))));
		}

		achievementGrid = new ScrollableGrid((int) (this.width / 5.8), (int) (this.height * .25), (int) (this.width * .65),
				(int) (this.height * .62), 45, 45, entries);
		achievementGrid.setVisibleBackground(false);
		this.registerComponent(achievementGrid);

		this.registerComponent(
				new TextBox((int) (this.width * .2), (int) (this.height * .15), this.width / 2, 20, "Search for Achievement")
						.setId("achsearch").setTextChangedListener(
								(TextBox textbox, String previousText) -> textChanged(textbox, previousText)));
		// The background
		this.registerComponent(new Picture(this.width / 8, (int) (this.height * .05), (int) (this.width * (6.0 / 8.0)),
				(int) (this.height * .9), new ResourceLocation("dyn", "textures/gui/background3.png")));
	}

	private void textChanged(TextBox textbox, String previousText) {
		if (textbox.getId() == "achsearch") {
			achievementGrid.clear();
			if(textbox.getText().isEmpty()){
				for (AchievementPlus a : AchievementHandler.getAllAchievements()) {
					List<String> hoverText = new ArrayList();
					hoverText.add(a.getName());
					hoverText.add(a.getDescription());
					ResourceLocation imgTexture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
					achievementGrid.add(new PictureButtonGridEntry(25, 25, imgTexture).doesDrawHoverText(true).setHoverText(hoverText)
							.setClickListener((PictureButtonGridEntry entry, Grid grid, int mouseX, int mouseY) -> this
									.getStage().display(new AchDisp(a, imgTexture))));
				}
			} else {
				for (AchievementPlus a : AchievementHandler.findAchievementsByName(textbox.getText())) {
					List<String> hoverText = new ArrayList();
					hoverText.add(a.getName());
					hoverText.add(a.getDescription());
					ResourceLocation imgTexture = new ResourceLocation("minecraft", "textures/items/experience_bottle.png");
					achievementGrid.add(new PictureButtonGridEntry(25, 25, imgTexture).doesDrawHoverText(true).setHoverText(hoverText)
							.setClickListener((PictureButtonGridEntry entry, Grid grid, int mouseX, int mouseY) -> this
									.getStage().display(new AchDisp(a, imgTexture))));
				}
			}
		}
	}
}
