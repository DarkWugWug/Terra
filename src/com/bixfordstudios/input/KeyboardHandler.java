package com.bixfordstudios.input;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

import java.util.HashMap;

import org.lwjgl.glfw.GLFWKeyCallback;

import com.bixfordstudios.camera.CameraManager;
import com.bixfordstudios.gamestate.State;

/**
 * Keyboard manager classed that is only accessed statically and holds all key bindings.
 * @author DarkEther
 *
 */
public class KeyboardHandler
{
	public static HashMap<State, GLFWKeyCallback> keyboards = new HashMap<State, GLFWKeyCallback>();
	
	private static final float TRANSLATION_SPEED = 0.25f;
	
	static
	{
		//Loading Keys
		keyboards.put(State.LOADING,
			new GLFWKeyCallback() {
				@Override
				public void invoke(long window, int key, int scancode, int action, int mods)
				{
					if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
	                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop	
				}
			});
		
		//Mainscreen Keys
		keyboards.put(State.MAINSCREEN,
				new GLFWKeyCallback() {
					@Override
					public void invoke(long window, int key, int scancode, int action, int mods)
					{
						if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
		                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop	
					}
				});
		
		//Running Keys
		keyboards.put(State.RUNNING,
				new GLFWKeyCallback() {
					@Override
					public void invoke(long window, int key, int scancode, int action, int mods)
					{
						
						if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {glfwSetWindowShouldClose(window, GL_TRUE);} // We will detect this in our rendering loop
						else if ((key == GLFW_KEY_W || key == GLFW_KEY_UP) && (action == GLFW_RELEASE || action == GLFW_REPEAT)) {CameraManager.translate(0f, TRANSLATION_SPEED);}
						else if ((key == GLFW_KEY_S || key == GLFW_KEY_DOWN) && (action == GLFW_PRESS || action == GLFW_REPEAT)) {CameraManager.translate(0f, -TRANSLATION_SPEED);}
						else if ((key == GLFW_KEY_D || key == GLFW_KEY_RIGHT) && (action == GLFW_PRESS || action == GLFW_REPEAT)) {CameraManager.translate(TRANSLATION_SPEED, 0f);}
						else if ((key == GLFW_KEY_A || key == GLFW_KEY_LEFT) && (action == GLFW_PRESS || action == GLFW_REPEAT)) {CameraManager.translate(-TRANSLATION_SPEED, 0f);}
					}
				});
		
		//Paused Keys
		keyboards.put(State.PAUSED,
				new GLFWKeyCallback() {
					@Override
					public void invoke(long window, int key, int scancode, int action, int mods)
					{
							
					}
				});
		
		//Configuring Keys
		keyboards.put(State.CONFIGURING,
				new GLFWKeyCallback() {
					@Override
					public void invoke(long window, int key, int scancode, int action, int mods)
					{
							
					}
				});
		
		//Reset Keys
		keyboards.put(State.RESET,
				new GLFWKeyCallback() {
					@Override
					public void invoke(long window, int key, int scancode, int action, int mods)
					{
							
					}
				});
		
		//Ended Keys
		keyboards.put(State.ENDED,
				new GLFWKeyCallback() {
					@Override
					public void invoke(long window, int key, int scancode, int action, int mods)
					{
							
					}
				});
	}
}
