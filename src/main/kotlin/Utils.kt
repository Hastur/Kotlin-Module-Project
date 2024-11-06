import java.util.Scanner

class Utils {
    private val scanner = Scanner(System.`in`)

    fun inputWithCheck(): String {
        var userInput: String
        while (true) {
            userInput = scanner.nextLine().trim()
            if (userInput.isEmpty()) println("Пустое значение, повторите ввод") else break
        }
        return userInput
    }
}