package ed.lab;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class E01TopKFrequentElementsTest {

    @ParameterizedTest
    @CsvFileSource(resources = "E01.csv", useHeadersInDisplayName = true, delimiter = '|')
    void testTopKFrequent(String input, int k, String expected) {
        int[] nums = parseIntArray(input);
        int[] expectedArray = parseIntArray(expected);
        Arrays.sort(expectedArray);

        var e01test = new E01TopKFrequentElements();

        int[] result = e01test.topKFrequent(nums, k);
        Arrays.sort(result);

        assertNotNull(result, "La respuesta no debe ser null");

        assertEquals(expectedArray.length, result.length,
                String.format("Se esperaba un arreglo de longitud %s pero se obtuvo uno de longitud %s",
                        expectedArray.length, result.length));

        String resultString = Arrays.toString(result);
        String expectedString = Arrays.toString(expectedArray);

        for (int i = 0; i < result.length; i++) {

            assertEquals(expectedArray[i], result[i],
                    String.format("Error en la posición %s. Se esperaba %s y se obtuvo %s.\nSe esperaba: %s\nSe obtuvo: %s",
                            i, expectedArray[i], result[i], expectedString, resultString));
        }
    }

    private static int[] parseIntArray(String input) {
        return Arrays.stream(input.trim().split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}