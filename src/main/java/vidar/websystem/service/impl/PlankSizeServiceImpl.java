package vidar.websystem.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vidar.websystem.domain.PlankSize;
import vidar.websystem.repository.PlankSizeRepository;
import vidar.websystem.service.PlankSizeService;

import java.util.Collections;
import java.util.List;

/**
 * @author yishi.xing
 * create datetime 7/23/2024 11:07 PM
 * description
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlankSizeServiceImpl implements PlankSizeService {

    private final PlankSizeRepository plankSizeRepository;
    /**
     * @return List of PlankSize in database
     */
    @Override
    public List<PlankSize> getAllPlankSizes() {
        return plankSizeRepository.findAll();
    }
}
