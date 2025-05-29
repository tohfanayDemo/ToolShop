package utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

    private Map<String, Object> runtimeDataMap;

    public Context() {
        runtimeDataMap = new HashMap<>();
    }

    // Scenario runtime data
    public void setRuntimeData(String key, Object value) {
        runtimeDataMap.put(key, value);
    }

    public Object getRuntimeData(String key) {
        return runtimeDataMap.get(key);
    }

    public void emptyRuntimeDataMap() {
        runtimeDataMap.clear();
        System.out.println("Runtime data cleared.");
    }
    
    /*
     * public Map<String, String> getTestDataForTestCase(String testCase) {
        return testData.stream()
                       .filter(data -> testCase.equalsIgnoreCase(data.get("Testcase")))
                       .findFirst()
                       .orElse(null);
    }
     * */
    
    public Map<String, String> getTestDataForTestCase(List<Map<String, String>> testData, String testCase) {
        for (Map<String, String> data : testData) {
            if (data.get("Testcase").equalsIgnoreCase(testCase)) {
                return data;
            }
        }
        return null;
    }
}
