package vidar.websystem.service;

import vidar.websystem.dto.request.PasswordResetRequest;
import vidar.websystem.dto.response.MessageResponse;

public interface AuthenticationService {

    MessageResponse sendPasswordResetCode(String email);

    String getEmailByPasswordResetCode(String code);

    MessageResponse resetPassword(PasswordResetRequest request);
}
