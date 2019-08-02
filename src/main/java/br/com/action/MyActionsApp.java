package br.com.action;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ResourceBundle;

/**
 * * Implementa todos os manipuladores de intenção para esta ação.
 */
public class MyActionsApp extends DialogflowApp {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @ForIntent("Default Welcome Intent")
    public ActionResponse welcome(ActionRequest request) {
        LOGGER.info("Welcome intent start.");
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        ResourceBundle rb = ResourceBundle.getBundle("resources");
        User user = request.getUser();

        if (user != null && user.getLastSeen() != null) {
            responseBuilder.add(rb.getString("welcome_back"));
        } else {
            responseBuilder.add(rb.getString("welcome"));
        }

        LOGGER.info("Welcome intent end.");

        return responseBuilder.build();
    }

    @ForIntent("bye")
    public ActionResponse bye(ActionRequest request) {
        LOGGER.info("Bye intent start.");
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        ResourceBundle rb = ResourceBundle.getBundle("resources");

        responseBuilder.add(rb.getString("bye")).endConversation();
        LOGGER.info("Bye intent end.");

        return responseBuilder.build();
    }
}
