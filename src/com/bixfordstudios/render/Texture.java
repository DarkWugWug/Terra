package com.bixfordstudios.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public enum Texture
{
	BLACK("res/tex/black.png"),
	BLUE("res/tex/blue.png"),
	BROWN("res/tex/brown.png"),
	GREEN("res/tex/green.png"),
	GREY("res/tex/grey.png"),
	PURPLE("res/tex/purple.png"),
	RED("res/tex/red.png"),
	WHITE("res/tex/white.png");
	
	public int textureID;
	
	Texture(String file)
	{
		this.textureID = loadPNGTexture(file, GL_VERTEX_SHADER);			
	}
	
	private static int loadPNGTexture(String filename, int textureUnit)
	{
		int textureID = 0;
		try
		{
			// Load in the image
			InputStream in = new FileInputStream(new File(filename));
			BufferedImage image = ImageIO.read(in);
			
			//Flip the image so that the origin is the bottom left not the top left
			AffineTransform transform = AffineTransform.getScaleInstance(1f, -1f);
			transform.translate(0, -image.getHeight());
			AffineTransformOp operation = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
			image = operation.filter(image, null);
			
			int width = image.getWidth();
			int height = image.getHeight();
			
			int[] pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
			
			ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * Float.BYTES);

			for (int y = 0; y < height; y++) {
			    for (int x = 0; x < width; x++) {
			        /* Pixel as RGBA: 0xAARRGGBB */
			        int pixel = pixels[y * width + x];

			        /* Red component 0xAARRGGBB >> (4 * 4) = 0x0000AARR */
			        buffer.put((byte) ((pixel >> 16) & 0xFF));

			        /* Green component 0xAARRGGBB >> (2 * 4) = 0x00AARRGG */
			        buffer.put((byte) ((pixel >> 8) & 0xFF));

			        /* Blue component 0xAARRGGBB >> 0 = 0xAARRGGBB */
			        buffer.put((byte) (pixel & 0xFF));

			        /* Alpha component 0xAARRGGBB >> (6 * 4) = 0x000000AA */
			        buffer.put((byte) ((pixel >> 24) & 0xFF));
			    }
			}

			/* Do not forget to flip the buffer! */
			buffer.flip();
			
			// Create a new texture object in memory and bind it
			textureID = glGenTextures();
			glActiveTexture(textureUnit);
			glBindTexture(GL_TEXTURE_2D, textureID);
			
			// All RGB bytes are aligned to each other and each component is 1 byte
			glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
			
			// Upload the texture data and generate mip maps (for scaling)			
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			glGenerateMipmap(GL_TEXTURE_2D);
			
			// Setup the ST coordinate system
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			
			// Setup what to do when the texture has to be scaled
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return textureID;
	}
}



