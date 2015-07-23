package com.bixfordstudios.utility;

public class Coord2f
{
	public float x, y;
	
	public Coord2f()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public Coord2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public static float distance(Coord2f coord1, Coord2f coord2)
	{
		return (float) Math.sqrt((coord2.x - coord1.x) * (coord2.x - coord1.x) + (coord2.y - coord1.y) * (coord2.y - coord1.y));
	}
	
	public Coord2f add(float addendX, float addendY) {return new Coord2f(this.x + addendX, this.y + addendY);}
	
	/**
	 * Rounds the floats to integers
	 * @return A Coord2i
	 */
	public Coord2i round() {return new Coord2i(Math.round(this.x), Math.round(this.y));}
	
	public Coord2f scale(float scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
		return this;
	}
	
	public String toString() {return this.x +", "+ this.y;}
}
