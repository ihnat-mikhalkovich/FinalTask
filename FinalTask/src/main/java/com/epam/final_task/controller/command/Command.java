package com.epam.final_task.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Interface for realise pattern Command.
 *
 * @author Ihnat Mikhalkovich
 * @since 1.0
 */
public interface Command {

    /**
     * Signature of the executable method
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException custom exception class for {@code HttpServletRequest} and {@code HttpServletRequest}.
     * @throws IOException execution exclusions
     * @see HttpServletRequest
     * @see HttpServletResponse
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
