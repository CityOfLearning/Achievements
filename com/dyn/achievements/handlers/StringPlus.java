package com.dyn.achievements.handlers;
/**
 * @author Dominic Amato
 * @version 1.0
 * @since 2016-03-06
 */
public class StringPlus {
	/**
	 * Instance of class to link achievements to parent achievement.
	 */
	private StringPlus parent;
	/**
	 * Description of achievement.
	 */
	private String val;

	/**
	 * Constructor of class to set values to default. 
	 * Sets parent to null and val to empty string.
	 */
	public StringPlus() {
		this.parent = null;
		this.val = "";
	}

	/**
	 * Constructor of class that can set the variables to what the user wants.
	 * Sets parent to p and val to v
	 * @param p
	 * @param v
	 */
	public StringPlus(StringPlus p, String v) {
		this.parent = p;
		this.val = v;
	}

	/**
	 * Checks if the achievement has a parent.
	 * @return True if parent is not null and false if parent is null.
	 */
	public boolean hasParent() {
		return this.parent != null;
	}

	/**
	 * Gets the parent.
	 * @return Returns parent variable.
	 */
	public StringPlus getParent() {
		return this.parent;
	}
	
	/**
	 * Gets the val string.
	 * @return Returns val variable.
	 */
	@Override
	public String toString(){
		return this.val;
	}

}