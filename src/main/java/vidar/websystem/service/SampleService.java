package vidar.websystem.service;

import vidar.websystem.domain.User;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.domain.HardwoodFloor;

import java.math.BigDecimal;

/**
 * @author yishi.xing
 * create datetime 7/1/2024 10:30 PM
 * description
 */
public interface SampleService {

    MessageResponse addSample(User user, HardwoodFloor product, BigDecimal price);
}
