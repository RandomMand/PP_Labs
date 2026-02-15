package firstpackage;

public class FirstPackage {

    private int firstNum;
    private int secondNum;

    public FirstPackage(int f, int s){
        firstNum = f;
        secondNum = s;
    }

    public void setFirstNum(int value){
        firstNum = value;
    }

    public void setSecondNum(int value){
        secondNum = value;
    }

    public int sum(){
        return firstNum + secondNum;
    }
}
