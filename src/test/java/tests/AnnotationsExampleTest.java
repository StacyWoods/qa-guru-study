package tests;

import org.junit.jupiter.api.*;

@DisplayName("Класс с демонстрационными тестами")
@Tag("examples")
public class AnnotationsExampleTest {

    @DisplayName("Before All")
    @BeforeAll
    static void setup() {
        Assertions.assertTrue(1 < 2);
        System.out.println("Before All");
    }

    @DisplayName("Before Each")
    @BeforeEach
    void checkBeforeEach() {
        Assertions.assertEquals("Foo", "Foo");
        System.out.println("Before Each");
    }

    @Disabled("CODETOOLS-7902347")
    @DisplayName("Демонстрационный тест")
    @Test
    void firstTest() {
        // Вот тут проверим ...
        Assertions.assertTrue(3 > 2);
        Assertions.assertFalse(3 < 2);
        Assertions.assertEquals("Foo", "Foo");
        Assertions.assertAll(
                () -> Assertions.assertTrue(3 < 2),
                () -> Assertions.assertTrue(3 > 2)
        );
        System.out.println("first Test");
    }

    @DisplayName("Демонстрационный тест № 2")
    @Test
    void secondTest() {
        Assertions.assertTrue(1 < 2);
        System.out.println("second Test");
    }

    @DisplayName("Демонстрационный тест № 3")
    @Test
    void thirdTest() {
        Assertions.assertAll(
                () -> Assertions.assertFalse(3 < 2),
                () -> Assertions.assertTrue(3 > 2)
        );
        System.out.println("third Test");
    }

    @DisplayName("After Each")
    @AfterEach
    void checkAfterEach() {
        Assertions.assertEquals("Foo", "Foo");
        System.out.println("After Each");
    }

    @DisplayName("After All")
    @AfterAll
    static void down() {
        Assertions.assertFalse(3 < 2);
        System.out.println("After All");
    }
}
