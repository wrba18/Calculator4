import java.util.Scanner;

public class Calculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите выражение (например: \"Hello\" + \"World!\") или 'exit' для выхода:");

        while (true) {
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            try {
                String result = calculate(input);
                System.out.println("\"" + result + "\"");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static String calculate(String expression) throws Exception {
        String[] parts = expression.split(" (?=[-+*/])|(?<=[-+*/]) "); // Разделение по операторам
        if (parts.length != 3) {
            throw new Exception("Некорректное количество аргументов. Используйте формат: \"строка1\" оператор \"строка2\" или \"строка\" оператор число.");
        }

        String left = parts[0].trim();
        String operator = parts[1].trim();
        String right = parts[2].trim();

        validateString(left);
        validateOperator(operator);

        left = left.substring(1, left.length() - 1);

        switch (operator) {
            case "+":
                return handleAddition(left, right);
            case "-":
                return handleSubtraction(left, right);
            case "*":
                return handleMultiplication(left, right);
            case "/":
                return handleDivision(left, right);
            default:
                throw new Exception("Недопустимая операция.");
        }
    }

    private static void validateString(String str) throws Exception {
        if (!str.startsWith("\"") || !str.endsWith("\"") || str.length() > 12) {
            throw new Exception("Неккоректная строка");
        }
    }

    private static void validateOperator(String operator) throws Exception {
        if (!operator.matches("[+\\-*\\/]|\\*|\\/")) {
            throw new Exception("Некорректный оператор");
        }
    }

    private static String handleAddition(String left, String right) throws Exception {
        validateString(right);
        right = right.substring(1, right.length() - 1);
        String result = left + right;
        return truncateIfNeeded(result);
    }

    private static String handleSubtraction(String left, String right) throws Exception {
        validateString(right);
        right = right.substring(1, right.length() - 1);
        String result = left.replaceFirst(right, "");
        return truncateIfNeeded(result);
    }

    private static String handleMultiplication(String left, String right) throws Exception {
        if (!right.matches("\\d+")) {
            throw new Exception("Второй аргумент должен быть числом от 1 до 10.");
        }
        int multiplier = Integer.parseInt(right);
        if (multiplier < 1 || multiplier > 10) {
            throw new Exception("Число должно быть от 1 до 10 включительно.");
        }
        String result = left.repeat(multiplier);
        return truncateIfNeeded(result);
    }

    private static String handleDivision(String left, String right) throws Exception {
        if (!right.matches("\\d+")) {
            throw new Exception("Второй аргумент должен быть числом от 1 до 10.");
        }
        int divisor = Integer.parseInt(right);
        if (divisor < 1 || divisor > 10) {
            throw new Exception("Число должно быть от 1 до 10 включительно.");
        }
        return left.substring(0, Math.max(left.length() / divisor, 0));
    }

    private static String truncateIfNeeded(String result) {
        return result.length() > 40 ? result.substring(0, 40) + "..." : result;
    }
}
