package nelsonalfo.tmdbunittestsapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.powermock.api.mockito.PowerMockito.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(StaticTestClass.class)
public class ExampleUnitTest {
    @Mock
    private List<String> mockedList;
    @Mock
    private LinkedList<String> mockedLinkedList;


    @Before
    public void setUpMock() throws Exception {
        initMocks(this);
        mockStatic(StaticTestClass.class);
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertThat(2 + 2).isEqualTo(4);
    }

    @Test
    public void addAndClearMethod_isExecuted() throws Exception {
        mockedList.add("one");
        mockedList.clear();

        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test
    public void getMethod_isExecuted() throws Exception {
        final String expectedValue = "primero";
        when(mockedLinkedList.get(0)).thenReturn(expectedValue);

        final String actualValue = mockedLinkedList.get(0);

        assertWithMessage("the expected value is '%s'", expectedValue).that(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void verifyStaticMethodCall() throws Exception {
        PowerMockito.when(StaticTestClass.getStubValue()).thenReturn("testValue");
        StaticTestClass testClass = new StaticTestClass();

        String value = testClass.returnFormattedValue();

        verifyStatic(StaticTestClass.class);
        StaticTestClass.getStubValue();
        assertThat(value).isEqualTo("<testValue>");
    }
}