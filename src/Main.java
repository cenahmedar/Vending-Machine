import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import test.SnackMachineTest;

public class  Main {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(SnackMachineTest.class);
        System.out.println(result.getFailureCount() + " Tests Fail");
        if (result.getFailureCount() == 0) return;
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTestHeader() + " Fail");
        }
    }
}
