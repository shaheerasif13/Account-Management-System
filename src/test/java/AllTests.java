import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AccountManagementSystemTest.class, CheckingAccountTest.class, SavingsAccountTest.class })
public class AllTests {

}
