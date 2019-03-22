package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.model.db.UserRole;

public final class UserRoleWrapper implements Wrappable<UserRole, UserRoleDTO> {
    @Override
    public UserRoleDTO dtoFromEntity(UserRole userRole) {
        return new UserRoleDTO(
                userRole.getId(),
                userRole.getRoleName()
        );
    }

    @Override
    public UserRole entityFromDTO(UserRoleDTO userRoleDTO) {
        final UserRole userRole = new UserRole();
        if (userRoleDTO.getId() != -1) {
            userRole.setId(userRoleDTO.getId());
        }
        userRole.setRoleName(userRoleDTO.getUserRole());
        return userRole;
    }
}
