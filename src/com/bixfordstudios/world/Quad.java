package com.bixfordstudios.world;

import com.bixfordstudios.render.Texture;
import com.bixfordstudios.render.TextureVertex;
import com.bixfordstudios.utility.Coord2i;


public class Quad extends WorldObject
{
	public static final int VERTEX_COUNT = 6;
	public static final float SIDE_LENGTH = 1f;
	public Texture ALL_SIDE_TEXTURE;
	
	public Quad()
	{
		// Random Texturing
		this.ALL_SIDE_TEXTURE = Texture.values()[(int) Math.random() * Texture.values().length];
	}
	
	/**
	 * Processes and returns all vertices to draw this object
	 * @param coordinate World Coordinate of shape's origin (lower-left vertex)
	 * @return ArrayList of {@link TextureVertex} containing all data needed to draw this shape
	 */
	@Override
	public TextureVertex[] getVertices(Coord2i coordinate)
	{
		TextureVertex[] ret = new TextureVertex[VERTEX_COUNT];
		
		// Vertex wind counter clock-wise
		
		// First Triangle
		TextureVertex v0 = new TextureVertex();
		v0.setTexture(ALL_SIDE_TEXTURE); v0.setXYZ(coordinate.x + SIDE_LENGTH, coordinate.y + SIDE_LENGTH, 0); v0.setST(1, 1); // Upper-Right
		ret[0] = v0;
		
		TextureVertex v1 = new TextureVertex();
		v1.setTexture(ALL_SIDE_TEXTURE); v1.setXYZ(coordinate.x, coordinate.y + SIDE_LENGTH, 0); v1.setST(0, 1); // Upper-Left
		ret[1] = v1;
		
		TextureVertex v2 = new TextureVertex();
		v2.setTexture(ALL_SIDE_TEXTURE); v2.setXYZ(coordinate.x + SIDE_LENGTH, coordinate.y, 0); v2.setST(1, 0); // Lower-Right
		ret[2] = v2;
		
		// Second Triangle
		TextureVertex v3 = new TextureVertex();
		v3.setTexture(ALL_SIDE_TEXTURE); v3.setXYZ(coordinate.x + SIDE_LENGTH, coordinate.y, 0); v3.setST(1, 0); // Lower-Right
		ret[3] = v3;
		
		TextureVertex v4 = new TextureVertex();
		v4.setTexture(ALL_SIDE_TEXTURE); v4.setXYZ(coordinate.x, coordinate.y + SIDE_LENGTH, 0); v4.setST(0, 1); // Upper-Left
		ret[4] = v4;
		
		TextureVertex v5 = new TextureVertex();
		v5.setTexture(ALL_SIDE_TEXTURE); v5.setXYZ(coordinate.x, coordinate.y, 0); v5.setST(0, 0); // Lower-Left
		ret[5] = v5;
		
		return ret;
	}	
}
