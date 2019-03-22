package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.util.Objects;

public final class UserDTO implements Serializable {
    private final int id;
    private final UserRoleDTO userRole;
    private final String userName;

    public UserDTO(int id, UserRoleDTO userRole, String userName) {
        this.id = id;
        this.userRole = userRole;
        this.userName = userName;
    }

    public int getId() {
        return this.id;
    }

    public UserRoleDTO getUserRole() {
        return this.userRole;
    }

    public String getUserName() {
        return this.userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(this.userName, userDTO.userName) &&
                Objects.equals(this.userRole, userDTO.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userName, this.userRole);
    }
}
