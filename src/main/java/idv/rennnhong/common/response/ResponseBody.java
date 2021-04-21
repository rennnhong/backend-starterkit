package idv.rennnhong.common.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import idv.rennnhong.common.query.PageableResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Collection;

@Getter
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class ResponseBody<T> {

    T payload;
    Object metadata;

    public static <T> SingleResponseBody<T> newSingleBody(T result) {
        SingleResponseBody<T> singleResult = new SingleResponseBody();
        singleResult.setPayload(result);
        return singleResult;
    }

    public static <T> ResponseBody<T> newSingleBody(T result, Object metadata) {
        SingleResponseBody<T> body = newSingleBody(result);
        body.setMetadata(metadata);
        return body;
    }

    public static <T> CollectionResponseBody<T> newCollectionBody(Collection<T> result) {
        CollectionResponseBody<T> body = new CollectionResponseBody();
        body.setPayload(result);
        return body;
    }

    public static <T> CollectionResponseBody<T> newCollectionBody(Collection<T> result, Object metadata) {
        CollectionResponseBody<T> body = newCollectionBody(result);
        body.setMetadata(metadata);
        return body;
    }

    public static <T> CollectionResponseBody<T> newPageableBody(
            Collection<T> result, int number, int size, long totalElements, int totalPages) {
        CollectionResponseBody<T> body = new CollectionResponseBody();
        PageMetadata pageMetadata = new PageMetadata(size, totalElements, totalPages, number);
        body.setPayload(result);
        body.setMetadata(pageMetadata);
        return body;
    }

    public static <T> ResponseBody<Collection<T>> newPageableBody(PageableResult<T> pageableResult) {
        return newPageableBody(
                pageableResult.getResult(),
                pageableResult.getNumber(),
                pageableResult.getSize(),
                pageableResult.getTotalElements(),
                pageableResult.getTotalPages()
        );
    }

    public static ErrorMessageBody newErrorMessageBody(ErrorMessages error) {
        ErrorMessageBody body = new ErrorMessageBody(error.getCode(), error.getErrorMessage());
        return body;
    }

    public static ErrorMessageBody newErrorMessageBody(ErrorMessages error, Object details) {
        ErrorMessageBody body = newErrorMessageBody(error);
        body.setDetails(details);
        return body;
    }

    @Getter
    @Accessors(chain = true)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageMetadata {

        private final int size;
        private final long totalElements;
        private final int totalPages;
        private final int number;

        public PageMetadata(int size, long totalElements, int totalPages, int number) {
            this.size = size;
            this.totalElements = totalElements;
            this.totalPages = totalPages;
            this.number = number;
        }
    }
}
