package com.gmail.merikbest2015.ecommerce.service.impl;

import com.gmail.merikbest2015.ecommerce.domain.HardwoodFloor;
import com.gmail.merikbest2015.ecommerce.domain.User;
import com.gmail.merikbest2015.ecommerce.repository.HardwoodFloorsRepository;
import com.gmail.merikbest2015.ecommerce.service.CartService;
import com.gmail.merikbest2015.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        user.getPerfumeList().remove(perfume);
    }

	@Override
	public void addHardwoodWithQuantityToCart(Long floorId, Long quantity) {
		User user = userService.getAuthenticatedUser();
        HardwoodFloor floor = perfumeRepository.getOne(floorId);
        if(user.getHardwoodQuantity().containsKey(floorId))
        	user.getHardwoodQuantity().replace(floorId, user.getHardwoodQuantity().get(floorId) + quantity);
        else
        	user.getHardwoodQuantity().put(floorId, quantity);
	}
}
