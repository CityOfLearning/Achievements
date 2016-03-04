package com.dyn.achievements.handlers;

import java.util.ArrayList;
import java.util.List;

import com.dyn.achievements.achievement.AchievementPlus;

import net.vivin.GenericTree;
import net.vivin.GenericTreeNode;

public class AchievementMap {
	private List<AchievementPlus> achievements;
	private List<GenericTree<AchievementPlus>> trees = new ArrayList();
	private int id;
	
	public AchievementMap(){
		id =0;
		achievements = new ArrayList();
	}
	
	public AchievementMap(int id, List<AchievementPlus> achs){
		this.id = id;
		this.achievements = achs;
	}
	
	public void addAchievement(AchievementPlus a){
		this.achievements.add(a);
	}
	
	public void addAchievements(List<AchievementPlus> a){
		this.achievements.addAll(a);
	}
	
	public void removeAchievement(AchievementPlus a){
		this.achievements.remove(a);
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
	
	public List<GenericTree<AchievementPlus>> getTrees(){
		return this.trees;
	}
	
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
		
		List<AchievementPlus> independent = new ArrayList();
		List<AchievementPlus> subnodes = new ArrayList();
		List<AchievementPlus> children = new ArrayList();
		List<AchievementPlus> roots = new ArrayList();
		List<AchievementPlus> orphans = new ArrayList();

		for (AchievementPlus a : this.achievements) {
			// first lets sort them in possible roots and children
			if (a.hasParent()) {
				children.add(a);
			} else {
				independent.add(a);

			}
		}

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

		//lets clean up all the lists
		independent.removeAll(roots);
		children.removeAll(subnodes);
		children.removeAll(orphans);

		for (AchievementPlus a : independent) {
			GenericTree<AchievementPlus> tree = new GenericTree<AchievementPlus>();
			GenericTreeNode<AchievementPlus> root = new GenericTreeNode<AchievementPlus>(a);
			tree.setRoot(root);
			this.trees.add(tree);
		}

		// so we have sorted out the orphans and independent achievements
		// now we have to link all the parents and children
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
	
	private void recursiveBuild(AchievementPlus node, List<GenericTreeNode<AchievementPlus>> nodes) {
		if (node.hasParent()) {
			recursiveBuild(node.getParent(), nodes);
		}

		nodes.add(new GenericTreeNode<AchievementPlus>(node));
	}
}
