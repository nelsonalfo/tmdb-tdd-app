package nelsonalfo.tmdbunittestsapp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.LinkedList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
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


    // Test Fixture
    @Before
    public void setUpMocks() throws Exception {
        initMocks(this); // Mockito

        mockStatic(StaticTestClass.class); // PowerMockito
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertThat(2 + 2).isEqualTo(4);

        int actualValue = 5 + 5;
        int expectedValue = 10;
        assertWithMessage("5 + 5 debe ser 10").that(actualValue).isEqualTo(expectedValue);
    }

    @Test
    public void addAndClearMethod_isExecuted() throws Exception {
        // Inicializacion - la hace el metodo setUpMocks

        // exercise, es decir, ejecutar la funcionalidad a probar
        mockedList.add("two");
        mockedList.add("tres");
        mockedList.clear();

        // Assert, es decir, verificar que lo ejecutaste funciona como deseas
        verify(mockedList).add(eq("two"));
        verify(mockedList).add(eq("tres"));
        verify(mockedList, times(1)).clear();
        verify(mockedList, never()).addAll(ArgumentMatchers.<String>anyList());
    }

    @Test
    public void getMethod_isExecuted() throws Exception {
        final String expectedValue = "primero";
        when(mockedLinkedList.get(0)).thenReturn(expectedValue);
        doNothing().when(mockedLinkedList).addFirst(eq("hola2"));

        final String actualValue = mockedLinkedList.get(0);
        mockedLinkedList.addFirst("hola");

        assertWithMessage("the expected value is '%s'", expectedValue).that(actualValue).isEqualTo(expectedValue);
        verify(mockedLinkedList).addFirst(eq("hola"));
    }

    @Test
    public void verifyStaticMethodCall() throws Exception {
        PowerMockito.when(StaticTestClass.getDummyValue()).thenReturn("testValue");
        StaticTestClass testClass = new StaticTestClass();

        String value = testClass.returnFormattedValue();

        verifyStatic(StaticTestClass.class);
        StaticTestClass.getDummyValue();
        assertThat(value).isEqualTo("<testValue>");
    }
}