package idv.rennnhong.backendstarterkit.web.validation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

public class BindingResultWrapper {
    BindingResult bindingResult;

    public BindingResultWrapper(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public boolean hasErrors() {
        return bindingResult.hasErrors();
    }

    public Map<String, String> asHashMap() {
        Map<String, String> errorMap = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }

}
