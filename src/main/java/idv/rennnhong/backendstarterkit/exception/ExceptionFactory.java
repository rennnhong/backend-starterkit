package idv.rennnhong.backendstarterkit.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Component
public class ExceptionFactory {

    private static ExceptionTextTemplate exceptionTextTemplate;

    private static Map<ExceptionType, Function<String, RuntimeException>> exceptions = new HashMap<>();

    @Autowired
    public ExceptionFactory(ExceptionTextTemplate exceptionTextTemplate) {
        ExceptionFactory.exceptionTextTemplate = exceptionTextTemplate;
        //初始化
        register(ExceptionType.ENTITY_NOT_FOUND, (message) -> new EntityNotFoundException(message));
        register(ExceptionType.DUPLICATE_ENTITY, (message) -> new DuplicateEntityException(message));
        register(ExceptionType.ENTITY_EXIST_RELATED, (message) -> new ExistRelatedException(message));
    }

    public static void register(ExceptionType exceptionType, Function<String, RuntimeException> action) {
        ExceptionFactory.exceptions.put(exceptionType, action);
    }

    public static void unregister(ExceptionType exceptionType) {
        ExceptionFactory.exceptions.remove(exceptionType);
    }

    public static RuntimeException newException(String messageTemplate, String... args) {
        return new RuntimeException(format(messageTemplate, args));
    }

    public static RuntimeException newException(ExceptionType exceptionType, String... args) {
        String key = getMessageTemplateKey(null, exceptionType);
        return newException(exceptionType, key, args);
    }

    public static RuntimeException newException(GroupType groupType, ExceptionType exceptionType, String... args) {
        String key = getMessageTemplateKey(groupType, exceptionType);
        return newException(exceptionType, key, args);
    }

    public static RuntimeException newExceptionWithId(ExceptionType exceptionType, Integer id, String... args) {
        String key = getMessageTemplateKey(null, exceptionType).concat(".").concat(id.toString());
        return newException(exceptionType, key, args);
    }

    public static RuntimeException newExceptionWithId(GroupType groupType, ExceptionType exceptionType, Integer id, String... args) {
        String key = getMessageTemplateKey(groupType, exceptionType).concat(".").concat(id.toString());
        return newException(exceptionType, key, args);
    }

    private static RuntimeException newException(ExceptionType exceptionType, String key, String... args) {
        String message = format(key, args);
        return ExceptionFactory.exceptions.get(exceptionType).apply(message);
    }

    private static String getMessageTemplateKey(GroupType groupType, ExceptionType exceptionType) {
        if (groupType == null) return "default".concat(".").concat(exceptionType.getValue()).toLowerCase();
        return groupType.name().concat(".").concat(exceptionType.getValue()).toLowerCase();
    }

    private static String format(String templateKey, String... args) {
        Optional<String> templateContent = Optional.ofNullable(exceptionTextTemplate.getContent(templateKey));
        if (templateContent.isPresent()) {
            return MessageFormat.format(templateContent.get(), (Object[]) args);
        }
        return null;
    }


    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class DuplicateEntityException extends RuntimeException {
        public DuplicateEntityException(String message) {
            super(message);
        }
    }
    public static class ExistRelatedException extends RuntimeException {
        public ExistRelatedException(String message) {
            super(message);
        }
    }
}
