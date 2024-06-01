package org.arrowgame.server.utils;

import org.arrowgame.server.ServerApplication;
import org.arrowgame.server.model.UserModel;

import java.util.*;

public class SessionManager {
    private static final Map<String, UserModel> sessions = new HashMap<>();

    public static String createSession(UserModel user) {
        String sessionId = generateSessionId();
        sessions.put(sessionId, user);
        return sessionId;
    }

    @SuppressWarnings(value = "unused")
    public static boolean isValidSession(String sessionId) {
        return sessions.containsKey(sessionId);
    }

    @SuppressWarnings(value = "unused")
    public static UserModel getUsernameForSession(String sessionId) {
        return sessions.get(sessionId);
    }

    private static String generateSessionId() {
        return UUID.randomUUID().toString();
    }

    public static UserModel getUserFromSession() {
        if (!isValidSession(ServerApplication.sessionID)){
            return null;
        }

        return sessions.get(ServerApplication.sessionID);

    }
}
