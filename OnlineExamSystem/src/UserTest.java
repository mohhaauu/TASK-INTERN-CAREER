import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserTest
{

    @org.junit.Test
    public void testGettersAndSetters()
    {
        User user = new User(1, "John", "Doe", "john.doe@example.com",
                "password", "student");

        assertEquals(1, user.getId());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals("student", user.getRole());

        user.setId(2);
        user.setFirstName("Jane");
        user.setLastName("Smith");
        user.setEmail("jane.smith@example.com");
        user.setPassword("newpassword");
        user.setRole("teacher");

        assertEquals(2, user.getId());
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane.smith@example.com", user.getEmail());
        assertEquals("newpassword", user.getPassword());
        assertEquals("teacher", user.getRole());
    }
}
