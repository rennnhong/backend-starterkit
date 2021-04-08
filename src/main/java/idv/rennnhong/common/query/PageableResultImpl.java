package idv.rennnhong.common.query;


import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public final class PageableResultImpl<T> implements PageableResult<T>, Serializable {


    private int size, number, totalPages;
    private long totalElements;

    private Collection<T> result;

    public PageableResultImpl(int size, int number, int totalPages, long totalElements, Collection<T> result) {
        this.size = size;
        this.number = number;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.result = result;
    }

}
