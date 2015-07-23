package com.bixfordstudios.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.lwjgl.BufferUtils;

import com.bixfordstudios.utility.Coord2i;
import com.bixfordstudios.world.WorldObject;

/**
 * Manages the rendering and shaders
 * @author DarkEther
 *
 */
public class GraphicsManager
{	
	private static HashMap<Texture, ArrayList<TextureVertex>> renderQueue = new HashMap<Texture, ArrayList<TextureVertex>>();
	
	private static int vao;
	private static int vbo;
	
	private static int shaderProgram;
	private static int vertexShader;
	private static int fragmentShader;
	
	private static final String vertexShaderString =
			"#version 150 core\r\n" + 
			"\r\n" + 
			"in vec2 position;\r\n" + 
			"in vec3 color;\r\n" + 
			"in vec2 texcoord;\r\n" + 
			"\r\n" + 
			"out vec3 vertexColor;\r\n" + 
			"out vec2 textureCoord;\r\n" + 
			"\r\n" + 
			"uniform mat4 model;\r\n" + 
			"uniform mat4 view;\r\n" + 
			"uniform mat4 projection;\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"    vertexColor = color;\r\n" + 
			"    textureCoord = texcoord;\r\n" + 
			"    mat4 mvp = projection * view * model;\r\n" + 
			"    gl_Position = mvp * vec4(position, 0.0, 1.0);"
			+ "}";
	
	private static final String fragmentShaderString =
			"#version 150 core\r\n" + 
			"\r\n" + 
			"in vec3 vertexColor;\r\n" + 
			"in vec2 textureCoord;\r\n" + 
			"\r\n" + 
			"out vec4 fragColor;\r\n" + 
			"\r\n" + 
			"uniform sampler2D texImage;\r\n" + 
			"\r\n" + 
			"void main() {\r\n" + 
			"    vec4 textureColor = texture(texImage, textureCoord);\r\n" + 
			"    fragColor = vec4(vertexColor, 1.0) * textureColor;\r\n" + 
			"}";
	
	/**
	 * Sets up and compiles shaders and shader program
	 */
	public static void init()
	{
		// Create vertex shader
		vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, vertexShaderString);
		glCompileShader(vertexShader);
		
		int vertexStatus = glGetShaderi(vertexShader, GL_COMPILE_STATUS);
		if (vertexStatus != GL_TRUE) 
		{
		    throw new RuntimeException(glGetShaderInfoLog(vertexStatus));
		}
		
		// Create fragment shader
		fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, fragmentShaderString);
		glCompileShader(fragmentShader);
		
		int fragmentStatus = glGetShaderi(fragmentShader, GL_COMPILE_STATUS);
		if (fragmentStatus != GL_TRUE) 
		{
		    throw new RuntimeException(glGetShaderInfoLog(fragmentShader));
		}
		
		// Create Shader Program
		shaderProgram = glCreateProgram();
		glAttachShader(shaderProgram, vertexShader);
		glAttachShader(shaderProgram, fragmentShader);
		
		// Position information will be attribute 0
		glBindAttribLocation(shaderProgram, 0, "position");
		// Color information will be attribute 1
		glBindAttribLocation(shaderProgram, 1, "color");
		// Texture information will be attribute 2
		glBindAttribLocation(shaderProgram, 2, "texcoord");
		
		// Texture image will be uniform 0
		// int uniTex = glGetUniformLocation(shaderProgram, "texImage");
		// glSetUniform(uniTex, 0);
		
		glLinkProgram(shaderProgram);
		glValidateProgram(shaderProgram);
				
		int programStatus = glGetProgrami(shaderProgram, GL_LINK_STATUS);
		if (programStatus != GL_TRUE) 
		{
		    throw new RuntimeException(glGetProgramInfoLog(shaderProgram));
		}
		
		for (Texture texture : Texture.values())
		{
			renderQueue.put(texture, new ArrayList<TextureVertex>());
		}
	}
	
	public static void exit()
	{
		
	}
	
	/**
	 * This will set up a vbo with the proper shaders and index points
	 * @param vbo
	 */
	public static void setupBuffer(int vbo)
	{
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
	}
	
	/**
	 * Adds a WorldObject to the cache to be rendered
	 * @param wo A WorldObject to render
	 */
	public static void add(Coord2i worldLocation, WorldObject worldObject)
	{
		TextureVertex[] vertexList = worldObject.getVertices(worldLocation);
		
		// All Textures will be the same for each object; just read the first one
		Texture texture = vertexList[0].getTexture();
		
		for (TextureVertex vertex : vertexList)
		{
			renderQueue.get(texture).add(vertex);
		}
	}
	
	public static int numTextureVertex()
	{
		int ret = 0;
		for (Entry<Texture, ArrayList<TextureVertex>> entry : renderQueue.entrySet())
		{
			ret += entry.getValue().size();
		}
		return ret;
	}
	
	/**
	 * Pushes all objects setup in the manager's rendering list to be drawn by OpenGL to screen. 
	 */
	public static void render()
	{
		FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(numTextureVertex() * TextureVertex.ELEMENT_COUNT * Float.BYTES);
				
		for (ArrayList<TextureVertex> textureArray : renderQueue.values())
		{
			for (TextureVertex texture : textureArray)
			{
				verticesBuffer.put(texture.getElementData());
			}
		}
		
		
		verticesBuffer.flip();
		
		// Create a new Vertex Array Object in memory and select it (bind it)
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		// Create a new vertex Buffer Object in memory and select it (bind)
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		
		// Attributes
		// Put the position coordinates in attribute list 0
		glVertexAttribPointer(0, TextureVertex.POSITION_ELEMENT_COUNT, GL_FLOAT, false, TextureVertex.STRIDE, TextureVertex.POSITION_BYTE_OFFSET);
		// Put the color components in attribute list 1
		glVertexAttribPointer(1, TextureVertex.COLOR_ELEMENT_COUNT, GL_FLOAT, false, TextureVertex.STRIDE, TextureVertex.COLOR_BYTE_OFFSET);
		// Put the texture coordinates in attribute list 2
		glVertexAttribPointer(2, TextureVertex.TEXTURE_ELEMENT_COUNT, GL_FLOAT, false, TextureVertex.STRIDE, TextureVertex.TEXTURE_BYTE_OFFSET);
		
		glClear(GL_COLOR_BUFFER_BIT);
		glDrawArrays(GL_TRIANGLES, 0, numTextureVertex());
		
		// Deselect the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		// Deselect the VAO
		glBindVertexArray(0);
	}
	
	/**
	 * Resets the cached list of worldobjects to be rendered
	 */
	public static void clear()
	{
		renderQueue.clear();
		
		for (Texture texture : Texture.values())
		{
			renderQueue.put(texture, new ArrayList<TextureVertex>());
		}
	}
}
