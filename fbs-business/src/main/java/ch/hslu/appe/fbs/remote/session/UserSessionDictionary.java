package ch.hslu.appe.fbs.remote.session;

import ch.hslu.appe.fbs.common.dto.UserDTO;

import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class UserSessionDictionary {

    private static final Object LOCK = new Object();

    private static Map<String, UserDTO> sessionMap;

    static {
        sessionMap = new HashMap<>();
    }

    public static void addUserSession(final UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("user object can't be a null reference");
        }
        synchronized (LOCK) {
            final Optional<String> optRemote = getRemoteHost();
            if (optRemote.isPresent()) {
                final String remote = optRemote.get();
                sessionMap.put(remote, userDTO);
            }
        }
    }

    public static void removeUserSession() {
        synchronized (LOCK) {
            final Optional<String> optRemote = getRemoteHost();
            if (optRemote.isPresent()) {
                final String remote = optRemote.get();
                sessionMap.remove(remote);
            }
        }
    }

    public static UserDTO getUserSession() {
        synchronized (LOCK) {
            final Optional<String> optRemote = getRemoteHost();
            if (optRemote.isPresent()) {
                final String remote = optRemote.get();
                return sessionMap.get(remote);
            }
            throw new IllegalArgumentException("No user session mapping available");
        }
    }

    private static Optional<String> getRemoteHost() {
        try {
            final String remote = RemoteServer.getClientHost();
            return Optional.of(remote);
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
