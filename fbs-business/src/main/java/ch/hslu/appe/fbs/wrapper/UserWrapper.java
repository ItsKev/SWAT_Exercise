package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.model.db.User;

public final class UserWrapper implements Wrappable<User, UserDTO> {

    private final UserRoleWrapper userRoleWrapper;

    public UserWrapper() {
        this.userRoleWrapper = new UserRoleWrapper();
    }

    @Override
    public UserDTO dtoFromEntity(User user) {
        return new UserDTO(
                user.getId(),
                userRoleWrapper.dtoFromEntity(user.getUserRoleByUserRole()),
                user.getUserName()
        );
    }

    @Override
    public User entityFromDTO(UserDTO userDTO) {
        final User user = new User();
        if (userDTO.getId() != -1) {
            user.setId(userDTO.getId());
        }
        user.setUserName(userDTO.getUserName());
        user.setUserRole(userDTO.getUserRole().getId());
        user.setDeleted((byte) 0);
        return user;
    }
}
