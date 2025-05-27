package utils1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {

	private List<Map<String, String>> testData;
    private Map<String, Object> runtimeDataMap;

    public Context() {
        testData = new ArrayList<>();
        runtimeDataMap = new HashMap<>();
    }

    // Scenario test data
    public List<Map<String, String>> getTestData() {
        return testData;
    }

    public void setTestData(List<Map<String, String>> testData) {
        this.testData = testData;
    }

    /*
     * public Map<String, String> getTestDataForTestCase(String testCase) {
        return testData.stream()
                       .filter(data -> testCase.equalsIgnoreCase(data.get("Testcase")))
                       .findFirst()
                       .orElse(null);
    }
     * */
    
    public Map<String, String> getTestDataForTestCase(String testCase) {
        for (Map<String, String> data : testData) {
            if (data.get("Testcase").equalsIgnoreCase(testCase)) {
                return data;
            }
        }
        return null;
    }
    

    public void emptyDataMap() {
        testData.clear();
        System.out.println("Test data cleared.");
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
}
