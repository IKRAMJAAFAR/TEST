/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package PROJ;

import java.util.*;

/**
 *
 * @author weare
 */
public class BaseNumber {
    public static void main(String[] args) {
        
        Number num = new Number();
        Scanner s = new Scanner(System.in);
        
        
        System.out.println("Do you want the instructions?");
        System.out.print("Enter 'y' to display : ");
        if(s.nextLine().charAt(0) == 'y'){
            System.out.println("Action List");
            System.out.println("1 - Enter input number with specified base");
            System.out.println("2 - Convert to a selected base");
            System.out.println("3 - (r-1) Complement of input");
            System.out.println("4 - r Complement of input");
            System.out.println("5 - Sign magnitude of input(ONLY FOR BASE 2)");
            System.out.println("6 - Operation as the input number is the first number");
            System.out.println("Default - Exit program\n");
        }
        System.out.println("Program Started");
        
        
        int inputBase = 0,outputBase = 0;
        int inputSecBase = 0;
        String number = "";
        String numberSec = "";
        
        
        end:for(;;){
            System.out.print("Action: ");
            switch(s.nextLine().charAt(0)){
                
                case '6' ->{
                    if(inputBase == 0){
                        System.out.println("The input number is not given");
                        break;
                    }
                    System.out.print("Enter the second input base number [2-64]: ");
                    inputSecBase = s.nextInt();
                    s.nextLine();
                    if(!num.checkBase(inputSecBase)){
                        System.out.println("Invalid input");
                        inputBase = 0;
                        break;
                    }
                    else{
                        System.out.println("Enter the number");
                        System.out.println("Note: Must Only need any following character(s)");
                        System.out.println(
                                Arrays.toString(num.displayLimit(inputSecBase)));
                        System.out.print("Second Input Number: ");
                        numberSec = s.nextLine();
                        if(!num.checkNum(numberSec, inputSecBase)){
                            System.out.println("Invalid character(s)");
                            inputBase = 0;
                        }
                        
                        
                        System.out.print("Enter the output base number [2-64]: ");
                        outputBase = s.nextInt();
                        s.nextLine();
                        if(!num.checkBase(outputBase)){
                            System.out.println("Invalid input");
                            break;
                        }
                        System.out.print("Select operands(+,-,*,/,%): ");
                        char operands = s.nextLine().charAt(0);
                        System.out.printf("(%s) base %d %c (%s) base %d = %s\n"
                        ,number,inputBase,operands,numberSec,inputSecBase,
                        num.operation
                        (number,inputBase,numberSec,inputSecBase,operands,outputBase));
                        
                    }
                }
                
                
                case '5' ->{
                    if(inputBase == 0){
                        System.out.println("The input number is not given");
                        break;
                    }
                    System.out.println(num.signMag(number, inputBase));
                    
                }
                
                
                case '4' ->{
                    if(inputBase == 0){
                        System.out.println("The input number is not given");
                        break;
                    }
                    System.out.println(num.rComp(number, inputBase));
                    
                }
                
                        
                case '3' ->{
                    if(inputBase == 0){
                        System.out.println("The input number is not given");
                        break;
                    }
                    System.out.println(num.rM1Comp(number, inputBase));
                }
                
                
                default -> {break end;}
                
                
                case '1' ->{
                    System.out.print("Enter the input base number [2-64]: ");
                    inputBase = s.nextInt();
                    s.nextLine();
                    if(!num.checkBase(inputBase)){
                        System.out.println("Invalid input");
                        inputBase = 0;
                        break;
                    }
                    else{
                        System.out.println("Enter the number");
                        System.out.println("Note: Must Only need any following character(s)");
                        System.out.println(
                                Arrays.toString(num.displayLimit(inputBase)));
                        System.out.print("Input Number: ");
                        number = s.nextLine();
                        if(!num.checkNum(number, inputBase)){
                            System.out.println("Invalid character(s)");
                            inputBase = 0;
                        }
                    }
                        
                }
                
                
                case '2' ->{
                    if(inputBase == 0){
                        System.out.println("The input number is not given");
                        break;
                    }
                    System.out.print("Enter the output base number [2-64]: ");
                    outputBase = s.nextInt();
                    s.nextLine();
                    if(!num.checkBase(outputBase)){
                        System.out.println("Invalid input");
                        break;
                    }
                    else{
                        System.out.println(num.OriToOut(number,inputBase,outputBase));
                    }
                }
                
                
            }
        }
        System.out.println("Program Ended");
    }
}

class Number{
    private final char[] list;
    
    // binary special ada sign magnitude
    public String signMag(String num,int base){
        if(base == 2){
            char test = num.charAt(0) == '1' ? '0':'1';
            String sub = num.substring(1);
            return "Sign magnitude: " + "" + test + "" + sub;
        }
        else
            return "No sign magnitude for base " + base;
    }
    
    public String rM1Comp(String num,int base){
        return operation(radix(num.length(),base),base,num,base,'-',base);
    }
    
    public String rComp(String num,int base){
        return operation(rM1Comp(num,base),base,"1",base,'+',base);
    }
    
    public String radix(int length,int base){
        String result = "";
        for(;length > 0;length--)
            result += list[base - 1];
        return result;
    }
    
    // Operation +,-,*,/,% => (num1,base1,num2,base2,operation)
    public String operation(String num1,int base1,String num2,int base2,char operands, int baseOut){
        int base10_N1 = changeBase10(num1,base1);
        int base10_N2 = changeBase10(num2,base2);
        int base10;
        switch(operands){
            default ->{
                System.out.println("Invalid operands");
                return "";
            }
            case '+' -> {
                base10 = base10_N1 + base10_N2;
            }
            case '-' -> {
                base10 = Math.abs(base10_N1 - base10_N2);
            }
            case '*' -> {
                base10 = base10_N1 * base10_N2;
            }
            case '/','%' -> {
                if(base10_N2 == 0){
                    System.out.println("Cannot be computed");
                    return "";
                }
                else if(operands == '/')
                    base10 = base10_N1 / base10_N2;
                else
                    base10 = base10_N1 % base10_N2;
            }
        }
        if(base10_N1 < base10_N2)
            return rComp(OriToOut(String.valueOf(base10),10,baseOut),baseOut);
        else
            return OriToOut(String.valueOf(base10),10,baseOut);
    }
    
    public char[] displayLimit(int base){
        char[] result = new char[base];
        System.arraycopy(list, 0, result, 0, base);
        return result;
    }
    
    public boolean checkBase(int base){
        return base >= 2 && base <= 64;
    }
    
    public Number(){
        list = new char[64];
        int element = 0;
        for(int repeat = 1;repeat <= 5;repeat++){
            char Initial = repeat == 1 ? '0':(repeat == 2 ? 'A':
                    (repeat == 3 ? 'a':(element == 62 ? '-':'_')));
            char Final = repeat == 1 ? '9':(repeat == 2 ? 'Z':
                    (repeat == 3 ? 'z':'~'));
                if(Final == '~')
                    list[element++] = Initial;
                else{
                    while(Initial <= Final)
                        list[element++] = Initial++;
                }
                
            }
        
    }
    
    public boolean checkNum(String num,int ori){
        for(int el = 0;el < num.length();el++){
            char test = num.charAt(el);
            boolean status = false;
            for(int index = 0;index < ori;index++){
                if(test == list[index]){
                    status = true;
                    break;
                }
            }
            if(!status){
                System.out.print("Out of bounds character(s)");
                return false;
            }
        }
        return true;
    }
    
    public int changeBase10(String num, int base){
        int result = 0;
        int element = 0;
        for(int el = num.length() - 1;el >= 0;el--){
            char test = num.charAt(el);
            for(int elList = 0;elList < list.length;elList++){
                if(list[elList] == test){
                    result += (int) Math.pow(base, element++) * elList;
                    break;
                }
            }
        }
        return result;
    }
 
    public String OriToOut(String num,int ori,int out){
        String result = "";
        int Numbase10 = changeBase10(num,ori);
        if(checkNum(num,ori)){
            for(;;){
                if(Numbase10 == 0)
                    break;
                else{
                    result += list[Numbase10 % out];
                    Numbase10 /= out;
                }
            }
        }
        return reverse(result);
    }
    
    public static String reverse(String line){
        String result = "";
        for(int test = line.length() - 1;test >= 0;test--){
            result += line.charAt(test);
        }
        return result;
    }
}
