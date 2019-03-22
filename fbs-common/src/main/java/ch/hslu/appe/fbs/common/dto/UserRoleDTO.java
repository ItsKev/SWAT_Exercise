package ch.hslu.appe.fbs.common.dto;

import java.io.Serializable;
import java.util.Objects;

public final class UserRoleDTO implements Serializable {
    private final int id;
    private final String userRole;

    public UserRoleDTO(int id, String userRole) {
        this.id = id;
        this.userRole = userRole;
    }

    public int getId() {
        return this.id;
    }

    public String getUserRole() {
        return this.userRole;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoleDTO)) return false;
        UserRoleDTO userRoleDTO = (UserRoleDTO) o;
        return Objects.equals(this.userRole, userRoleDTO.userRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.userRole);
    }
}
