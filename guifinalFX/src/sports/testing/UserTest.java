package sports.testing;

import java.util.ArrayList;
import junit.framework.TestCase;
import org.junit.Test;

import sports.model.Exercise;
import sports.model.Food;
import sports.model.User;
import sports.model.Weight;
/**
 * Test class to check methods in User Weight Food and Exercise
 * 
 * @author hxm02u
 * @version 1.0.0
 */
public class UserTest extends TestCase {
	
	@Test
	public void testCalculateBMI() {
		User u = new User("harry", 7, 170.0);
		Weight w = new Weight(69.0, "2013-11-28");
		u.addNewWeight(w);
		assertEquals(23.9, u.calculateBMI());
	}
	
	@Test
	public void testSetListofExercise() {
		User u = new User();
		Exercise ex1 = new Exercise("Running", 5.0, "2013-11-28");
		u.setListofExercise(ex1);
		ArrayList<Exercise> a = new ArrayList<Exercise>();
		a.add(ex1);
		assertEquals(u.getListofExercise(), a);
	}
	
	@Test
	public void testSetListofFood() {
		User u = new User();
		Food f = new Food("ban", 1, 1, 1, 0, "2013-11-10");
		u.setListofFood(f);
		ArrayList<Food> a = new ArrayList<Food>();
		a.add(f);
		assertEquals(u.getListofFood(), a);
	}

}
