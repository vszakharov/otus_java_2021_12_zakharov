package ru.otus.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class AuthenticatedRequest extends HttpServletRequestWrapper {

    private final String userId;

    public AuthenticatedRequest(HttpServletRequest req, String userId) {
        super(req);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
