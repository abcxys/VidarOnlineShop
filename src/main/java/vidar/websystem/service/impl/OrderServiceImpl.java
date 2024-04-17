package vidar.websystem.service.impl;

import lombok.RequiredArgsConstructor;
import vidar.websystem.constants.ErrorMessage;
import vidar.websystem.domain.HardwoodFloor;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.OrderRequest;
import vidar.websystem.repository.SalesOrderRepository;
import vidar.websystem.service.OrderService;
import vidar.websystem.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserService userService;
    private final SalesOrderRepository salesOrderRepository;
    private final ModelMapper modelMapper;
    private final MailService mailService;

    @Override
    public SalesOrder getOrder(Long orderId) {
        User user = userService.getAuthenticatedUser();
        return salesOrderRepository.getByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessage.ORDER_NOT_FOUND));
    }

    @Override
    public List<HardwoodFloor> getOrdering() {
        User user = userService.getAuthenticatedUser();
        return user.getPerfumeList();
    }

    @Override
    public Page<SalesOrder> getUserOrdersList(Pageable pageable) {
        User user = userService.getAuthenticatedUser();
        return salesOrderRepository.findOrderByUserId(user.getId(), pageable);
    }

    @Override
    @Transactional
    public Long postOrder(User user, OrderRequest orderRequest) {
        SalesOrder salesOrder = modelMapper.map(orderRequest, SalesOrder.class);
        salesOrder.setUser(user);
        salesOrder.getHardwoodfloors().addAll(user.getPerfumeList());
        salesOrderRepository.save(salesOrder);
        user.getPerfumeList().clear();
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("order", salesOrder);
        mailService.sendMessageHtml(salesOrder.getEmail(), "SalesOrder #" + salesOrder.getId(), "salesOrder-template", attributes);
        return salesOrder.getId();
    }
}
