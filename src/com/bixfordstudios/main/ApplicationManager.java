package com.bixfordstudios.main;

import static org.lwjgl.glfw.Callbacks.errorCallbackPrint;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwTerminate;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL11;

import com.bixfordstudios.game.GameManager;
import com.bixfordstudios.gamestate.State;
import com.bixfordstudios.gamestate.StateManager;
import com.bixfordstudios.input.InputManager;
import com.bixfordstudios.render.GraphicsManager;
import com.bixfordstudios.window.WindowManager;
import com.bixfordstudios.world.WorldManager;

public class ApplicationManager
{
	private static GLFWErrorCallback errorCallback;
	
	public static void main(String[] args)
	{
		try
		{
			init();
			
			StateManager.changeState(State.MAINSCREEN);
			// Mainscreen
			
			GameManager.loop();
		}
		finally
		{
			exit();
		}
	}
	
	/**
	 * Sets up general application framework and initializations.<p>
	 * Dependents: None
	 */
	public static void init()
	{
		// Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
        
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
        
        // Initialize all other managers (order specific)
		WindowManager.init();
		StateManager.init();
		InputManager.init();
		GameManager.init();
		WorldManager.init();
		GraphicsManager.init();
	}
	
	/**
	 * Closes out each framework to close the application.
	 */
	public static void exit()
	{
		// Exit all other managers
		InputManager.exit();
		WindowManager.exit();
		StateManager.exit();
		GameManager.exit();
		WorldManager.exit();
		GraphicsManager.exit();
		
		// ApplicationManager exit
		glfwTerminate();
		errorCallback.release();
	}
}
