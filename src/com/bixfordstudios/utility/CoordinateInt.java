package com.bixfordstudios.utility;

public class CoordinateInt {

	public int x;
	public int y;
	public int z;
	
	public CoordinateInt()
	{
		this(0, 0, 0);
	}
	
	public CoordinateInt(int x, int y, int z)
	{
		this.x = x;
		this.y = y; 
		this.z = z;
	}
	
	public static float distance(CoordinateInt coord1, CoordinateInt coord2)
	{
		return (float) Math.sqrt((coord2.x - coord1.x) * (coord2.x - coord1.x) + (coord2.y - coord1.y) * (coord2.y - coord1.y) + (coord2.z - coord1.z) * (coord2.z - coord1.z));
	}
	
	public static float distance(CoordinateFloat coord1, CoordinateFloat coord2)
	{
		return (float) Math.sqrt((coord2.x - coord1.x) * (coord2.x - coord1.x) + (coord2.y - coord1.y) * (coord2.y - coord1.y) + (coord2.z - coord1.z) * (coord2.z - coord1.z));
	}
	
	public int hashCode()
	{
		return ((this.x * 31 + this.y) * 31 + this.z);
		
	}
	
	public boolean equals(Object ob)
	{
		if(ob==null) return false;
		if(!(ob.getClass() == getClass())) return false;
		
		CoordinateInt other = (CoordinateInt) ob;
		if (other.x == this.x && other.y == this.y && other.z == this.z)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public String toString()
	{
		return this.x +", "+ this.y +", "+ this.z;
	}
}
