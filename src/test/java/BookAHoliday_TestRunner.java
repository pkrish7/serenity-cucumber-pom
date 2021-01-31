import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features="src/test/resources/features/BookAHoliday.feature",
        glue = "step_definitions/BookAHoliday")
public class BookAHoliday_TestRunner {
}