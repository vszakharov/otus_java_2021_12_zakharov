package ru.otus.aop.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import ru.otus.aop.proxy.annotations.Log;

class Ioc {

    private Ioc() {
    }

    static TestLoggingInterface createTestLogging() {
        InvocationHandler handler = new DemoInvocationHandler(new TestLoggingImpl());
        return (TestLoggingInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLoggingInterface.class}, handler);
    }

    static class DemoInvocationHandler implements InvocationHandler {
        private final TestLoggingInterface testLogging;
        private final Set<MethodInfo> annotatedMethods;

        DemoInvocationHandler(TestLoggingInterface testLogging) {
            this.testLogging = testLogging;
            annotatedMethods = getAnnotatedMethods(testLogging.getClass(), Log.class);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (annotatedMethods.contains(new MethodInfo(method))) {
                System.out.println("executed method: " + method.getName() + " param: " +
                        Arrays.stream(args)
                                .map(Object::toString)
                                .collect(Collectors.joining(", "))
                );
            }
            return method.invoke(testLogging, args);
        }

        private HashSet<MethodInfo> getAnnotatedMethods(Class<?> type, Class<? extends Annotation> annotation) {
            var annotatedMethods = new HashSet<MethodInfo>();
            for (var method : type.getDeclaredMethods()) {
                if (method.isAnnotationPresent(annotation)) {
                    annotatedMethods.add(new MethodInfo(method));
                }
            }

            return annotatedMethods;
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "testLogging=" + testLogging +
                    '}';
        }
    }
}
