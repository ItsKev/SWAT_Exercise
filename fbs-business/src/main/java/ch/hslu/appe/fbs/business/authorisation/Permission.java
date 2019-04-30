package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.common.permission.UserPermissions;
import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Permission {

    private String name;
    private List<String> permissions;
    private List<UserPermissions> userPermissions;

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("permissions")
    private List<String> getPermissions() {
        return permissions;
    }

    public List<UserPermissions> getUserPermissions() {
        if (userPermissions == null) {
            userPermissions = new ArrayList<>();
            permissions.forEach(permission -> userPermissions.add(UserPermissions.valueOf(permission.toUpperCase())));
        }
        return Collections.unmodifiableList(userPermissions);
    }

}
