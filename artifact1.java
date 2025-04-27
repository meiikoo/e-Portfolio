package edu.pitt.cs;

import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CoffeeMakerQuestUnitTest {

	static final String newline = System.lineSeparator(); // Platform independent newline ("\n" or "\r\n")

	CoffeeMakerQuest cmq;
	Player player;
	ArrayList<Room> rooms;

	@Before
	public void setup() {
		// 1. Create a Player with no items (no coffee, no cream, no sugar) and assign to player.
		// Make sure you use the Player.createInstance method to create the player, or GradeScope autograder will not function properly.
		// TODO: Fill in
		player = Player.createInstance(InstanceType.MOCK);
		Mockito.when(player.hasItem(Item.COFFEE)).thenReturn(false);
		Mockito.when(player.hasItem(Item.CREAM)).thenReturn(false);
		Mockito.when(player.hasItem(Item.SUGAR)).thenReturn(false);

		// 2. Create 6 rooms exactly as specified in rooms.config and add to rooms list.
		// You are expected to hard-code the room configurations. The test cases in this
		// class depend on those hard-coded values, so it would be pointless to allow
		// them to be changed.
		// Make sure you use the Room.createInstance method to create the rooms, or GradeScope autograder will not function properly.
		// TODO: Fill in
		rooms = new ArrayList<>();

		rooms.add(Room.createInstance(InstanceType.MOCK, "Quaint sofa", "Small", Item.CREAM, "Magenta",  null));
		rooms.add(Room.createInstance(InstanceType.MOCK, "Sad record player", "Funny", Item.NONE, "Beige", "Massive"));
		rooms.add(Room.createInstance(InstanceType.MOCK, "Tight pizza", "Refinanced", Item.COFFEE, "Dead", "Smart"));
		rooms.add(Room.createInstance(InstanceType.MOCK, "Flat energy drink", "Dumb", Item.NONE, "Vivacious", "Slim"));
		rooms.add(Room.createInstance(InstanceType.MOCK, "Beautiful bag of money", "Bloodthirsty", Item.NONE, "Purple", "Sandy"));
		rooms.add(Room.createInstance(InstanceType.MOCK, "Perfect air hockey table", "Rough", Item.SUGAR, null, "Minimalist"));

		// 3. Create a CoffeeMakerQuest object using player and rooms and assign to cmq.
		// Make sure you use the CoffeeMakerQuest.createInstance method to create cmq, or GradeScope autograder will not function properly.
		// TODO: Fill in
		cmq = CoffeeMakerQuest.createInstance(InstanceType.IMPL, player, rooms);
	}

	@After
	public void tearDown() {
	}

	/**
	 * Test case for String getHelpString().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.getHelpString().
	 * Postconditions: Return value is:
	 *                 "N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline +
	 *                 "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline.
	 * </pre>
	 */
	@Test
	public void testGetHelpString() {
		try {
			Class<?> c = cmq.getClass();
			Method getHelpStringMethod = c.getDeclaredMethod("getHelpString");
			getHelpStringMethod.setAccessible(true);
			String result = (String) getHelpStringMethod.invoke(getHelpStringMethod);

			assertEquals("N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline
			+ "I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline
			, result);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test case for Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.getCurrentRoom().
	 * Postconditions: Return value is rooms.get(0).
	 * </pre>
	 */
	@Test
	public void testGetCurrentRoom() {
		Room currentRoom = cmq.getCurrentRoom();
		assertEquals(rooms.get(0), currentRoom);
	}

	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.setCurrentRoom(rooms.get(2)).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(rooms.get(2)) is true. 
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(2).
	 * </pre>
	 */
	@Test
	public void testSetCurrentRoom() {
		boolean setCurrentRoomResult = cmq.setCurrentRoom(rooms.get(2));
		assertTrue(setCurrentRoomResult);
		Room currentRoom = cmq.getCurrentRoom();
		assertEquals(rooms.get(2), currentRoom);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check succeeds.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is true.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectly() {
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertTrue(result);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(3) is modified so that it has no south door.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyMissingSouthDoor() {
		rooms.set(3, Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, "", null));
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areRoomsUnique() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(2) is modified so that its adjective is modified to "Small".
	 * Execution steps: Call cmq.areRoomsUnique().
	 * Postconditions: Return value of cmq.areRoomsUnique() is false.
	 * </pre>
	 */
	@Test
	public void testAreRoomsUniqueDuplicateAdjective() {
		rooms.set(2, Room.createInstance(InstanceType.MOCK, "Tight pizza", "Small", Item.COFFEE, "Dead", "Smart"));
		boolean result = cmq.areRoomsUnique();
		assertFalse(result);
	}

	/**
	 * Test case for String processCommand("I").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("I").
	 * Postconditions: Return value is: "YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline.
	 * </pre>
	 */
	@Test
	public void testProcessCommandI() {
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline);
		String result = cmq.processCommand("I");
		assertEquals("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + "YOU HAVE NO SUGAR!" + newline, result);
	}

	/**
	 * Test case for String processCommand("l").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("l").
	 * Postconditions: Return value is: "There might be something here..." + newline + "You found some creamy cream!" + newline.
	 *                 Cream is added to player.
	 * </pre>
	 */
	@Test
	public void testProcessCommandLCream() {
		String result = cmq.processCommand("l");
		assertEquals("There might be something here..." + newline + "You found some creamy cream!" + newline, result);
		Mockito.verify(player, Mockito.times(1)).addItem(Item.CREAM);
	}

	/**
	 * Test case for String processCommand("n").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                cmq.setCurrentRoom(rooms.get(3)) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(4).
	 * </pre>
	 */
	@Test
	public void testProcessCommandN() {
		cmq.setCurrentRoom(rooms.get(3));
		String processCommandResult = cmq.processCommand("n");
		Room getCurrentRoomResult = cmq.getCurrentRoom();

		assertEquals("", processCommandResult);
		assertEquals(rooms.get(4), getCurrentRoomResult);
	}

	/**
	 * Test case for String processCommand("s").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("s") is: "A door in that direction does not exist." + newline.
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(0).
	 * </pre>
	 */
	@Test
	public void testProcessCommandS() {
		String processCommandResult = cmq.processCommand("s");
		Room getCurrentRoomResult = cmq.getCurrentRoom();

		assertEquals("A door in that direction does not exist." + newline, processCommandResult);
		assertEquals(rooms.get(0), getCurrentRoomResult);
	}

	/**
	 * Test case for String processCommand("D").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is: "YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + 
	 *                    "YOU HAVE NO SUGAR!" + newline + newline + "You drink thin air and can only dream of coffee. You cannot study." + newline + "You lose!" + newline.
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDLose() {
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + 
	                    "YOU HAVE NO SUGAR!" + newline);
		Mockito.when(player.hasItem(Item.COFFEE)).thenReturn(false);
		Mockito.when(player.hasItem(Item.CREAM)).thenReturn(false);
		Mockito.when(player.hasItem(Item.SUGAR)).thenReturn(false);

		String processCommandResult = cmq.processCommand("D");
		boolean gameOverResult = cmq.isGameOver();

		assertEquals("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + 
		"YOU HAVE NO SUGAR!" + newline + newline + "You drink thin air and can only dream of coffee. You cannot study." + newline + 
		"You lose!" + newline, processCommandResult);
		assertTrue(gameOverResult);
	}

	/**
	 * Test case for String processCommand("D").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                Player has all 3 items (coffee, cream, sugar).
	 * Execution steps: Call cmq.processCommand("D").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("D") is: "You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + 
	 *                    "You have some tasty sugar." + newline + newline + "You drink the beverage and are ready to study!" + newline + "You win!" + newline.
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDWin() {
		Mockito.when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + 
		"You have some tasty sugar." + newline);
		Mockito.when(player.hasItem(Item.COFFEE)).thenReturn(true);
		Mockito.when(player.hasItem(Item.CREAM)).thenReturn(true);
		Mockito.when(player.hasItem(Item.SUGAR)).thenReturn(true);

		String processCommandResult = cmq.processCommand("D");
		boolean gameOverResult = cmq.isGameOver();

		assertEquals("You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + 
		"You have some tasty sugar." + newline + newline + "You drink the beverage and are ready to study!" + newline + 
		"You win!" + newline, processCommandResult);
		assertTrue(gameOverResult);
	}



	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms is modified so that it has only one room with a south door only.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyOneRoomSouthDoor() {
		rooms.clear();
		rooms.add(Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, null, "another door"));
		
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms is modified so that it has only one room with a north door only.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyOneRoomNorthDoor() {
		rooms.clear();
		rooms.add(Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, "", null));
		
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms is modified so that it has only one room with a north and a south door.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyOneRoomBothDoor() {
		rooms.clear();
		rooms.add(Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, "", ""));
		
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms is modified so that it has only one room with no doors.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is true.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyOneRoomNoDoor() {
		rooms.clear();
		rooms.add(Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, null, null));
		
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertTrue(result);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(3) is modified so that it has no north door.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyMissingNorthDoor() {

		rooms.set(3, Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, null, ""));
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(0) is modified so that it has no north door.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlySouthernmostRoomMissingNorthDoor() {

		rooms.set(0, Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, null, null));
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(0) is modified so that it has a north door and a south door.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlySouthernmostRoomHasSouthDoor() {

		rooms.set(0, Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, "", ""));
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(rooms.size() - 1) is modified so that it has a north door.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyNorthernmostRoomHasNorthDoor() {
		rooms.set(rooms.size() - 1, Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, "", ""));
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areDoorsPlacedCorrectly() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(rooms.size() - 1) is modified so that it has no south door.
	 * Execution steps: Call cmq.areDoorsPlacedCorrectly().
	 * Postconditions: Return value of cmq.areDoorsPlacedCorrectly() is false.
	 * </pre>
	 */
	@Test
	public void testAreDoorsPlacedCorrectlyNorthernmostRoomMissingSouthDoor() {
		rooms.set(rooms.size() - 1, Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, null, null));
		boolean result = cmq.areDoorsPlacedCorrectly();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areRoomsUnique() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(2) is modified so that its furnishing is modified to "Quaint sofa".
	 * Execution steps: Call cmq.areRoomsUnique().
	 * Postconditions: Return value of cmq.areRoomsUnique() is false.
	 * </pre>
	 */
	@Test
	public void testAreRoomsUniqueDuplicateFurnishing() {
		rooms.set(2, Room.createInstance(InstanceType.MOCK, "Quaint sofa", "Refinanced", Item.COFFEE, "Dead", "Smart"));
		boolean result = cmq.areRoomsUnique();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areRoomsUnique() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                rooms.get(2) is modified so that its furnishing is modified to "Quaint sofa" and its adjective is modified to "Small".
	 * Execution steps: Call cmq.areRoomsUnique().
	 * Postconditions: Return value of cmq.areRoomsUnique() is false.
	 * </pre>
	 */
	@Test
	public void testAreRoomsUniqueDuplicateFurnishingAndAdjective() {
		rooms.set(2, Room.createInstance(InstanceType.MOCK, "Quaint sofa", "Small", Item.COFFEE, "Dead", "Smart"));
		boolean result = cmq.areRoomsUnique();
		assertFalse(result);
	}

	/**
	 * Test case for boolean areRoomsUnique() when check fails.
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.areRoomsUnique().
	 * Postconditions: Return value of cmq.areRoomsUnique() is true.
	 * </pre>
	 */
	@Test
	public void testAreRoomsUniqueNoDuplicate() {
		boolean result = cmq.areRoomsUnique();
		assertTrue(result);
	}

	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.setCurrentRoom(rooms.get(2)).
	 *                  Call cmq.getCurrentRoom().
	 * 					Call cmq.setCurrentRoom(rooms.get(0)).
	 * 					Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(rooms.get(2)) is true. 
	 *                 Return value of the first cmq.getCurrentRoom() is rooms.get(2).
	 * 				   Return value of cmq.setCurrentRoom(rooms.get(0)) is true. 
	 * 				   Return value of the second cmq.getCurrentRoom() is rooms.get(0).
	 * 
	 * </pre>
	 */
	@Test
	public void testSetCurrentRoom0() {
		boolean setCurrentRoomResult = cmq.setCurrentRoom(rooms.get(2));
		assertTrue(setCurrentRoomResult);
		Room currentRoom = cmq.getCurrentRoom();
		assertEquals(rooms.get(2), currentRoom);

		boolean setCurrentRoomResult2 = cmq.setCurrentRoom(rooms.get(0));
		assertTrue(setCurrentRoomResult2);
		Room currentRoom2 = cmq.getCurrentRoom();
		assertEquals(rooms.get(0), currentRoom2);
	}

	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.setCurrentRoom(rooms.get(1)).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(rooms.get(1)) is true. 
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(1).
	 * </pre>
	 */
	@Test
	public void testSetCurrentRoom1() {
		boolean setCurrentRoomResult = cmq.setCurrentRoom(rooms.get(1));
		assertTrue(setCurrentRoomResult);
		Room currentRoom = cmq.getCurrentRoom();
		assertEquals(rooms.get(1), currentRoom);
	}

	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.setCurrentRoom(rooms.get(3)).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(rooms.get(3)) is true. 
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(3).
	 * </pre>
	 */
	@Test
	public void testSetCurrentRoom3() {
		boolean setCurrentRoomResult = cmq.setCurrentRoom(rooms.get(3));
		assertTrue(setCurrentRoomResult);
		Room currentRoom = cmq.getCurrentRoom();
		assertEquals(rooms.get(3), currentRoom);
	}

	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.setCurrentRoom(rooms.get(4)).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(rooms.get(4)) is true. 
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(4).
	 * </pre>
	 */
	@Test
	public void testSetCurrentRoom4() {
		boolean setCurrentRoomResult = cmq.setCurrentRoom(rooms.get(4));
		assertTrue(setCurrentRoomResult);
		Room currentRoom = cmq.getCurrentRoom();
		assertEquals(rooms.get(4), currentRoom);
	}

	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.setCurrentRoom(rooms.get(5)).
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.setCurrentRoom(rooms.get(5)) is true. 
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(5).
	 * </pre>
	 */
	@Test
	public void testSetCurrentRoom5() {
		boolean setCurrentRoomResult = cmq.setCurrentRoom(rooms.get(5));
		assertTrue(setCurrentRoomResult);
		Room currentRoom = cmq.getCurrentRoom();
		assertEquals(rooms.get(5), currentRoom);
	}

	/**
	 * Test case for void setCurrentRoom(Room room) and Room getCurrentRoom().
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.setCurrentRoom(Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, "", "")).
	 * Postconditions: Return value of cmq.setCurrentRoom(Room.createInstance(InstanceType.MOCK, "", "", Item.NONE, "", "")) is false. 
	 * </pre>
	 */
	@Test
	public void testSetCurrentRoomNotFound() {
		boolean setCurrentRoomResult = cmq.setCurrentRoom(Room.createInstance(InstanceType.MOCK
		, "", "", Item.NONE, "", ""));
		assertFalse(setCurrentRoomResult);
	}

	/**
	 * Test case for String processCommand("l").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("n").
	 * 					Call cmq.processCommand("l").
	 * Postconditions: Return value is: "You don't see anything out of the ordinary." + newline.
	 *                 Cream is not added to player.
	 * 				   Coffee is not added to player.
	 * 				   Sugar is not added to player.
	 * </pre>
	 */
	@Test
	public void testProcessCommandLNothing() {
		String ignored = cmq.processCommand("n");
		String result = cmq.processCommand("l");
		assertEquals("You don't see anything out of the ordinary." + newline, result);
		Mockito.verify(player, Mockito.never()).addItem(Item.CREAM);
		Mockito.verify(player, Mockito.never()).addItem(Item.COFFEE);
		Mockito.verify(player, Mockito.never()).addItem(Item.SUGAR);
	}

	/**
	 * Test case for String processCommand("h").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                cmq.setCurrentRoom(rooms.get(3)) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "".
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(4).
	 * </pre>
	 */
	@Test
	public void testProcessCommandH() {
		String processCommandResult = cmq.processCommand("h");

		assertEquals("N - Go north" + newline + "S - Go south" + newline + "L - Look and collect any items in the room" + newline +
    			"I - Show inventory of items collected" + newline + "D - Drink coffee made from items in inventory" + newline,
				processCommandResult);
	}

	/**
	 * Test case for String processCommand("s").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("n").
	 * 					Call cmq.getCurrentRoom().
	 * 					Call cmq.processCommand("s").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is: "" .
	 * 				   Return value of the first cmq.getCurrentRoom() is rooms.get(1).
	 * 				   Return value of cmq.processCommand("s") is: "".
	 *                 Return value of the second cmq.getCurrentRoom() is rooms.get(0).
	 * </pre>
	 */
	@Test
	public void testProcessCommandSSuccess() {
		String processCommandResultn = cmq.processCommand("n");
		Room getCurrentRoomResultn = cmq.getCurrentRoom();

		assertEquals("", processCommandResultn);
		assertEquals(rooms.get(1), getCurrentRoomResultn);

		String processCommandResult = cmq.processCommand("s");
		Room getCurrentRoomResult = cmq.getCurrentRoom();

		assertEquals("", processCommandResult);
		assertEquals(rooms.get(0), getCurrentRoomResult);
	}

	/**
	 * Test case for String processCommand("n").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 *                cmq.setCurrentRoom(rooms.get(rooms.size() - 1)) has been called.
	 * Execution steps: Call cmq.processCommand("n").
	 *                  Call cmq.getCurrentRoom().
	 * Postconditions: Return value of cmq.processCommand("n") is "A door in that direction does not exist." + newline.
	 *                 Return value of cmq.getCurrentRoom() is rooms.get(rooms.size() - 1).
	 * </pre>
	 */
	@Test
	public void testProcessCommandNNorthernmost() {
		cmq.setCurrentRoom(rooms.get(rooms.size() - 1));
		String processCommandResult = cmq.processCommand("n");
		Room getCurrentRoomResult = cmq.getCurrentRoom();

		assertEquals("A door in that direction does not exist." + newline, processCommandResult);
		assertEquals(rooms.get(rooms.size() - 1), getCurrentRoomResult);
	}

	/**
	 * Test case for String processCommand("d").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * 				  Player only has Coffee but not Sugar or Cream
	 * Execution steps: Call cmq.processCommand("d").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("d") is: "You have a cup of delicious coffee." + newline + "YOU HAVE NO CREAM!" + newline + 
	 *                    "YOU HAVE NO SUGAR!" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + "You lose!" + newline.
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDLoseOnlyCoffee() {
		Mockito.when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee." + newline + "YOU HAVE NO CREAM!" + newline + 
	                    "YOU HAVE NO SUGAR!" + newline);
		Mockito.when(player.hasItem(Item.COFFEE)).thenReturn(true);
		Mockito.when(player.hasItem(Item.CREAM)).thenReturn(false);
		Mockito.when(player.hasItem(Item.SUGAR)).thenReturn(false);

		String processCommandResult = cmq.processCommand("d");
		boolean gameOverResult = cmq.isGameOver();

		assertEquals("You have a cup of delicious coffee." + newline + "YOU HAVE NO CREAM!" + newline + 
		"YOU HAVE NO SUGAR!" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline, processCommandResult);
		assertTrue(gameOverResult);
	}

	/**
	 * Test case for String processCommand("d").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * 				  Player only has Cream but not Sugar or Coffee
	 * Execution steps: Call cmq.processCommand("d").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("d") is: "YOU HAVE NO COFFEE!" + newline + "You have some fresh cream." + newline + 
	 *                    "YOU HAVE NO SUGAR!" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + "You lose!" + newline.
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDLoseOnlyCream() {
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!" + newline + "You have some fresh cream." + newline + 
	                    "YOU HAVE NO SUGAR!" + newline);
		Mockito.when(player.hasItem(Item.COFFEE)).thenReturn(false);
		Mockito.when(player.hasItem(Item.CREAM)).thenReturn(true);
		Mockito.when(player.hasItem(Item.SUGAR)).thenReturn(false);

		String processCommandResult = cmq.processCommand("d");
		boolean gameOverResult = cmq.isGameOver();

		assertEquals("YOU HAVE NO COFFEE!" + newline + "You have some fresh cream." + newline + 
		"YOU HAVE NO SUGAR!" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline, processCommandResult);
		assertTrue(gameOverResult);
	}

	/**
	 * Test case for String processCommand("d").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * 				  Player only has Sugar but not Coffee or Cream
	 * Execution steps: Call cmq.processCommand("d").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("d") is: "YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + 
	    "You have some tasty sugar." + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDLoseOnlySugar() {
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + 
	                    "You have some tasty sugar." + newline);
		Mockito.when(player.hasItem(Item.COFFEE)).thenReturn(false);
		Mockito.when(player.hasItem(Item.CREAM)).thenReturn(false);
		Mockito.when(player.hasItem(Item.SUGAR)).thenReturn(true);

		String processCommandResult = cmq.processCommand("d");
		boolean gameOverResult = cmq.isGameOver();

		assertEquals("YOU HAVE NO COFFEE!" + newline + "YOU HAVE NO CREAM!" + newline + 
	    "You have some tasty sugar." + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline, processCommandResult);
		assertTrue(gameOverResult);
	}

	/**
	 * Test case for String processCommand("d").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * 				  Player only has Coffee and Cream but no Sugar.
	 * Execution steps: Call cmq.processCommand("d").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("d") is: "You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + 
	    "YOU HAVE NO SUGAR!" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDLoseCoffeeCream() {
		Mockito.when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + 
	    "YOU HAVE NO SUGAR!" + newline);
		Mockito.when(player.hasItem(Item.COFFEE)).thenReturn(true);
		Mockito.when(player.hasItem(Item.CREAM)).thenReturn(true);
		Mockito.when(player.hasItem(Item.SUGAR)).thenReturn(false);

		String processCommandResult = cmq.processCommand("d");
		boolean gameOverResult = cmq.isGameOver();

		assertEquals("You have a cup of delicious coffee." + newline + "You have some fresh cream." + newline + 
	    "YOU HAVE NO SUGAR!" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline, processCommandResult);
		assertTrue(gameOverResult);
	}

	/**
	 * Test case for String processCommand("d").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * 				  Player only has Coffee and Sugar but no Cream.
	 * Execution steps: Call cmq.processCommand("d").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("d") is: "You have a cup of delicious coffee." + newline + "YOU HAVE NO CREAM!" + newline + 
	    "You have some tasty sugar" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDLoseCoffeeSugar() {
		Mockito.when(player.getInventoryString()).thenReturn("You have a cup of delicious coffee." + newline + "YOU HAVE NO CREAM!" + newline + 
	    "You have some tasty sugar" + newline);
		Mockito.when(player.hasItem(Item.COFFEE)).thenReturn(true);
		Mockito.when(player.hasItem(Item.CREAM)).thenReturn(false);
		Mockito.when(player.hasItem(Item.SUGAR)).thenReturn(true);

		String processCommandResult = cmq.processCommand("d");
		boolean gameOverResult = cmq.isGameOver();

		assertEquals("You have a cup of delicious coffee." + newline + "YOU HAVE NO CREAM!" + newline + 
	    "You have some tasty sugar" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline, processCommandResult);
		assertTrue(gameOverResult);
	}

		/**
	 * Test case for String processCommand("d").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * 				  Player only has Cream and Sugar but no Coffee.
	 * Execution steps: Call cmq.processCommand("d").
	 *                  Call cmq.isGameOver().
	 * Postconditions: Return value of cmq.processCommand("d") is: "YOU HAVE NO COFFEE!" + newline + "You have some fresh cream" + newline + 
	    "You have some tasty sugar" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline
	 *                 Return value of cmq.isGameOver() is true.
	 * </pre>
	 */
	@Test
	public void testProcessCommandDLoseCreamSugar() {
		Mockito.when(player.getInventoryString()).thenReturn("YOU HAVE NO COFFEE!" + newline + "You have some fresh cream" + newline + 
	    "You have some tasty sugar" + newline);
		Mockito.when(player.hasItem(Item.COFFEE)).thenReturn(false);
		Mockito.when(player.hasItem(Item.CREAM)).thenReturn(true);
		Mockito.when(player.hasItem(Item.SUGAR)).thenReturn(true);

		String processCommandResult = cmq.processCommand("d");
		boolean gameOverResult = cmq.isGameOver();

		assertEquals("YOU HAVE NO COFFEE!" + newline + "You have some fresh cream" + newline + 
	    "You have some tasty sugar" + newline + newline + "You refuse to drink this half-made sludge. You cannot study." + newline + 
		"You lose!" + newline, processCommandResult);
		assertTrue(gameOverResult);
	}

	/**
	 * Test case for String processCommand("Q").
	 * 
	 * <pre>
	 * Preconditions: Player, rooms, and cmq test fixture has been created.
	 * Execution steps: Call cmq.processCommand("Q").
	 * Postconditions: Return value of cmq.processCommand("Q") is "What?" + newline.
	 * </pre>
	 */
	@Test
	public void testProcessCommandInvalid() {
		String processCommandResult = cmq.processCommand("Q");

		assertEquals("What?" + newline,
				processCommandResult);
	}
}