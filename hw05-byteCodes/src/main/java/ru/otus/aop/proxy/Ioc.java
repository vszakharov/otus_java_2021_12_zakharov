package ru.otus.aop.proxy;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
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

        DemoInvocationHandler(TestLoggingInterface testLogging) {
            this.testLogging = testLogging;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Class<? extends Annotation> annotation = Log.class;
            Class<?> type = testLogging.getClass();
            var parameters = method.getParameterTypes();
            var originalMethod = type.getDeclaredMethod(method.getName(), parameters);
            if (originalMethod.isAnnotationPresent(annotation)) {
                System.out.println("executed method: " + method.getName() + " param: " +
                        Arrays.stream(args)
                                .map(Object::toString)
                                .collect(Collectors.joining(", "))
                );
            }
            return method.invoke(testLogging, args);
        }

        @Override
        public String toString() {
            return "DemoInvocationHandler{" +
                    "testLogging=" + testLogging +
                    '}';
        }
    }
}
