package calculatorapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.NavigableMap;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author Павел
 */
public class CalculatorApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner s = new Scanner(System.in);
        String str = s.nextLine();
        str = str.toUpperCase();
        str = str.replaceAll("\\s+","");
        Calculator c = new Calculator(str);
        System.out.print(c.getResult());
    }
    
}


class Calculator{
    private String str;
    private final ArrayList<Double> numbers;
    private final ArrayList<Character> operators;
//    private final NavigableMap<String, Integer> romanNumbers;
//   {
//        NavigableMap<String, Integer> initMap = new TreeMap<>();
//        initMap.put("M", 1000);
//        initMap.put("CM", 900);
//        initMap.put("D", 500);
//        initMap.put("CD", 400);
//        initMap.put("C", 100);
//        initMap.put("XC", 90);
//        initMap.put("L", 50);
//        initMap.put("XL", 40);
//        initMap.put("X", 10);
//        initMap.put("IX", 9);
//        initMap.put("V", 5);
//        initMap.put("IV", 4);
//        initMap.put("I", 1);
//        romanNumbers = Collections.unmodifiableNavigableMap(initMap);
//    }
    
    private int valueOfRoman(char c) {
        switch (c) {
            case 'M':
                return 1000;
            case 'D':
                return 500;
            case 'C':
                return 100;
            case 'L':
                return 50;
            case 'X':
                return 10;
            case 'V':
                return 5;
            case 'I':
                return 1; 
            default:
                break;
        }

    return -1;
    }
    
    public String getStr(){
        return this.str;
    }
    
    public void setStr(String str){
        this.str = str;
    }
    
    Calculator(String str){
        this.str = str;
        numbers = new ArrayList();
        operators = new ArrayList();
        if(isRoman(str.charAt(str.length()-1))){
            getNumbersFromRoman();
            getOperatorsFromRoman();
        }else {
            getNumbers();
            getOperators();
        }
        calculate();
    }
    
    private double romanToInt( String s ){
    int total = 0;
    int pivot = 0;

    for( int i = s.length()-1; i >= 0; i--){ 
        int current = valueOfRoman(s.charAt(i));
        if( current >= pivot ){
            pivot = current; 
            total += pivot;
        }else{
            total -= current;
        }   
    }
    return total;
}
    
    private boolean isRoman(char a){
        return a == 'I' || a == 'V' || a == 'X' || a == 'L' || a == 'C' || a == 'D' || a == 'M';
    }
    
    
    private void getNumbersFromRoman(){
        for(int i = 0; i < str.length(); i++){           
            if(isRoman(str.charAt(i))){
               for(int e = i; e < str.length(); e++){
                   if(e == str.length()-1){
                       if(isRoman(str.charAt(e))){
                       numbers.add(romanToInt(str.substring(i, e+1)));
                       i = e+1;
                       }
                    }else if(!(isRoman(str.charAt(e)) || str.charAt(e) == '.')){
                        numbers.add(romanToInt(str.substring(i, e)));
                        i = e+1;
                        if(str.charAt(i) == str.charAt(e)){
                            numbers.add(romanToInt(str.substring(e,i)));
                            i = e + 1;
                        }
                    }
               }
            }
        }
        if(str.charAt(0) == '-'){
           numbers.set(0, -numbers.get(0));
        }
        for(int i = 0; i < numbers.size(); i++){System.out.println(numbers.get(i));}
    }
    
    private void getOperatorsFromRoman(){
        for(int i = 1; i < str.length(); i++){
            if(!(isRoman(str.charAt(i)))){
                operators.add(str.charAt(i));
            }
        }
        for(int i = 0; i < operators.size(); i++){System.out.println(operators.get(i));}
    }
    
    private void getNumbers(){
        for(int i = 0; i < str.length(); i++){           
            if(Character.isDigit(str.charAt(i))){
               for(int e = i; e < str.length(); e++){
                   if(e == str.length()-1){
                       numbers.add(Double.valueOf(str.substring(i, str.length())));
                       i = e+1;
                   }else if(!(Character.isDigit(str.charAt(e)) || str.charAt(e) == '.')){
                       numbers.add(Double.valueOf(str.substring(i, e)));
                       i = e+1;
                   }
               }
            }
        }
        if(str.charAt(0) == '-'){
           numbers.set(0, -numbers.get(0));
        }
        for(int i = 0; i < numbers.size(); i++){System.out.println(numbers.get(i));}
    }  
    
    private void getOperators(){
        for(int i = 1; i < str.length(); i++){
            if(!(Character.isDigit(str.charAt(i))) && !(str.charAt(i) == '.')){
                operators.add(str.charAt(i));
            }
        }
        for(int i = 0; i < operators.size(); i++){System.out.println(operators.get(i));}
    }    
    
    private void calculate(){
        for(int i = 0; i < operators.size(); i++){           
            if(!operators.isEmpty()){
                Multiply(i);
            }
        }
        
        for(int i = 0; i < operators.size(); i++){
            if(!operators.isEmpty()){
                Divide(i);
            }
        }
        
        for(int i = 0; i < operators.size(); i++){ 
            if(!operators.isEmpty()){
            Plus(i);     
            }
        }
        for(int i = 0; i < operators.size(); i++){
            if(!operators.isEmpty()){
            Minus(i);
            }
        }
    } 
    
    private void Multiply(int i){
        if(operators.get(i) == '*'){
            numbers.set(i, numbers.get(i) * numbers.get(i+1));
            numbers.remove(i+1);
            operators.remove(i);
        }else if (operators.get(i) == '/'){
                Divide(i);
        }
    }
    
     private void Divide(int i){
        if(operators.get(i) == '/'){
            numbers.set(i, numbers.get(i) / numbers.get(i+1));
            numbers.remove(i+1);
            operators.remove(i);
        }else if(operators.get(i) == '*'){
         Multiply(i);
        }
    }
    
    private void Minus(int i){
        if(operators.get(i) == '-'){
            numbers.set(i, numbers.get(i) - numbers.get(i+1));
            numbers.remove(i+1);
            operators.remove(i);
        }else if(operators.get(i) == '+'){
            Plus(i);
        }
    }
    
    private void Plus(int i){
        if(operators.get(i) == '+'){
            numbers.set(i, numbers.get(i) + numbers.get(i+1));
            numbers.remove(i+1);
            operators.remove(i);
        }else if(operators.get(i) == '-'){
            Minus(i);
        }
    }
    
    public Double getResult(){
        return numbers.get(0);
    } 
    
}