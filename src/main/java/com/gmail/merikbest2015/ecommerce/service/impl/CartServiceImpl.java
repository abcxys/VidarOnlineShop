package com.gmail.merikbest2015.ecommerce.service.impl;

import com.gmail.merikbest2015.ecommerce.domain.CartItem;
import com.gmail.merikbest2015.ecommerce.domain.HardwoodFloor;
import com.gmail.merikbest2015.ecommerce.domain.User;
import com.gmail.merikbest2015.ecommerce.repository.HardwoodFloorsRepository;
import com.gmail.merikbest2015.ecommerce.service.CartService;
import com.gmail.merikbest2015.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final UserService userService;
    private final HardwoodFloorsRepository perfumeRepository;

    @Override
    public List<HardwoodFloor> getPerfumesInCart() {
        User user = userService.getAuthenticatedUser();
        return user.getPerfumeList();
    }

    @Override
    @Transactional
    public void addPerfumeToCart(Long perfumeId) {
        User user = userService.getAuthenticatedUser();
        HardwoodFloor perfume = perfumeRepository.getOne(perfumeId);
        user.getPerfumeList().add(perfume);
    }

    @Override
    @Transactional
    public void removePerfumeFromCart(Long perfumeId) {
        User user = userService.getAuthenticatedUser();
        HardwoodFloor perfume = perfumeRepository.getOne(perfumeId);
        while(user.getPerfumeList().contains(perfume))
        	user.getPerfumeList().remove(perfume);
    }

	@Override
	@Transactional
	public void addHardwoodWithQuantityToCart(Long floorId, Long quantity) {
		User user = userService.getAuthenticatedUser();
        HardwoodFloor floor = perfumeRepository.getOne(floorId);
        for(int i = 0;i < quantity;i++) {
        	user.getPerfumeList().add(floor);
        }
	}

	@Override
	public List<CartItem> getFloorQuantitesInCart() {
		User user = userService.getAuthenticatedUser();
		List<HardwoodFloor> floors = user.getPerfumeList();
		Map<HardwoodFloor, Long> counts = floors.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting()));
		List<CartItem> cart = new ArrayList<>();
		for(HardwoodFloor floor : counts.keySet())
			cart.add(new CartItem(floor, counts.get(floor)));
        return cart;
	}
}
