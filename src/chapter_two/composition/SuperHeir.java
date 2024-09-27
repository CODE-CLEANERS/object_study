package chapter_two.composition;

public class SuperHeir {
    private final int foo;

    public void printFoo(){
        System.out.println(foo + "는 내건데?");
    }
    public SuperHeir(int foo) {
        this.foo = foo;
    }
}
