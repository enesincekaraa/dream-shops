package com.enesincekara.dreamshops.security.ratelimit;

import com.google.common.util.concurrent.RateLimiter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class RateLimitService {

    private static final int MAX_REQUESTS = 5;
    private static final long WINDOW_SECONDS = 60;

    private final Map<String,Attempt> attempts = new ConcurrentHashMap<>();

    public boolean isAllowed(String key){
        long now = Instant.now().getEpochSecond();

        Attempt attempt = attempts.get(key);

        if (attempt == null || (now - attempt.windowStart) >= WINDOW_SECONDS) {
            attempts.put(key, new Attempt(1,now));
            return true;
        }

        if (attempt.count >= MAX_REQUESTS) {
            return false;
        }
        attempt.count++;
        return true;
    }


    private static class Attempt {
        int count;
        long windowStart;

        Attempt(int count, long windowStart) {
            this.count = count;
            this.windowStart = windowStart;
        }
    }
}
