package com.gmail.merikbest2015.ecommerce.service;

import com.gmail.merikbest2015.ecommerce.domain.HardwoodFloor;

import java.util.List;

public interface CartService {

    List<HardwoodFloor> getPerfumesInCart();

    void addPerfumeToCart(Long perfumeId);

    void removePerfumeFromCart(Long perfumeId);
}
