package utils1;

import java.util.HashMap;
import java.util.Map;

public class GlobalContext {

	 private static final Map<String, Object> globalDataMap = new HashMap<>();

	    public static void setGlobalData(String key, Object value) {
	        globalDataMap.put(key, value);
	    }

	    public static Object getGlobalData(String key) {
	        return globalDataMap.get(key);
	    }

	    public static void clearGlobalData() {
	        globalDataMap.clear();
	        System.out.println("Global context data cleared.");
	    }
}
