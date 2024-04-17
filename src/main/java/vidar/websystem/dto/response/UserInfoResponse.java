package vidar.websystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.domain.User;

import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
public class UserInfoResponse {
    private User user;
    private Page<SalesOrder> orders;
}
