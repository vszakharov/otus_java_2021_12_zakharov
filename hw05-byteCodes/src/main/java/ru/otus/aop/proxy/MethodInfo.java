package ru.otus.aop.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class MethodInfo {

    //Имя метода
    private final String name;

    //Параметры
    private final Class<?>[] parameters;

    public MethodInfo(Method method) {
        this.name = method.getName();
        this.parameters = method.getParameterTypes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MethodInfo that = (MethodInfo) o;
        return Objects.equals(name, that.name) && Arrays.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }
}
