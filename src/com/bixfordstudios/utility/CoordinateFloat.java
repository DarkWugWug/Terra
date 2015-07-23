package com.bixfordstudios.utility;

public class CoordinateFloat
{
	public float x, y, z;
	
	public CoordinateFloat()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	public CoordinateFloat(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public static float distance(CoordinateFloat coord1, CoordinateFloat coord2)
	{
		return (float) Math.sqrt((coord2.x - coord1.x) * (coord2.x - coord1.x) + (coord2.y - coord1.y) * (coord2.y - coord1.y) + (coord2.z - coord1.z) * (coord2.z - coord1.z));
	}
	
	/**
	 * Rounds the floats to a thousandth (-.--)
	 * @return This object
	 */
	public CoordinateFloat round()
	{
		this.x =  ((float)((int)(this.x * 100))) / 100;
		this.y =  ((float)((int)(this.y * 100))) / 100;
		this.z =  ((float)((int)(this.z * 100))) / 100;
		return this;
	}
	
	public CoordinateFloat scale(float scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		return this;
	}
	
	public String toString()
	{
		return this.x +", "+ this.y +", "+ this.z;
	}
}
