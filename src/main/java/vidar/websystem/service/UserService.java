package vidar.websystem.service;

import vidar.websystem.domain.SalesOrder;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.ChangePasswordRequest;
import vidar.websystem.dto.request.EditUserRequest;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.dto.response.MessageResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getAuthenticatedUser();

    Page<SalesOrder> searchUserOrders(SearchRequest request, Pageable pageable);

    MessageResponse editUserInfo(EditUserRequest request);

    MessageResponse changePassword(ChangePasswordRequest request);
}
