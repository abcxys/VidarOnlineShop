package vidar.websystem.controller;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.Pages;
import vidar.websystem.constants.PathConstants;
import vidar.websystem.dto.request.UserRequest;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.service.RegistrationService;
import vidar.websystem.utils.ControllerUtils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping(PathConstants.REGISTRATION)
public class RegistrationController {

    private final RegistrationService registrationService;
    private final ControllerUtils controllerUtils;

    @GetMapping
    public String registration() {
        return Pages.REGISTRATION;
    }

    @PostMapping
    public String registration(@RequestParam("g-recaptcha-response") String captchaResponse,
                               @Valid UserRequest user,
                               BindingResult bindingResult,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        if (controllerUtils.validateInputFields(bindingResult, model, "user", user)) {
            return Pages.REGISTRATION;
        }
        MessageResponse messageResponse = registrationService.registration(captchaResponse, user);
        if (controllerUtils.validateInputField(model, messageResponse, "user", user)) {
            return Pages.REGISTRATION;
        }
        return controllerUtils.setAlertFlashMessage(redirectAttributes, PathConstants.LOGIN, messageResponse);
    }

    @GetMapping("/activate/{code}")
    public String activateEmailCode(@PathVariable String code, Model model) {
        return controllerUtils.setAlertMessage(model, Pages.LOGIN, registrationService.activateEmailCode(code));
    }
}
