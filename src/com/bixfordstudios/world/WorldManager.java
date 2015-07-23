package com.bixfordstudios.world;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import com.bixfordstudios.camera.CameraManager;
import com.bixfordstudios.render.GraphicsManager;
import com.bixfordstudios.utility.Coord2i;

public class WorldManager
{	
	public static HashMap<Coord2i, WorldObject> world = new HashMap<Coord2i, WorldObject>();
	
	public static void init()
	{
		
	}
	
	public static void exit()
	{
		
	}
	
	/**
	 * Renders all objects in {@link WorldManager#renderQueue}
	 */
	public static void render()
	{	
		// Refresh the cached objects from the graphics manager
		GraphicsManager.clear();
		
		ArrayList<Coord2i> visibleObjects = CameraManager.getVisibleCoords();
		
		for (Coord2i coord : visibleObjects) 
		{ 
			GraphicsManager.add(coord, assertWorldObject(coord)); 
		}
		
		// Render all recently added visible objects
		GraphicsManager.render();
	}
	
	/**
	 * Returns the saved WorldObject that should be at this location or generates a new one
	 * @param coord A coordinate
	 * @return The corresponding WorldObject with this coordinate
	 */
	private static WorldObject assertWorldObject(Coord2i coord)
	{
		if (world.containsKey(coord)) {return world.get(coord);}
		else {return createWorldObject();}
	}
	
	/**
	 * Creates a new world object of the specific type that the game is running on. For now, there is no logic to this function and is only returning
	 * a new {@link Quad}
	 * @return A new WorldObject
	 */
	private static WorldObject createWorldObject() {return new Quad();}
	
	/**
	 * Save the world HashMap to file
	 * @param file File to save to
	 */
	private static void saveToFile(File file)
	{
		
	}
	
	/**
	 * Loads in the world HashMap from file
	 * @param file File to read from
	 */
	private static void loadFromFile(File file)
	{
		
	}
	
}
