package idv.rennnhong.backendstarterkit.persistence.audit;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    /**
     * 查出目前使用者
     *
     * @return 使用者名稱
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("宋仲基");
    }

}

