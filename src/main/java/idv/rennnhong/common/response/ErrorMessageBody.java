package idv.rennnhong.common.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PACKAGE)
@AllArgsConstructor
public class ErrorMessageBody  {
    private final int code;
    private final String errorMessage;
}
