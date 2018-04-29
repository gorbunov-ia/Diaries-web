package ru.gorbunov.diaries.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.session.jdbc.JdbcOperationsSessionRepository;
import org.springframework.stereotype.Component;

/**
 * Setting session inactive interval.
 * Delete after fix issue:
 * https://github.com/spring-projects/spring-session/issues/110
 *
 * @author Gorbunov.ia
 */
@Component
public class SessionConfig implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * Session repository.
     */
    @Autowired
    private JdbcOperationsSessionRepository sessionRepository;

    /**
     * Inject session timeout.
     */
    @Value("${server.session.timeout:1800}")
    private Integer maxInactiveInterval;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        sessionRepository.setDefaultMaxInactiveInterval(maxInactiveInterval);
    }

}
