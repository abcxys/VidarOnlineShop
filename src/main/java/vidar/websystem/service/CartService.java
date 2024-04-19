package vidar.websystem.service;

import java.util.List;

import vidar.websystem.domain.CartItem;
import vidar.websystem.domain.DatatablesView;
import vidar.websystem.domain.Dealer;
import vidar.websystem.domain.HardwoodFloor;

public interface CartService {

    List<HardwoodFloor> getPerfumesInCart();
    
    List<CartItem> getFloorQuantitesInCart();

    List<Dealer> getDealers();

    List<Dealer> getActiveDealers();

    Dealer getDealerById(Long id);

    DatatablesView<CartItem> getCartItemsTable();

    void addPerfumeToCart(Long perfumeId);
    
    void addHardwoodWithQuantityToCart(Long floorId, Long quantity);
    
    void updateHardwoodWithQuantityToCart(Long floorId, Long quantity);

    void removePerfumeFromCart(Long perfumeId);
}
