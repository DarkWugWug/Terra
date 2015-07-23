package com.bixfordstudios.camera;

import java.util.ArrayList;

import com.bixfordstudios.utility.Coord2f;
import com.bixfordstudios.utility.Coord2i;

public class CameraManager
{
	private static final int VIEW_RADIUS = 1;
	
	// TO-DO: Assess if the possibility of two camera (i.e. two center values) should be implemented
	
	private static Coord2f center = new Coord2f();
	
	/**
	 * Adds the addends to the camera's position
	 * @param xTranslation X-Axis Translation
	 * @param yTranslation Y-Axis Translation
	 */
	public static void translate(float xTranslation, float yTranslation) {center = center.add(xTranslation, yTranslation);}
	
	/**
	 * Gets the position of the camera
	 * @return The position
	 */
	public static Coord2f getPosition() {return center;}
	
	/**
	 * Gets the coordinates of the world space that the camera's position is in
	 * @return The coordinate
	 */
	public static Coord2i getCoord() {return new Coord2i(Math.round(center.x), Math.round(center.y));}
	
	/**
	 * Gets all coordinates that are visible<br>
	 * Visible coordinates are all of those that are within the square area around the coordinate returned by {@link getCoord()} with side length defined by {@link VIEW_RADIUS}
	 * plus one. Allowing for the edge objects -- partially visible -- to be rendered.
	 * @return An array of Coord2i
	 */
	public static ArrayList<Coord2i> getVisibleCoords()
	{
		ArrayList<Coord2i> ret = new ArrayList<Coord2i>();
		
		for (int x = getCoord().x - VIEW_RADIUS - 1; x < (VIEW_RADIUS + 1) * 2; x++)
		{
			for (int y = getCoord().y - VIEW_RADIUS - 1; y < (VIEW_RADIUS + 1) * 2; y++)
			{
				ret.add(new Coord2i(x, y));
			}
		}
		return ret;
	}
	
	public static void init()
	{
		
	}
	
	public static void exit()
	{
		
	}
}
