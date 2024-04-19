package vidar.websystem.service.impl;

import lombok.RequiredArgsConstructor;
import vidar.websystem.domain.*;
import vidar.websystem.repository.DealerRepository;
import vidar.websystem.repository.HardwoodFloorsRepository;
import vidar.websystem.repository.WarehouseRepository;
import vidar.websystem.service.CartService;
import vidar.websystem.service.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final HardwoodFloorsRepository hardwoodFloorsRepository;
    private final DealerRepository dealerRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public List<HardwoodFloor> getPerfumesInCart() {
        User user = userService.getAuthenticatedUser();
        return user.getPerfumeList();
    }

    @Override
    @Transactional
    public void addPerfumeToCart(Long perfumeId) {
        User user = userService.getAuthenticatedUser();
        HardwoodFloor perfume = hardwoodFloorsRepository.getOne(perfumeId);
        user.getPerfumeList().add(perfume);
    }

    @Override
    @Transactional
    public void removePerfumeFromCart(Long perfumeId) {
        User user = userService.getAuthenticatedUser();
        HardwoodFloor perfume = hardwoodFloorsRepository.getOne(perfumeId);
        while(user.getPerfumeList().contains(perfume))
        	user.getPerfumeList().remove(perfume);
    }

	@Override
	@Transactional
	public void addHardwoodWithQuantityToCart(Long floorId, Long quantity) {
		User user = userService.getAuthenticatedUser();
        HardwoodFloor floor = hardwoodFloorsRepository.getOne(floorId);
        for(int i = 0;i < quantity;i++) {
        	user.getPerfumeList().add(floor);
        }
	}

    @Override
    public DatatablesView<CartItem> getCartItemsTable(){
        DatatablesView<CartItem> cartView  = new DatatablesView<>();
        List<CartItem> cartList = getFloorQuantitesInCart();
        List<SalesOrderItem> salesOrderItemList = cartList.stream().map(cartItem ->{
            FloorColorSize floorColorSize = hardwoodFloorsRepository.findFloorColorById(cartItem.getFloor().getId());
            return new SalesOrderItem(floorColorSize, cartItem.getQuantity());
        }).collect(Collectors.toList());
        int count = cartList.size();
        cartView.setData(cartList);
        cartView.setRecordsTotal(count);
        return cartView;
    }

	@Override
	public List<CartItem> getFloorQuantitesInCart() {
		User user = userService.getAuthenticatedUser();
		List<HardwoodFloor> floors = user.getPerfumeList();
		Map<HardwoodFloor, Long> counts = floors.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		List<CartItem> cart = new ArrayList<>();
		for(HardwoodFloor floor : counts.keySet())
			cart.add(new CartItem(floor, new BigDecimal(counts.get(floor))));
        return cart;
	}

    /**
     * @return Entire dealers' list.
     */
    @Override
    public List<Dealer> getDealers() {
        return dealerRepository.findAll();
    }

    /**
     * @return Active Dealer's list.
     */
    @Override
    public List<Dealer> getActiveDealers() {
        return dealerRepository.findByActiveTrue();
    }

    /**
     * @param id Dealer's id.
     * @return the full dealer entity object.
     */
    @Override
    public Dealer getDealerById(Long id) {
        return dealerRepository.findById(id).orElse(null);
    }

    /**
     * @return List of available warehouses.
     */
    @Override
    public List<Warehouse> getWarehouses() {
        return warehouseRepository.findAll();
    }

    @Override
	@Transactional
	public void updateHardwoodWithQuantityToCart(Long floorId, Long quantity) {
		User user = userService.getAuthenticatedUser();
        HardwoodFloor perfume = hardwoodFloorsRepository.getOne(floorId);
        while(user.getPerfumeList().contains(perfume))
        	user.getPerfumeList().remove(perfume);
        while(quantity-- > 0)
        	user.getPerfumeList().add(perfume);
	}
}
