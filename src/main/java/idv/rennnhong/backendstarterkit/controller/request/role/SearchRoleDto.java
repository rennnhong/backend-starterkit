package idv.rennnhong.backendstarterkit.controller.request.role;

import lombok.Data;


@Data
public class SearchRoleDto {

    Integer pageNumber;
    Integer rowsPerPage;
    String code;
    String name;
}
