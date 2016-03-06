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
	
	private StringMap achMap;
	//we should have an idea how many levels there are and the number of nodes per level
	private Map<Integer, Integer> levels = new HashMap();
	private int total;

	public AchMap(StringMap map) {
		this.setBackground(new DefaultBackground());
		this.title = "Achievement Map Gui";
		this.achMap = map;
		total=0;
		for( GenericTree<StringPlus> t : map.getTrees()){
			total += t.getNumberOfNodes();
			recursiveCount(0, t.getRoot());
		}
		System.out.println(total);
		for(int i =0;i<levels.size();i++){
			System.out.println("Level - " + i + " total nodes: " + levels.get(i));
		}
	}

	@Override
	public void setup() {
		super.setup();

		

		// The background
		this.registerComponent(new Picture(this.width / 8, (int) (this.height * .05), (int) (this.width * (6.0 / 8.0)),
				(int) (this.height * .9), new ResourceLocation("dyn", "textures/gui/background3.png")));
	}
	
	private void recursiveCount(int level, GenericTreeNode<StringPlus> nodes) {
		if (nodes.hasChildren()) {
			for(GenericTreeNode<StringPlus> node: nodes.getChildren()){
				recursiveCount(level+1, node);
			}
		}
		if(levels.containsKey(level)){
			levels.replace(level, levels.get(level), levels.get(level)+1);
		} else {
			//we have to assume there is something on this level
			levels.put(level, 1);
		}
	}

}
