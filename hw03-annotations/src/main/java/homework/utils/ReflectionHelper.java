package homework.utils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class ReflectionHelper {
    private ReflectionHelper() {
    }

    public static Object getFieldValue(Object object, String name) {
        try {
            var field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            return field.get(object);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void setFieldValue(Object object, String name, Object value) {
        try {
            var field = object.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(object, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object callMethod(Object object, String name, Object... args) {
        try {
            var method = object.getClass().getDeclaredMethod(name, toClasses(args));
            method.setAccessible(true);
            return method.invoke(object, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T instantiate(Class<T> type, Object... args) {
        try {
            if (args.length == 0) {
                return type.getDeclaredConstructor().newInstance();
            } else {
                Class<?>[] classes = toClasses(args);
                return type.getDeclaredConstructor(classes).newInstance(args);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Class<?>[] toClasses(Object[] args) {
        return Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
    }

    public static Optional<String> getAnnotatedMethod(Class<?> type, Class<? extends Annotation> annotation) {
        for (var method : type.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                return Optional.of(method.getName());
            }
        }

        return Optional.empty();
    }

    public static List<String> getAnnotatedMethods(Class<?> type, Class<? extends Annotation> annotation) {
        List<String> annotatedMethods = new ArrayList<>();
        for (var method : type.getDeclaredMethods()) {
            if (method.isAnnotationPresent(annotation)) {
                annotatedMethods.add(method.getName());
            }
        }

        return annotatedMethods;
    }
}
