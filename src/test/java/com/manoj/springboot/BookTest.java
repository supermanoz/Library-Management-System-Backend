package com.manoj.springboot;

import com.manoj.springboot.serviceImpl.BookServiceImpl;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookTest {

    BookServiceImpl bookService;

    @BeforeEach
    void init(){
        bookService=new BookServiceImpl();
    }

    @Nested
    @DisplayName("Math Tests")
//    @Tag("Math Test") --> for this configuration is needed
    class MathTest{
//        @Test
        @DisplayName("I am a sum testing method")
        @RepeatedTest(2)
        void sumTest(){
            int actualResult=bookService.sum(3,10);
            int expectedResult=15;
            assertEquals(expectedResult,actualResult);
        }

        @Test
        @Disabled
        void prodTest(){
            assertEquals(15,bookService.prod(2,5),"Bruh this Book service prod method is wrong man!");
        }

    }

    @Test
    void divideTest(){
        assertEquals(5,bookService.divide(15,3));
    }
}
