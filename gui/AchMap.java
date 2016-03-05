package com.dyn.achievements.gui;

import java.util.HashMap;
import java.util.Map;

import com.dyn.achievements.handlers.StringMap;
import com.dyn.achievements.handlers.StringPlus;
import com.rabbit.gui.background.DefaultBackground;
import com.rabbit.gui.component.display.Picture;
import com.rabbit.gui.show.Show;

import net.minecraft.util.ResourceLocation;
import net.vivin.GenericTree;
import net.vivin.GenericTreeNode;

public class AchMap extends Show {

	// we should have an idea how many levels there are and the number of nodes
	// per level
	private Map<Integer, Integer> levels = new HashMap<Integer, Integer>();

	public AchMap(StringMap map) {
		this.setBackground(new DefaultBackground());
		this.title = "Achievement Map Gui";
		for (GenericTree<StringPlus> t : map.getTrees()) {
			t.getNumberOfNodes();
			this.recursiveCount(0, t.getRoot());
		}
	}

	private void recursiveCount(int level, GenericTreeNode<StringPlus> nodes) {
		if (nodes.hasChildren()) {
			for (GenericTreeNode<StringPlus> node : nodes.getChildren()) {
				this.recursiveCount(level + 1, node);
			}
		}
		if (this.levels.containsKey(level)) {
			this.levels.replace(level, this.levels.get(level), this.levels.get(level) + 1);
		} else {
			// we have to assume there is something on this level
			this.levels.put(level, 1);
		}
	}

	@Override
	public void setup() {
		super.setup();

		// The background
		this.registerComponent(new Picture(this.width / 8, (int) (this.height * .05), (int) (this.width * (6.0 / 8.0)),
				(int) (this.height * .9), new ResourceLocation("dyn", "textures/gui/background3.png")));
	}

}
