package com.bixfordstudios.world;

import com.bixfordstudios.render.TextureVertex;
import com.bixfordstudios.utility.Coord2i;

/**
 * Generic world render object
 * @author rosen.231
 *
 */
public abstract class WorldObject
{	
	/**
	 * Processes and returns all vertices to draw this object
	 * @param coordinate World Coordinate of shape's origin
	 * @return ArrayList of {@link TextureVertex} containing all data needed to draw this shape
	 */
	public abstract TextureVertex[] getVertices(Coord2i coordinate);
	
	/*
	 * (0,0) at the bottom left
	 * (1,0) at the bottom right
	 * (1,1) at the top right
	 * (0,1) at the top left
	 */
}
