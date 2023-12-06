package chapter_two.composition;

public class Heritor extends SuperHeir {
    public Heritor(int foo) {
        super(foo);
    }

    public void iGotFooTo(){
        this.printFoo();
    }
}