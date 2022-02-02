package homework;

import homework.annotations.After;
import homework.annotations.Before;
import homework.annotations.Test;

public class ClassTest {

    @Before
    void setUp() {
        System.out.println("Before");
    }

    @Test
    void test1() {
        System.out.println("Test1");
    }

    @Test
    void test2() {
        throw new RuntimeException("Test2");
    }

    @Test
    void test3() {
        System.out.println("Test3");
    }

    @After
    void tearDown() {
        System.out.println("After");
    }
}
