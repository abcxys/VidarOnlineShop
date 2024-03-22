package vidar.websystem.service;

import vidar.websystem.dto.request.UserRequest;
import vidar.websystem.dto.response.MessageResponse;

public interface RegistrationService {

    MessageResponse registration(String captchaResponse, UserRequest user);

    MessageResponse activateEmailCode(String code);
}
