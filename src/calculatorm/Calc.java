package calculatorm;

import java.util.Arrays;
import java.util.List;

public class Calc {
    private int num1, num2;     //числа в выражении
    private String operator;    //оператор в выражении, допускается: +-*/

    //---выполнение арифметического выражения (только целые числа в ответе)
    private int calcExp(int n1, String op, int n2){
        int res;
        switch (op) {
            case "+":
                res = n1+n2;
                break;
            case "-":
                res = n1-n2;
                break;
            case "*":
                res = n1*n2;
                break;
            case "/":
                res = n1/n2;
                break;
            default:
                throw new AssertionError();
        }
        return res;
    }
    
    //---public метод с проверками и с выводом результата
    public String result(String exp) throws CalcException{
        boolean isRomanExp;     // ---Признак, что числа римские
        Parse parse = new Parse();
        
        //---разбиваем исходное выражение String по разделителю " "
        List<String> expItems = Arrays.asList(exp.split(" "));
        
        //---проверка, что создалось 3 элемента: число1, оператор, число2, иначе исключение
        if (expItems.size()!=3){
            throw new CalcException("ОШИБКА. Выражение должно иметь вид: \"Число1 Операция Число2\", разделенные пробелом...");
        }
        
        //--- проверка оператора, должен быть: + - * /
        if (parse.checkOperator(expItems.get(1))){
            operator = expItems.get(1);
        } else {
            throw new CalcException("ОШИБКА. Оператор '" + expItems.get(1) + "' не корректен, должен быть: + - * / ");
        }
        
        //---проверка чисел, должны быть оба арабские или оба римские
        if (parse.isNumeric(expItems.get(0)) && parse.isNumeric(expItems.get(2))){      //---проверяем, что оба числа арабские
            num1 = Integer.parseInt(expItems.get(0));
            num2 = Integer.parseInt(expItems.get(2));
            isRomanExp = false;
        } else if (parse.isRoman(expItems.get(0)) && parse.isRoman(expItems.get(2))){   //---проверяем, что оба числа римские
            num1 = parse.romeToArabConvert(expItems.get(0));
            num2 = parse.romeToArabConvert(expItems.get(2));
            isRomanExp = true;
        } else {    //--- числа не соответствуют
            throw new CalcException("ОШИБКА. Числа должны быть оба арабские или оба римские");
        }
        
        //---проверка чисел, должны быть от 1 до 10 включительно
        if (!(num1>=1 && num1<=10)){
            throw new CalcException("ОШИБКА. Число #1 должно быть от 1 до 10 или от I до X включительно");
        }

        if (!(num2>=1 && num2<=10)){
            throw new CalcException("ОШИБКА. Число #2 должно быть от 1 до 10 или от I до X включительно");
        }
        
        //--- получаем результат
        int res = calcExp(num1, operator, num2);
        
        //--- если числа римские, то конвертируем в римские и возвращаем результат
        if (isRomanExp){
            String sign = res < 0 ? "-" : "";
            return sign + parse.arabToRomeConvert(Math.abs(res));
        }
        
        //--- возвращаем ответ - арабское число
        return String.valueOf(res);
    }
    
}
