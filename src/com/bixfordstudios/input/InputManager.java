package com.bixfordstudios.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.*;

import com.bixfordstudios.gamestate.State;
import com.bixfordstudios.gamestate.StateManager;
import com.bixfordstudios.window.WindowManager;

/**
 * Input manager classed that is only accessed statically and interprets input. Listens to the StateManager via {@link stateChange()}
 * @author DarkEther
 *
 */
public class InputManager
{	
	private static GLFWKeyCallback keyCallback;
	
	/**
	 * Sets up the default KeyCallback. <p>
	 * Dependents: WindowManager.init(), StateManager.init()
	 */
	public static void init()
	{
		glfwSetInputMode(WindowManager.getWindow(), GLFW_STICKY_KEYS, 1);
		updateKeyCallback();
	}
	
	/**
	 * Releases the keyCallbacks.
	 */
	public static void exit()
	{
		// Will need to release them all ( to be sure )
		for (GLFWKeyCallback callback : KeyboardHandler.keyboards.values())
		{
			callback.release();
		}
	}
	
	/**
	 * Resets the currently binded key bindings.
	 * @param curState State changed to
	 */
	public static void stateChange(State curState)
	{
		// Release
		keyCallback.release();
		
		// Revalue
		updateKeyCallback();
	}
	
	/**
	 * Called to interpret input.
	 */
	public static void update()
	{
		// Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();
	}
	
	public static void updateKeyCallback()
	{
		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(WindowManager.getWindow(), KeyboardHandler.keyboards.get(StateManager.getCurrent()));
		keyCallback = KeyboardHandler.keyboards.get(StateManager.getCurrent());
	}
}
