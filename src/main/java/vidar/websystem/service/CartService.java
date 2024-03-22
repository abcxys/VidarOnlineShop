package vidar.websystem.service;

import java.util.HashMap;
import java.util.List;

import vidar.websystem.domain.CartItem;
import vidar.websystem.domain.HardwoodFloor;

public interface CartService {

    List<HardwoodFloor> getPerfumesInCart();
    
    List<CartItem> getFloorQuantitesInCart();

    void addPerfumeToCart(Long perfumeId);
    
    void addHardwoodWithQuantityToCart(Long floorId, Long quantity);
    
    void updateHardwoodWithQuantityToCart(Long floorId, Long quantity);

    void removePerfumeFromCart(Long perfumeId);
}
