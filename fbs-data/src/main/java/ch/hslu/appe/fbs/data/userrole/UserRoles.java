package ch.hslu.appe.fbs.data.userrole;

public enum UserRoles {
    SYSADMIN("SysAdmin"),
    DATATYPIST("Datentypist"),
    SALESPERSON("Verkaufspersonal"),
    BRANCHMANAGER("Filialleiter");

    private final String role;

    UserRoles(final String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}
