package dev.adi.testapp.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class NotificationID {
    private static final AtomicInteger notiIdGen = new AtomicInteger(0);

    public static int getId() {
        return notiIdGen.incrementAndGet();
    }
}
