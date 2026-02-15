class SecondClass {
    private int firstNum;
    private int secondNum;
   
    public SecondClass(int f, int s){
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


class ThirdTask{
    public static void main(String[] args) {
        SecondClass o = new SecondClass(0, 0);
        int i, j;
        for (i = 1; i <= 8; i++) {
            for(j = 1; j <= 8; j++) {
                o.setFirstNum(i);
                o.setSecondNum(j);
                System.out.print(o.sum());
                System.out.print(" ");
            }
            System.out.println();
        }

    }
}
