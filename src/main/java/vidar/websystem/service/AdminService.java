package vidar.websystem.service;

import vidar.websystem.domain.HardwoodFloor;
import vidar.websystem.domain.SalesOrder;
import vidar.websystem.domain.User;
import vidar.websystem.dto.request.PerfumeRequest;
import vidar.websystem.dto.request.SearchRequest;
import vidar.websystem.dto.response.MessageResponse;
import vidar.websystem.dto.response.UserInfoResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface AdminService {

    Page<HardwoodFloor> getPerfumes(Pageable pageable);

    Page<HardwoodFloor> searchPerfumes(SearchRequest request, Pageable pageable);

    Page<User> getUsers(Pageable pageable);

    Page<User> searchUsers(SearchRequest request, Pageable pageable);

    SalesOrder getOrder(Long orderId);

    Page<SalesOrder> getOrders(Pageable pageable);

    Page<SalesOrder> searchOrders(SearchRequest request, Pageable pageable);

    HardwoodFloor getPerfumeById(Long perfumeId);

    MessageResponse editPerfume(PerfumeRequest perfumeRequest, MultipartFile file);

    MessageResponse addPerfume(PerfumeRequest perfumeRequest, MultipartFile file);

    UserInfoResponse getUserById(Long userId, Pageable pageable);
}
