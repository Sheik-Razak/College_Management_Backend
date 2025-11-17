import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import dao.UserDAO;
import custom_exception.*;

class UserDAOTest {
    private UserDAO userDAO;

    @BeforeEach
    void init() {
        userDAO = new UserDAO();
    }

    @Test
    void testSignupSuccess() throws Exception {
        boolean signupSuccess = userDAO.signup("Student", "userjunittest", "mailjunittest@example.com", "pass", "FullName", "Male", "2000-01-01", "1234567890", "Address", "BE", 1);
        assertTrue(signupSuccess);
    }

    @Test
    void testDuplicateSignupThrows() throws Exception {
        userDAO.signup("Student", "dupjunittest", "dupjunittest@example.com", "pass", "FullName", "Male", "2000-01-01", "1234567890", "Address", "BE", 1);
        assertThrows(DuplicateEntryException.class, () -> {
            userDAO.signup("Student", "dupjunittest", "dupjunittest@example.com", "pass", "FullName", "Male", "2000-01-01", "1234567890", "Address", "BE", 1);
        });
    }

    @Test
    void testGetUserByUsernameThrows() {
        assertThrows(UserNotFoundException.class, () -> {
            userDAO.getUserByUsername("idontexistjunittest");
        });
    }
}
