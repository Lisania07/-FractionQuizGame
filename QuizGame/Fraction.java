/**
 * Class Fraction-Quiz Game
 *
 * @Lisa Dai
 * @version 1.0
 * @param numerator, a numerator of the returned fraction.
 * @param denominator, a denominator of the returned fraction.
 *
 */
import java.util.Scanner;
import java.util.Random;
public class Fraction{
    //Instance Variable
    public int numerator,denominator;
    private String numeratorString=String.valueOf(numerator);  
    private  String denominatorString=String.valueOf(denominator);  

    //Constructors
    /*
     * Default constructor
     * @No param A Fraction object with declared initial value
     */
    public Fraction(){
        int numerator=(int)(Math.random()*5);
        int denominator=(int)(Math.random()*5)+5;
        if (numerator==0) numerator=1;
        if (denominator==0) denominator=1;

        this.numerator=numerator;
        this.denominator=denominator;
    }

    /*
     *  Parameterized constructor
     * @param numeratorValue,the desired numerator
     * @param denominatorValue, the desired denominator
     * A Fraction object takes two int parameters
     */
    public Fraction(int numeratorValue,int denominatorValue){
        numerator=numeratorValue;
        denominator=denominatorValue;
    }

    /*
     * @Fraction constructor
     * @param farc A fraction object 
     */
    public Fraction(String frac){
        int slashIndex=frac.indexOf("/");
        if (slashIndex>0){
            String n=frac.substring(0,slashIndex);
            String d=frac.substring(slashIndex+1);
            numerator=Integer.parseInt(n);
            denominator= Integer.parseInt(d);
            fixDenominator();
        }
    }

    /*
     * Copy ocnstructor
     * @param frac A Fraction object to copy.
     */
    public Fraction(Fraction frac){
        numerator=frac.numerator;
        denominator=frac.denominator;
        fixDenominator();
    }
    //Behaviour Methods
    private void fixDenominator(){
        if(denominator==0){
            System.out.println("Error:denoiminator can not be 0, change to 1");
            denominator=1;
        }
    }
    //Accessor Methods
    public int getNumerator(){
        return numerator;
    }

    public int getDenominator(){
        return denominator;
    }

    public String toString(){
        return numerator+"/"+denominator;
    }

    public double toDouble(){
        return (double) numerator/denominator;
    }

    //GCF
    private static int findGcf(int a, int b){
        a=Math.abs(a);
        b=Math.abs(b);
        int max=Math.max(a,b);
        int min=Math.min(a,b);
        while(max!=min){
            int diff = max-min;
            max = Math.max(diff, min);
            min = Math.min(diff, min);
        }
        return max;
    }

    //Mutator
    public void reduce(){
        numerator=numerator/findGcf(numerator,denominator);
        denominator=denominator/findGcf(numerator,denominator);
    }

    public void setNumerator(int num){
        numerator=num;
    }

    public void setDenominator(int denom){
        denominator=denom;
        fixDenominator();
    }

    public static Fraction multiply(Fraction n,Fraction d){
        int nProductComplex=n.getNumerator()*d.getNumerator();
        int dProductComplex=n.getDenominator()*d.getDenominator();
        int x =findGcf(nProductComplex, dProductComplex);
        int nProduct=nProductComplex/x;
        int dProduct=dProductComplex/x;
        Fraction product=new Fraction(nProduct, dProduct);
        return product;
    }

    public static Fraction divide(Fraction n,Fraction d){
        int nQuotientComplex=n.getNumerator()*d.getDenominator();
        int dQuotientComplex=n.getDenominator()*d.getNumerator();
        int x =findGcf(nQuotientComplex,dQuotientComplex); 
        int nProduct=nQuotientComplex/x;
        int dProduct=dQuotientComplex/x;
        Fraction quotient= new Fraction(nProduct, dProduct);
        return quotient;
    }

    public static Fraction add(Fraction n,Fraction d){
        int nSumComplex=n.getNumerator()*d.getDenominator()+d.getNumerator()*n.getDenominator();
        int dSumComplex=n.getDenominator()*d.getDenominator();
        int x=findGcf(nSumComplex,dSumComplex); 
        int nProduct=nSumComplex/x;
        int dProduct=dSumComplex/x;
        Fraction sum= new Fraction(nProduct, dProduct);
        return sum;
    }

    public static Fraction subtract(Fraction n,Fraction d){
        int nDifferenceComplex=n.getNumerator()*d.getDenominator()-d.getNumerator()*n.getDenominator();
        int dDifferenceComplex=n.getDenominator()*d.getDenominator();
        int x=findGcf(nDifferenceComplex,dDifferenceComplex); 
        int nProduct=nDifferenceComplex/x;
        int dProduct=dDifferenceComplex/x;
        Fraction difference=new Fraction(nProduct, dProduct);
        return difference;
    }

    public boolean equals(Fraction f){
        return(this.getNumerator()==f.getNumerator()&&this.getDenominator()==f.getDenominator());
    }

    private static int correctCount=0;
    private static int wrongCount=0;
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Let the Fraction Quiz Begin. Answers should be in lowest terms. Good luck!");
        while (true) {
            Fraction fraction1 = new Fraction();
            Fraction fraction2 = new Fraction();
            int operatorCode= (int)(Math.random()*4);
            String[] myOperator = {"+", "-", "*", "/"};
            String operator = myOperator[operatorCode];
            System.out.println("What is "+fraction1+operator+fraction2+"?");

            // Get user's answer
            String userAnswer = scanner.nextLine();
            // Check quit or not
            if(userAnswer.equals("quit")) {
                showMark();
            }

            Fraction userFraction = new Fraction(userAnswer);
            //evaluate
            Fraction result = null;
            if(operatorCode==0)result = add(fraction1, fraction2);

            if(operatorCode==1) result = subtract (fraction1, fraction2);

            if(operatorCode==2) result = multiply (fraction1, fraction2);

            if(operatorCode==3) result = divide (fraction1, fraction2);

            //compare the result and user's answer 
            if(result!=null&&result.equals(userFraction)) {
                System.out.println("Correct");
                correctCount++;
            }else{
                System.out.println("Wrong.The correct answer is: "+result);
                wrongCount++;
            }

        }
    }

    private static void showMark() {
        Fraction ratio=new Fraction(correctCount,correctCount+wrongCount);
        System.out.println("Win/Loss Ratio: "+ratio);

        // Calculate and display win percentage
        int percentage=(int)((float)correctCount/(correctCount+wrongCount)*100);
        System.out.println("Win Percentage: "+percentage+"%");
    }
}
