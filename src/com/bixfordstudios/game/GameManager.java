package com.bixfordstudios.game;

import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.glClear;

import org.lwjgl.opengl.GLContext;

import com.bixfordstudios.camera.CameraManager;
import com.bixfordstudios.gamestate.State;
import com.bixfordstudios.gamestate.StateManager;
import com.bixfordstudios.input.InputManager;
import com.bixfordstudios.window.WindowManager;
import com.bixfordstudios.world.WorldManager;

/**
 * Static manager class
 * @author DarkEther
 *
 */
public class GameManager
{
	
	public static final float TARGET_UPS = 20f;
	
	private static float delta;
	private static float accumulator = 0f;
	private static float interval = 1f / TARGET_UPS;
	private static float alpha;
	
	/**
	 * Main game loop.
	 */
	public static void loop()
	{
		StateManager.changeState(State.PAUSED);
		StateManager.changeState(State.RUNNING);
		// Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(WindowManager.getWindow()) == GL_FALSE ) {
        	delta = (float) glfwGetTime();
        	accumulator += delta;
        	
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            
            glfwSwapBuffers(WindowManager.getWindow()); // swap the color buffers
            
            InputManager.update();
            
            while (accumulator >= interval)
            {
            	update(1f / TARGET_UPS);
            	accumulator -= interval;
            }
            
            alpha = accumulator / interval;
            render(alpha);
        }
        //State Change: ENDED , MAINSCREEN (?)
	}
	
	/**
	 * Game update that should be called {@link TARGET_UPS} times per second.
	 * @param delta Time passed between updates
	 */
	public static void update(float delta)
	{
		WorldManager.render();
	}
	
	/**
	 * Render update that should be called {@link TARGET_FPS} times per second.
	 * @param delta
	 */
	public static void render(float delta)
	{
		
	}
	
	public static void init()
	{
		// Initializes OpenGL context to start rendering
		glfwMakeContextCurrent(WindowManager.getWindow());
		
		// This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
		GLContext.createFromCurrent();

        // Enable v-sync
        glfwSwapInterval(1);
		
		// Set the clear color
        // glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	}
	
	public static void exit()
	{
		
	}
}
