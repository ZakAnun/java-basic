package enumdemo;

public class Main {

    public static void main(String[] args) {
        int a = 4;
        int b = 5;
        int addResult = Operator.ADD.calculate(a, b);
        int subResult = Operator.SUB.calculate(a, b);
        int mulResult = Operator.MUL.calculate(a, b);
        int divResult = Operator.DIV.calculate(a, b);
        System.out.println("Operator.ADD = " + addResult);
        System.out.println("Operator.SUB = " + subResult);
        System.out.println("Operator.MUL = " + mulResult);
        System.out.println("Operator.DIV = " + divResult);

        System.out.println();

        SingleObject singleObject1 = SingleObject.getInstance();
        SingleObject singleObject2 = SingleObject.getInstance();
        SingleObject singleObject3 = SingleObject.getInstance();

        System.out.println("singleObject1 = " + singleObject1);
        System.out.println("singleObject2 = " + singleObject2);
        System.out.println("singleObject3 = " + singleObject3);
    }
}
