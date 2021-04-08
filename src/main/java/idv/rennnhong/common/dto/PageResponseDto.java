package idv.rennnhong.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResponseDto {

    List data;
    Long currentPage;
    Long totalPage;
    Long rowsPerPage;
    Long totalItems;
}
