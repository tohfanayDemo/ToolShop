package utils;

public class TestContextManager {
	
    private static final ThreadLocal<Context> threadLocalContext = ThreadLocal.withInitial(Context::new);

    public static Context getContext() {
        return threadLocalContext.get();
    }

    public static void removeContext() {
        threadLocalContext.remove();
    }
}
