import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Unit test class for the User class.
 */
public class UserTest
{

    /**
     * Test method to verify the getters and setters of the User class.
     */
    @org.junit.Test
    public void testGettersAndSetters()
    {
        // Create a User object with initial values
        User user = new User(1, "John", "Doe", "john.doe@gmail.com",
                "password", "student");

        // Verify initial values using getters
        assertEquals(1, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@gmail.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("student", user.getRole());

        // Update values using setters
        user.setId(2);
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("jane.smith@example.com");
        user.setPassword("newpassword");
        user.setRole("teacher");

        // Verify updated values using getters
        assertEquals(2, user.getId());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane.smith@example.com", user.getEmail());
        assertEquals("newpassword", user.getPassword());
        assertEquals("teacher", user.getRole());
    }
}
