package com.bixfordstudios.render;


/**
 * Class to standardize textured coordinates
 * @author rosen.231
 *
 */
public class TextureVertex
{
	private com.bixfordstudios.render.Texture texture;
	
	// Vertex Data
	private float[] xyzw = new float[] {0f, 0f, 0f, 0f};
	private float[] rgba = new float[] {1f, 1f, 1f, 1f};
	private float[] st = new float[] {0f, 0f};
	
	// The amount of bytes an element has
	public static final int ELEMENT_BYTES = Float.BYTES;
	
	// Elements per parameter
	public static final int POSITION_ELEMENT_COUNT = 4;
	public static final int COLOR_ELEMENT_COUNT = 4;
	public static final int TEXTURE_ELEMENT_COUNT = 2;
	
	// Bytes per parameter
	public static final int POSITION_BYTE_COUNT = POSITION_ELEMENT_COUNT * ELEMENT_BYTES;
	public static final int COLOR_BYTE_COUNT = COLOR_ELEMENT_COUNT * ELEMENT_BYTES;
	public static final int TEXTURE_BYTE_COUNT = TEXTURE_ELEMENT_COUNT * ELEMENT_BYTES;
	
	// Byte offset per parameter
	public static final int POSITION_BYTE_OFFSET= 0;
	public static final int COLOR_BYTE_OFFSET = POSITION_BYTE_OFFSET + POSITION_BYTE_COUNT;
	public static final int TEXTURE_BYTE_OFFSET = COLOR_BYTE_OFFSET + COLOR_BYTE_COUNT;
	
	// The amount of elements that a vertex has
	public static final int ELEMENT_COUNT = POSITION_ELEMENT_COUNT + COLOR_ELEMENT_COUNT + TEXTURE_ELEMENT_COUNT;
	
	// The size of a vertex in bytes
	public static final int STRIDE = POSITION_BYTE_COUNT + COLOR_BYTE_COUNT + TEXTURE_BYTE_COUNT;
	
	// Setters
	public void setXYZ(float x, float y, float z)
	{
		this.setXYZW(x, y, z, 1);
	}
	
	public void setRGB(float r, float g, float b)
	{
		this.setRGBA(r, g, b, 1f);
	}
	
	public void setST(float s, float t)
	{
		this.st = new float[] {s, t};
	}
	
	public void setXYZW(float x, float y, float z, float w)
	{
		this.xyzw = new float[] {x, y, z, w};
	}
	
	public void setRGBA(float r, float g, float b, float a)
	{
		this.rgba = new float[] {r, g, b, a};
	}
	
	public void setTexture(Texture tex)
	{
		this.texture = tex;
	}
	
	
	//Getters
	/**
	 * Returns the composite array of all vertices: [x, y, z, w, r, g, b, a, s, t]
	 * @return An array of all vertices
	 */
	public float[] getElementData()
	{
		float[] ret = new float[TextureVertex.ELEMENT_COUNT];
		int i = 0;
		
		// Insert XYZW elements
		ret[i++] = this.xyzw[0];
		ret[i++] = this.xyzw[1];
		ret[i++] = this.xyzw[2];
		ret[i++] = this.xyzw[3];
		// Insert RGBA elements
		ret[i++] = this.rgba[0];
		ret[i++] = this.rgba[1];
		ret[i++] = this.rgba[2];
		ret[i++] = this.rgba[3];
		// Insert ST elements
		ret[i++] = this.st[0];
		ret[i++] = this.st[1];
		
		return ret;
	}
	
	public float[] getXYZW()
	{
		return new float[] {this.xyzw[0], this.xyzw[1], this.xyzw[2], this.xyzw[3]};
	}
	
	public float[] getRGBA()
	{
		return new float[] {this.rgba[0], this.rgba[1], this.rgba[2], this.rgba[3]};
	}
	
	public float[] getST()
	{
		return new float[] {this.st[0], this.st[1]};
	}
	
	public Texture getTexture()
	{
		return this.texture;
	}
}
