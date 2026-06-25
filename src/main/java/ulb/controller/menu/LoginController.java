package ulb.controller.menu;

import ulb.controller.ApplicationController;
import ulb.model.user.User;
import ulb.view.menu.LoginView;
import ulb.service.login.LoginService;

/**
 * Handles user interactions on the login screen: authentication, registration, and quit.
 */
public final class LoginController implements LoginView.LoginListener {
    private final ApplicationController applicationController;
    private final LoginView view;
    private final LoginService loginService;


    /**
     * @param view                  the login view to bind
     * @param applicationController the root controller for navigation
     * @param loginService          the service handling credential validation and registration
     */
    public LoginController(LoginView view, ApplicationController applicationController, LoginService loginService) {
        this.view = view;
        this.applicationController = applicationController;
        this.loginService = loginService;
        view.setListener(this);
    }

    @Override
    public void onLoginRequested(String username, String password) {
        try {
            User authenticatedUser = loginService.login(username, password);
            applicationController.completeLogin(authenticatedUser);
        } catch (IllegalArgumentException | IllegalStateException e) {
            view.showError(e.getMessage());
        }
    }

    @Override
    public void onRegisterRequested(String username, String password) {
        try {
            User createdUser = loginService.register(username, password);
            view.showRegistrationSuccess(username);
            applicationController.completeLogin(createdUser);
        } catch (IllegalArgumentException | IllegalStateException e) {
            view.showError(e.getMessage());
        }
    }

    @Override
    public void onQuitRequested() {
        applicationController.exitApplication();
    }
}
