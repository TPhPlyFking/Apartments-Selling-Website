package com.apartment.website.common.util;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class View {
    private View() {
    }

    public static void forward(HttpServletRequest request, HttpServletResponse response, String view)
            throws ServletException, IOException {
        String jspPath = "/WEB-INF/views/" + view + ".jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPath);
        dispatcher.forward(request, response);
    }
}
