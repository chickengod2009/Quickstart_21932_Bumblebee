package org.firstinspires.ftc.control.functionality;

public interface Testable {

    void test() throws InterruptedException;

    default void test2() throws InterruptedException{}
    default void test3()throws InterruptedException{}
    default void test4()throws InterruptedException{}
    default void test5() throws InterruptedException{}
    default void test6() throws InterruptedException{}

}
