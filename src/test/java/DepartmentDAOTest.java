import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import dao.DepartmentDAO;
import custom_exception.*;
import model.Department;

class DepartmentDAOTest {
    private DepartmentDAO departmentDAO;

    @BeforeEach
    void init() {
        departmentDAO = new DepartmentDAO();
    }

    @Test
    void testDeleteDepartmentThrows() {
        assertThrows(DepartmentNotFoundException.class, () -> {
            departmentDAO.deleteDepartment(-1);
        });
    }

    @Test
    void testInsertDuplicateDepartmentThrows() throws Exception {
        Department dept = new Department();
        dept.setDeptName("ECE");
        dept.setDeptHead("HOD");
        dept.setOfficeLocation("Main Block");
        departmentDAO.insertDepartment(dept);
        assertThrows(DuplicateEntryException.class, () -> {
            departmentDAO.insertDepartment(dept);
        });
    }
}
