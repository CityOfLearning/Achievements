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
/**
 * Class extends show for GUI.
 * @author Dominic Amato
 * @version 1.0
 * @since 2016-03-06
 */
public class AchMap extends Show {
	/**
	 * Map of strings.
	 */
	private StringMap achMap;
	/**
	 * Map of levels of achievements.
	 * We should have an idea how many levels there are and the number of nodes per level
	 */
	private Map<Integer, Integer> levels = new HashMap();
	/**
	 * Total amount of achievement nodes.
	 */
	private int total;

	/**
	 * Constructor for AchMap.
	 * Sets variables to default values.
	 * @param map Takes in a map of strings.
	 */
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

	/**
	 * Sets up GUI Screen for displaying achievement map.
	 */
	@Override
	public void setup() {
		super.setup();

		

		// The background
		this.registerComponent(new Picture(this.width / 8, (int) (this.height * .05), (int) (this.width * (6.0 / 8.0)),
				(int) (this.height * .9), new ResourceLocation("dyn", "textures/gui/background3.png")));
	}
	
	/**
	 * Recursively counts how many levels a node has.
	 * @param level
	 * @param nodes
	 */
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
