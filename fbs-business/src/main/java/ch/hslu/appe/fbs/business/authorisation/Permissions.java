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

    private List<Permission> permissionsFromJson;

    public Permissions() {
        File file = new File(getClass().getClassLoader().getResource("permissions.json").getFile());
        byte[] json = new byte[0];
        try {
            json = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            LOGGER.error(e);
            permissionsFromJson = Collections.emptyList();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            permissionsFromJson = Arrays.asList(objectMapper.readValue(json, Permission[].class));
        } catch (IOException e) {
            LOGGER.error(e);
            permissionsFromJson = Collections.emptyList();
        }
    }

    public List<Permission> getPermissions() {
        return Collections.unmodifiableList(permissionsFromJson);
    }

    public Optional<Permission> getPermission(UserRoles userRole) {
        return permissionsFromJson.stream().filter(permission ->
                permission.getName().equalsIgnoreCase(userRole.name())).findFirst();
    }
}
