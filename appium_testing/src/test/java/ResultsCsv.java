public class ResultsCsv {

    private String testName = null;
    private Boolean testPassed = false;
    private String timestamp = null;
    private String testResultMessage = "Test successful!";

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTestResultMessage() {
        return testResultMessage;
    }

    public void setTestResultMessage(String testResultMessage) {
        this.testResultMessage = testResultMessage;
    }

    public ResultsCsv() {
    }

    public ResultsCsv(String testName) {
        this.testName = testName;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public Boolean getTestPassed() {
        return testPassed;
    }

    public void setTestPassed(Boolean testPassed) {
        this.testPassed = testPassed;
    }
}
