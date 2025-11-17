package model;

public class Department {
    private int deptId;
    private String deptName;
    private String deptHead;
    private String officeLocation;

    public Department() {}

    public Department(int deptId, String deptName, String deptHead, String officeLocation) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.deptHead = deptHead;
        this.officeLocation = officeLocation;
    }
    public Department(int deptId) {
        this.deptId = deptId;
    }

    public int getDeptId() { return deptId; }
    public void setDeptId(int deptId) { this.deptId = deptId; }

    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    public String getDeptHead() { return deptHead; }
    public void setDeptHead(String deptHead) { this.deptHead = deptHead; }

    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String officeLocation) { this.officeLocation = officeLocation; }

    @Override
    public String toString() {
        return "Department [deptId=" + deptId +
                ", deptName=" + deptName +
                ", deptHead=" + deptHead +
                ", officeLocation=" + officeLocation + "]";
    }
}
