package com.bixfordstudios.utility;


public class Coord2i {

	public int x;
	public int y;
	
	public Coord2i()
	{
		this(0, 0);
	}
	
	public Coord2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Coord2i(Coord2i coordinate)
	{
		this.x = coordinate.x;
		this.y = coordinate.y;
	}
	
	public Coord2i add(int xAddend, int yAddend)
	{
		return new Coord2i((this.x + xAddend), (this.y + yAddend));
	}
	
	public static float distance(Coord2i coord1, Coord2i coord2)
	{
		return (float) Math.sqrt((coord2.x - coord1.x) * (coord2.x - coord1.x) + (coord2.y - coord1.y) * (coord2.y - coord1.y));
	}
	
	public static float distance(Coord2f coord1, Coord2f coord2)
	{
		return (float) Math.sqrt((coord2.x - coord1.x) * (coord2.x - coord1.x) + (coord2.y - coord1.y) * (coord2.y - coord1.y));
	}
	
	public int[] toArray()
	{
		return new int[] {this.x, this.y};
	}
	
	/**
	 * Puts the coordinates stored in this coordinate into an array where x is index 0 and y is index 1
	 * @return An array -- [x, y]
	 */
	public static int[] toArray(Coord2i[] array)
	{
		int[] ret = new int[array.length * 2];
		int i = 0;
		while ( i < array.length * 2 )
		{
			ret[i] = array[i].x;
			ret[i+i] = array[i].y;
			i += 2;
		}
		return ret;
	}
	
	/**
	 * Uses bijective algorithm to generate unique value
	 */
	public int hashCode()
	{
		return this.x + (this.y + ( (( this.x + 1 ) / 2 ) * (( this.x + 1 ) / 2 ) ) );
		
	}
	
	public boolean equals(Object ob)
	{
		if(ob==null) return false;
		if(!(ob.getClass() == getClass())) return false;
		
		Coord2i other = (Coord2i) ob;
		if (other.x == this.x && other.y == this.y)	return true;
		else return false;
	}
	
	public String toString()
	{
		return this.x +", "+ this.y;
	}
}
