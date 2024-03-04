package com.gmail.merikbest2015.ecommerce.service;

import com.gmail.merikbest2015.ecommerce.domain.CartItem;
import com.gmail.merikbest2015.ecommerce.domain.HardwoodFloor;

import java.util.HashMap;
import java.util.List;

public interface CartService {

    List<HardwoodFloor> getPerfumesInCart();
    
    List<CartItem> getFloorQuantitesInCart();

    void addPerfumeToCart(Long perfumeId);
    
    void addHardwoodWithQuantityToCart(Long floorId, Long quantity);

    void removePerfumeFromCart(Long perfumeId);
}
