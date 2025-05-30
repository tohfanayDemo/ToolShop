# 🛠️ ToolShop Test Automation Framework

### 📁 Overview

ToolShop is a test automation framework built with Selenium, Cucumber, and TestNG, designed for scalable, maintainable, and cross-browser end-to-end UI testing.It supports:

- ✅ Scenario-scoped and Global-scoped data contexts
- ✅ Cross-browser testing (Chrome, Firefox, etc.)
- ✅ Parallel execution with thread-local context
- ✅ Retry logic, test reporting, and hooks
- ✅ Page Object Model (POM) architecture <br><br>

---
### 📦 Tech Stack

**Language** : Java  
**Test Framework** : Cucumber + TestNG  
**Build Tool**: Maven  
**Browser Automation**: Selenium WebDriver  
**Parallel Execution**: Thread-safe setup using ThreadLocal  
**Reporting**: ExtentReports and Allure  <br><br>

---

### 🧪 Project Structure

**ToolShop**/  
**src**/  
├── main/java  
│   ├── constants/  
│   ├── driver/  
│   ├── logger/  
│   ├── models/  
│   ├── pages/  
│   └── utils/  
├── test/java  
│   ├── hooks/  
│   ├── runner/  
│   └── stepDefs/  
└── test/resources  
    ├── config/  
    ├── features/  
    ├── testData/  
    └── testNG/  
├── README.md # This file  
├── **pom.xml**  <br><br>

---

### 🚀 How to Run Tests  
🔧 **Using Maven:**

1. *Cross-Browser Execution*  
mvn test <br><br>


2. *Tagged Scenario Execution*    
mvn test -Dcucumber.filter.tags=@smoke <br><br>

3. *Specific Browser Execution (for a given tagged scenario)*  
mvn test -Dcucumber.filter.tags=@smoke -Dbrowser=chrome   <br><br>

---

### 🌐Supported Browsers:  
Chrome  
Firefox  
Edge   <br><br>

---

### 📊 Test Reports
Test execution reports would be available under:

### 1. Allure
- root directory (run "allure serve allure-results")

### 2. Extent and PDF
- /target/ExtentReports/

### 3. Cucumber Reports
- /target/cucumber-reports/  <br><br>

---

### 👤 Author  
Tohfatul Nayeem  
📧 tohfa.nay@gmail.com  
💻 GitHub: [https://github.com/tohfanayDemo/ToolShop]