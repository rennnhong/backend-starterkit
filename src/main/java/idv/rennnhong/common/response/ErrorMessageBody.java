package idv.rennnhong.common.response;

import lombok.*;

@Getter
@Setter(AccessLevel.PACKAGE)
@RequiredArgsConstructor
public class ErrorMessageBody {
    private final int code;
    private final String message;
    private Object details;
}
