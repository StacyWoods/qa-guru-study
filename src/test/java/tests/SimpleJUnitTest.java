package tests;

import org.junit.jupiter.api.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Tag("demoqa-jenkins")
public class SimpleJUnitTest {

    public static void main(String[] args) {
        // Находит классы с тестами для AnnotationsExampleTest
        // todo вынести в массив классов для проверки
        Arrays.stream(AnnotationsExampleTest.class.getDeclaredMethods())
                .filter(method ->  method.getAnnotation(BeforeAll.class) != null)
                .forEach(methodBeforeAll -> {
                    methodBeforeAll.setAccessible(true);
                    runMethodByClass(methodBeforeAll, AnnotationsExampleTest.class);
                });
        List<Method> declaredBeforeEachMethods = Arrays.stream(AnnotationsExampleTest.class.getDeclaredMethods())
                .filter(method -> method.getAnnotation(BeforeEach.class) != null)
                .collect(Collectors.toList());
        List<Method> declaredAfterEachMethods = Arrays.stream(AnnotationsExampleTest.class.getDeclaredMethods())
                .filter(method -> method.getAnnotation(AfterEach.class) != null)
                .collect(Collectors.toList());

        Arrays.stream(AnnotationsExampleTest.class.getDeclaredMethods())
                .filter(method -> (method.getAnnotation(Test.class) != null && method.getAnnotation(Disabled.class) == null))
                .forEach(methodTest -> {
                    methodTest.setAccessible(true);
                    runMethodsByClass(declaredBeforeEachMethods, AnnotationsExampleTest.class);
                    runMethodByClass(methodTest, AnnotationsExampleTest.class);
                    runMethodsByClass(declaredAfterEachMethods, AnnotationsExampleTest.class);
                });

        Arrays.stream(AnnotationsExampleTest.class.getDeclaredMethods())
                .filter(method -> method.getAnnotation(AfterAll.class) != null)
                .forEach(methodAfterAll -> {
                    methodAfterAll.setAccessible(true);
                    runMethodByClass(methodAfterAll, AnnotationsExampleTest.class);
                });
    }

    private static void runMethodByClass(Method method, Class<?> clazz) {
        try {
            method.invoke(clazz.getDeclaredConstructor().newInstance());
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) { // Тест упал
            System.out.println("Тест упал: " + e.getCause().getMessage());
        }
    }

    private static void runMethodsByClass(List<Method> methodsToRun, Class<?> clazz) {
        for (Method methodToRun : methodsToRun) {
            methodToRun.setAccessible(true);
            runMethodByClass(methodToRun, clazz);
        }
    }
}
