package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.data.userrole.UserRoles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Permissions {

    private static final Logger LOGGER = LogManager.getLogger(Permissions.class);

    private List<Permission> permissions;

    public Permissions() {
        File file = new File(getClass().getClassLoader().getResource("permissions.json").getFile());
        byte[] json = new byte[0];
        try {
            json = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
            permissions = Collections.emptyList();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            permissions = Arrays.asList(objectMapper.readValue(json, Permission[].class));
        } catch (IOException e) {
            e.printStackTrace();
            permissions = Collections.emptyList();
        }
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public Optional<Permission> getPermission(UserRoles userRole) {
        return permissions.stream().filter(permission ->
                permission.getName().toLowerCase().equals(userRole.name().toLowerCase())).findFirst();
    }
}
