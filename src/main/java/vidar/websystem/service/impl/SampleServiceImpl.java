package vidar.websystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vidar.websystem.domain.HardwoodFloor;
import vidar.websystem.domain.User;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.service.SampleService;

import java.math.BigDecimal;

/**
 * @author yishi.xing
 * create datetime 7/1/2024 10:30 PM
 * description
 */
@Service
@RequiredArgsConstructor
public class SampleServiceImpl implements SampleService {
    /**
     * @param user Authenticated user
     * @param product Hardwood product associated with the sample
     * @param price Price of the sample
     * @return Resulting message response
     */
    @Override
    public MessageResponse addSample(User user, HardwoodFloor product, BigDecimal price) {
        return null;
    }
}
