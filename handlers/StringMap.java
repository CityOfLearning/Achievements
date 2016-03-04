package com.dyn.achievements.handlers;

import java.util.ArrayList;
import java.util.List;

import net.vivin.GenericTree;
import net.vivin.GenericTreeNode;

public class StringMap {
	private List<StringPlus> tStrings;
	private List<GenericTree<StringPlus>> trees = new ArrayList();
	private int id;

	public StringMap() {
		id = 0;
		tStrings = new ArrayList();
	}

	public StringMap(int id, List<StringPlus> strs) {
		this.id = id;
		this.tStrings = strs;
	}

	public void addString(StringPlus a) {
		this.tStrings.add(a);
	}

	public void addStrings(List<StringPlus> a) {
		this.tStrings.addAll(a);
	}

	public void removeString(StringPlus a) {
		this.tStrings.remove(a);
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	public List<GenericTree<StringPlus>> getTrees(){
		return this.trees;
	}

	public void processMap() {
		// we need to know where to place achievements in the mapping.
		// Are they independent? i.e have no parent or children
		// are they the top level parent i.e no parents but children
		// or are they a mid to last level child node
		// there may be cases a node has a parent not in this tree, its then an
		// orphan

		/*
		 * We're building a tree that looks like this:
		 * 
		 * I am root! /\ A B / \ C D \ E
		 */

		/*
		 * All Independents are root All Orphans are root Some Parents can be
		 * root
		 * 
		 * All children have 1 parent parents can have multiple children
		 * children can be parents
		 * 
		 */

		List<StringPlus> independent = new ArrayList();
		List<StringPlus> subnodes = new ArrayList();
		List<StringPlus> children = new ArrayList();
		List<StringPlus> roots = new ArrayList();
		List<StringPlus> orphans = new ArrayList();

		for (StringPlus a : this.tStrings) {
			// first lets sort them in possible parents and children
			if (a.hasParent()) {
				children.add(a);
			} else {
				independent.add(a);

			}
		}

		for (StringPlus a : children) {
			if (independent.contains(a.getParent())) {
				// we need to check if a root has a child
				if (!roots.contains(a.getParent())) {
					roots.add(a.getParent());
				}
			} else if (children.contains(a.getParent())) {
				// this is a sub node
				subnodes.add(a.getParent());
				// children.remove(a.getParent());
				// do we do something here?
			} else {
				// else we have an orphan
				orphans.add(a);
				GenericTree<StringPlus> tree = new GenericTree<StringPlus>();
				GenericTreeNode<StringPlus> root = new GenericTreeNode<StringPlus>(a);
				tree.setRoot(root);
				System.out.println("Adding Orphan " + a);
				this.trees.add(tree);
			}
		}

		independent.removeAll(roots);

		children.removeAll(subnodes);
		children.removeAll(orphans);

		for (StringPlus a : independent) {
			GenericTree<StringPlus> tree = new GenericTree<StringPlus>();
			GenericTreeNode<StringPlus> root = new GenericTreeNode<StringPlus>(a);
			tree.setRoot(root);
			System.out.println("Adding Independent " + a);
			this.trees.add(tree);
		}

		// so we have sorted out the orphans and independent achievements
		// now we have to link all the parents and children

		for (StringPlus a : roots) {
			GenericTree<StringPlus> tree = new GenericTree<StringPlus>();
			GenericTreeNode<StringPlus> root = new GenericTreeNode<StringPlus>(a);
			for (StringPlus b : children) {
				recursiveBuildNode(root, b, new GenericTreeNode<StringPlus>());
			}
			System.out.println("Adding Tree " + a + " with nodes " + root.getChildren());
			tree.setRoot(root);
			this.trees.add(tree);
		}

		System.out.println("Independents");
		for (StringPlus s : independent) {
			System.out.println(s);
		}
		System.out.println("Sub-Nodes");
		for (StringPlus s : subnodes) {
			System.out.println(s);
		}
		System.out.println("Children");
		for (StringPlus s : children) {
			System.out.println(s);
		}
		System.out.println("Roots");
		for (StringPlus s : roots) {
			System.out.println(s);
		}

		System.out.println("Trees");
		for (GenericTree<StringPlus> t : this.trees) {
			System.out.println(t + " - Nodes: " + t.getNumberOfNodes());
		}
		
		System.out.println("Checking Trees Makeup");
		for (GenericTree<StringPlus> t : this.trees) {
			System.out.println("Root");
			System.out.println(t.getRoot().getData());
			System.out.println("Children");
			for( GenericTreeNode<StringPlus> tc : t.getRoot().getChildren()){
				System.out.println(tc.getData() + "->" + tc.getChildren());
			}
		}
		System.out.println("Done");
	}
	
	private void recursiveBuildNode(GenericTreeNode<StringPlus> root, StringPlus node, GenericTreeNode<StringPlus> nodes) {
		if (node.hasParent()) {
			GenericTreeNode<StringPlus> newNode = new GenericTreeNode<StringPlus>();
			nodes.setData(node);
			newNode.addChild(nodes);
			recursiveBuildNode(root, node.getParent(), newNode);
			if(node.getParent() == root.getData()){
				//nodes.setData(node);
				root.addChild(nodes);
				System.out.println(nodes);
			} /*else {
				System.out.println(nodes);
				nodes.addChild(new GenericTreeNode<StringPlus>(node));
			}*/
		}
	}
}
