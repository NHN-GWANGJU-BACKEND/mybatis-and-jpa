package com.nhnacademy.method;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionLogin {

    public static String getSessionLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (String) session.getAttribute("login");
    }
}
