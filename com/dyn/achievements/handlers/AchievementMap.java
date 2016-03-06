package com.dyn.achievements.handlers;

import java.util.ArrayList;
import java.util.List;

import com.dyn.achievements.achievement.AchievementPlus;

import net.vivin.GenericTree;
import net.vivin.GenericTreeNode;
/**
 * @author Dominic Amato
 * @version 1.0
 * @since 2016-03-06
 */
public class AchievementMap {
	/**
	 * List of achievements.
	 */
	private List<AchievementPlus> achievements;
	/**
	 * List of achievement trees.
	 */
	private List<GenericTree<AchievementPlus>> trees = new ArrayList();
	/**
	 * ID of map.
	 */
	private int id;
	
	/**
	 * Constructor of achievement map.
	 * Sets values to default.
	 */
	public AchievementMap(){
		id =0;
		achievements = new ArrayList();
	}
	
	/**
	 * Constructor of achievement map.
	 * Sets variables to parameters.
	 * @param id
	 * @param achs
	 */
	public AchievementMap(int id, List<AchievementPlus> achs){
		this.id = id;
		this.achievements = achs;
	}
	
	/**
	 * Adds an achievement to the list of achievements.
	 * @param a
	 */
	public void addAchievement(AchievementPlus a){
		this.achievements.add(a);
	}
	/**
	 * Adds multiple achievements to the list of achievements.
	 * @param a
	 */
	public void addAchievements(List<AchievementPlus> a){
		this.achievements.addAll(a);
	}
	
	/**
	 * Removes an achievement from the list of achievements.
	 * @param a
	 */
	public void removeAchievement(AchievementPlus a){
		this.achievements.remove(a);
	}
	
	/**
	 * Set the ID of achievement.
	 * @param id
	 */
	public void setId(int id){
		this.id = id;
	}
	
	/**
	 * Gets the ID of achievement.
	 * @return
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Gets the list of achievement trees.
	 * @return
	 */
	public List<GenericTree<AchievementPlus>> getTrees(){
		return this.trees;
	}
	
	/**
	 * Processes the map based of the achievements.
	 */
	public void processMap(){
		//we need to know where to place achievements in the mapping. 
		//Are they independent? i.e have no parent or children
		//are they the top level parent i.e no parents but children
		//or are they a mid to last level child node
		//there may be cases a node has a parent not in this tree, its then an orphan
		
		/*
		We're building a tree that looks like this:
		 
		I am root!
		 /\
		A  B
	   /    \
	  C 	 D
			  \
			   E
		*/
		
		/*
		 * All Independents are root
		 * All Orphans are root
		 * Some Parents can be root
		 * 
		 * All children have 1 parent
		 * parents can have multiple children
		 * children can be parents 
		 * 
		 */
		/**
		 * List of independent achievements.
		 */
		List<AchievementPlus> independent = new ArrayList();
		/**
		 * List of sub-node achievements.
		 */
		List<AchievementPlus> subnodes = new ArrayList();
		/**
		 * List of achievements that are children of other achievements.
		 */
		List<AchievementPlus> children = new ArrayList();
		/**
		 * List of achievements that are roots.
		 */
		List<AchievementPlus> roots = new ArrayList();
		/**
		 * List of achievements that are orphans.
		 */
		List<AchievementPlus> orphans = new ArrayList();

		/**
		 * Separates children from independent nodes in the achievements list.
		 */
		for (AchievementPlus a : this.achievements) {
			// first lets sort them in possible roots and children
			if (a.hasParent()) {
				children.add(a);
			} else {
				independent.add(a);

			}
		}
		/**
		 * Checks the children list and separates independent, root, subnode, and orphan achievement nodes.
		 */
		for (AchievementPlus a : children) {
			if (independent.contains(a.getParent())) {
				// we need to check if a root has a child
				if (!roots.contains(a.getParent())) {
					roots.add(a.getParent());
				}
			} else if (children.contains(a.getParent())) {
				// this is a sub node
				subnodes.add(a.getParent());
			} else {
				// else we have an orphan
				orphans.add(a);
				GenericTree<AchievementPlus> tree = new GenericTree<AchievementPlus>();
				GenericTreeNode<AchievementPlus> root = new GenericTreeNode<AchievementPlus>(a);
				tree.setRoot(root);
				this.trees.add(tree);
			}
		}

		/**
		 * Cleans up all the lists
		 */
		/**
		 * Removes root nodes from independent nodes.
		 */
		independent.removeAll(roots);
		/**
		 * Removes sub-nodes from child nodes.
		 */
		children.removeAll(subnodes);
		/**
		 * Removes orphan nodes from child nodes.
		 */
		children.removeAll(orphans);

		/**
		 * Adds independent nodes to the main tree.
		 */
		for (AchievementPlus a : independent) {
			GenericTree<AchievementPlus> tree = new GenericTree<AchievementPlus>();
			GenericTreeNode<AchievementPlus> root = new GenericTreeNode<AchievementPlus>(a);
			tree.setRoot(root);
			this.trees.add(tree);
		}

		// so we have sorted out the orphans and independent achievements
		// now we have to link all the parents and children
		/**
		 * Builds the tree including children of roots.
		 */
		for (AchievementPlus a : roots) {
			GenericTree<AchievementPlus> tree = new GenericTree<AchievementPlus>();
			GenericTreeNode<AchievementPlus> root = new GenericTreeNode<AchievementPlus>(a);
			for (AchievementPlus b : children) {
				List<GenericTreeNode<AchievementPlus>> rootList = new ArrayList();
				GenericTreeNode<AchievementPlus> recRoots = new GenericTreeNode<AchievementPlus>();
				recursiveBuild(b, rootList);
				//this is sorta janky but its the only method
				//pop the front of the list, that is the root node itself
				AchievementPlus tRoot = rootList.remove(0).getData();
				//you have to set the data or its null
				//generic tree isn't smart enough to promote first child
				//to root when root is null
				recRoots.setData(rootList.remove(0).getData());
				//you have to set the children from the list...
				//setting from a child node breaks linkage for some reason
				recRoots.setChildren(rootList);
				if (tRoot == a) {				
					root.addChild(recRoots);
				}
			}
			tree.setRoot(root);
			this.trees.add(tree);
		}	
	}
	/**
	 * Recursively builds the node tree based on if achievements have parents or children.
	 * @param root
	 * @param node
	 * @param nodes
	 */
	private void recursiveBuild(AchievementPlus node, List<GenericTreeNode<AchievementPlus>> nodes) {
		if (node.hasParent()) {
			recursiveBuild(node.getParent(), nodes);
		}

		nodes.add(new GenericTreeNode<AchievementPlus>(node));
	}
}
