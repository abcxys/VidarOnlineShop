package vidar.websystem.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class SearchRequest {
    private List<String> colours;
    private List<String> widths;
    private Integer price = 0;
    private String searchType;
    private String text;
}
