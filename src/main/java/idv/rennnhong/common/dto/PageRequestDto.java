package idv.rennnhong.common.dto;

import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class PageRequestDto {

    @Min(1)
    Integer pageNumber;

    @Min(1)
    Integer rowsPerPage;
}
