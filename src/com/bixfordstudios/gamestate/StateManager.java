package com.bixfordstudios.gamestate;

import java.util.EnumSet;
import java.util.HashMap;

import com.bixfordstudios.input.InputManager;

/**
 * State manager classed that is only accessed statically and regulates state changes.
 * @author DarkEther
 *
 */
public class StateManager
{
	
	// Initial starting state
	private static State currentState;
	
	/**
	 * Regulates the state changes. <p>
	 * HashMap that should contain all possible states as the key and the corresponding value(s) as the states that can be access from the given state.
	 */
	private static HashMap<State, EnumSet<State>> stateMap = new HashMap<State, EnumSet<State>>();
	static {
		//Explanation: If in the RUNNING state then you can only access the PAUSED and ENDED states as legal state changes 
		stateMap.put(State.LOADING, 
				EnumSet.of(State.MAINSCREEN));
		
		stateMap.put(State.MAINSCREEN, 
				EnumSet.of(State.PAUSED));
		
		stateMap.put(State.RUNNING, 
				EnumSet.of(State.PAUSED, State.ENDED));
		
		stateMap.put(State.PAUSED, 
				EnumSet.of(State.RUNNING, State.RESET, State.CONFIGURING));
		
		stateMap.put(State.ENDED,
				EnumSet.of(State.RESET));
		
		stateMap.put(State.CONFIGURING, 
				EnumSet.of(State.PAUSED));
		
		stateMap.put(State.RESET, 
				EnumSet.of(State.PAUSED, State.RESET));
	}
	
	/**
	 * Sets the first state to load into (In the case it would change from compile time).<p>
	 * Dependent: None
	 */
	public static void init()
	{
		currentState = State.LOADING;
	}
	
	/**
	 * Does nothing.
	 */
	public static void exit() {}
	
	/**
	 * Returns the current state
	 * @return The current state
	 */
	public static State getCurrent()
	{
		return currentState;
	}
	
	/**
	 * Changes the current state to the desiredState. If the state change is illegal it will throw a {@link IllegalArgumentException}
	 * @param desiredState State to change to
	 */
	public static void changeState(State desiredState)
	{
		if(isLegal(desiredState))
		{
			updateState(desiredState);
		}
		else
		{
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * Checks {@link stateMap} to see if the desiredState is a legal state change given {@link currentState}
	 * @param desiredState State to check
	 * @return True, state change is legal; False, state change is illegal
	 */
	private static boolean isLegal(State desiredState)
	{
		return stateMap.get(currentState).contains(desiredState);
	}
	
	/**
	 * Changes the current state and notifies listening managers.
	 * <ul>Managers Listening:
	 * <li>InputManager</li></ul>
	 * @param state
	 */
	private static void updateState(State state)
	{
		//Update State
		currentState = state;
		
		//Notify Any Necessary Managers
		InputManager.updateKeyCallback();
	}
}
