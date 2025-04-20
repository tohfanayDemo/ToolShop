package utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Context {

	List<Map<String, String>> testData;
	
	public Context() {
		
		testData = new ArrayList<>();
	}

	public List<Map<String, String>> getTestData() {
		return testData;
	}

	public void setTestData(List<Map<String, String>> testData) {
		this.testData = testData;
	}
	
	public Map<String, String> getTestDataForTestCase(String testCase) {
        List<Map<String, String>> dataList = getTestData();
        for (Map<String, String> data : dataList) {
            if (data.get("Testcase").equalsIgnoreCase(testCase)) {
                return data;
            }
        }
        return null; 
    }
	
	public void emptyDataMap() {
		if (getTestData() != null) {
			getTestData().clear();  //removes all the maps from the list. The List object itself remains, but it's now empty.
            System.out.println("Test data cleared.");
        }
	}
	
	
}
