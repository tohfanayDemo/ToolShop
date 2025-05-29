package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalContext {
	
	private static final GlobalContext INSTANCE = new GlobalContext();
    public static GlobalContext getInstance() {
        return INSTANCE;
    }

	private List<Map<String, String>> testData;
    private Map<String, Object> globalDataMap;

    public GlobalContext() {
        testData = new ArrayList<>();
        globalDataMap = new HashMap<>();
    }

    public List<Map<String, String>> getTestData() {
        return testData;
    }

    public void setTestData(List<Map<String, String>> testData) {
        this.testData = testData;
    } 

    public void emptyTestDataMap() {
        testData.clear();
        System.out.println("Test data cleared.");
    }

    public void setGlobalData(String key, Object value) {
    	globalDataMap.put(key, value);
    }

    public Object getGlobalData(String key) {
        return globalDataMap.get(key);
    }

    public void emptyGlobalDataMap() {
    	globalDataMap.clear();
        System.out.println("GlobalDataMap data cleared.");
    }
}
