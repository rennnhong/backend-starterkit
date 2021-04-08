package idv.rennnhong.common.query;


import lombok.Data;
import org.springframework.util.StringUtils;


@Data
public class QueryParameter {

    Integer pageNumber = 0;

    Integer rowsPerPage = 0;

    Integer pageLimit = 0;

    Integer pageOffset = 0;

    public QueryParameter() {
    }

    public QueryParameter(Integer pageNumber,
                          Integer rowsPerPage) {
        this.pageNumber = pageNumber;
        this.rowsPerPage = rowsPerPage;
        this.pageLimit = rowsPerPage;
        this.pageOffset = (pageNumber - 1) * rowsPerPage;
    }

    public QueryParameter addPageNumber(Integer pageNumber) {
        if (StringUtils.isEmpty(pageNumber)) {
            pageNumber = 1;
        }
        this.pageNumber = pageNumber;
        return this;
    }

    public QueryParameter addRowsPerPage(Integer rowsPerPage) {
        if (StringUtils.isEmpty(rowsPerPage)) {
            rowsPerPage = 10;
        }
        this.rowsPerPage = rowsPerPage;
        return this;
    }

    public QueryParameter build() {
        return new QueryParameter(pageNumber, rowsPerPage);
    }

}
