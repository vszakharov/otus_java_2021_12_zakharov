package homework;

import java.util.List;
import java.util.Optional;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

import static homework.utils.ReflectionHelper.callMethod;
import static homework.utils.ReflectionHelper.getAnnotatedMethod;
import static homework.utils.ReflectionHelper.getAnnotatedMethods;
import static homework.utils.ReflectionHelper.instantiate;

public class TestRunner {

    public static void RunTestsFromClass(String className) throws ClassNotFoundException {
        Class<?> type = Class.forName(className);
        Optional<String> before = getAnnotatedMethod(type, Before.class);
        Optional<String> after = getAnnotatedMethod(type, After.class);
        List<String> tests = getAnnotatedMethods(type, Test.class);

        int all = tests.size();
        int passed = 0;
        int failed = 0;
        for (var test : tests) {
            Object object = instantiate(type);
            try {
                before.ifPresent(s -> callMethod(object, s));
                callMethod(object, test);
                passed++;
            } catch (Exception ex) {
                failed++;
            } finally {
                after.ifPresent(s -> callMethod(object, s));
            }
        }

        System.out.printf("All: %d, Passed: %d, Failed: %d", all, passed, failed);
    }
}
