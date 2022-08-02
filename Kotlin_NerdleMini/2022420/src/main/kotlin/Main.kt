import java.util.Scanner

fun blackBG(str: String): String {
    val ANSI_BLACK_BACKGROUND = "\u001B[40m"
    val ANSI_RESET = "\u001B[0m"
    return ANSI_BLACK_BACKGROUND + str + ANSI_RESET
}

fun greenBG(str: String): String {
    val ANSI_GREEN_BACKGROUND = "\u001B[42m"
    val ANSI_RESET = "\u001B[0m"
    return ANSI_GREEN_BACKGROUND + str + ANSI_RESET
}

fun yellowBG(str: String): String {
    val ANSI_YELLOW_BACKGROUND = "\u001B[43m"
    val ANSI_RESET = "\u001B[0m"
    return ANSI_YELLOW_BACKGROUND + str + ANSI_RESET
}
fun generateOp() :String {
    val operator = "+-*/".random().toString()
    return operator
}

fun generateKey(operator:String): String {
    var key : String = ""
    while (key.length != 6){
        if (operator == "+"){
            var num01 : Int = (0..99).random()
            var num02 : Int = (0..99).random()
            var num03 : String = (num01 + num02).toString()
            key = num01.toString() + operator + num02.toString() + "=" + num03

        }
        else if (operator == "-"){
            var num01 : Int = (0..99).random()
            var num02 : Int = (0..num01).random()
            var num03 : String = (num01 - num02).toString()
            key = num01.toString() + operator + num02.toString() + "=" + num03

        }
        else if (operator == "*") {
            var num01 : Int = (0..99).random()
            var num02 : Int = (0..99).random()
            var num03 : String = (num01 * num02).toString()
            key = num01.toString() + operator + num02.toString() + "=" + num03

        }
        else{
            var num01 : Int = (0..99).random()

            var div = mutableListOf<Int>()
            for (i in 1..num01) {
                if (num01 % i == 0) {
                    div.add(i)
                }
            }
            var num02 : Int = div.random()
            var num03 : Int = num01 / num02
            key = num01.toString() + operator + num02.toString() + "=" + num03
            }
        }
    return key
    }

fun extractCal(sample:String):String {
    val p = sample.split("").toMutableList()
    var calculate = mutableListOf<String>()
    p.removeAt(p.count() - 1)
    p.removeAt(0)

    for (i in 0..5) {
        if (p[i] == "+" || p[i] == "-" || p[i] == "*" || p[i] == "/") {
            calculate.add(p[i])
        }
    }
    calculate.removeIf { it !in listOf("+","-","*","/")}
    return calculate.joinToString("")
}


fun extractEqual(sample:String):String {
    val p = sample.split("").toMutableList()
    var Equal = mutableListOf<String>()
    p.removeAt(p.count() - 1)
    p.removeAt(0)

    for (i in 0..5) {
        if (p[i] == "=") {
            Equal.add(p[i])
        }
    }
    return Equal.joinToString("")
}

fun extractNum01(sample:String):String {
    val p = sample.split("").toMutableList()
    var frontnum = mutableListOf<String>()
    p.removeAt(p.count() - 1)
    p.removeAt(0)

    for (i in 0..5) {
        frontnum.add(p[i])
        if (p[i] == "+" || p[i] == "-" || p[i] == "*" || p[i] == "/") {
            frontnum.removeIf { it !in listOf("0","1","2","3","4","5","6","7","8","9")}
            return frontnum.joinToString("")
        }
        }
    return "1"
    }
fun extractNum02(sample:String):String {
    val p = sample.split("").toMutableList()
    var backnum = mutableListOf<String>()
    p.removeAt(p.count() - 1)
    p.removeAt(0)

    for (i in 0..5) {
        if (p[i] == "+" || p[i] == "-" || p[i] == "*" || p[i] == "/") {
            for (k in i..p.indexOf("=")){
                backnum.add(p[k])
            }
        }
    }

    backnum.removeIf { it !in listOf("0","1","2","3","4","5","6","7","8","9")}
    return backnum.joinToString("")
}

fun extractNum03(sample:String):String {
    val p = sample.split("").toMutableList()
    var Num03 = mutableListOf<String>()
    p.removeAt(p.count() - 1)
    p.removeAt(0)

    for (i in 0..5) {
        if (p[i] == "=" ) {
            for (k in i..p.count()-1){
                Num03.add(p[k])
            }
        }
    }
    Num03.removeIf { it !in listOf("0","1","2","3","4","5","6","7","8","9")}
    return Num03.joinToString("")
}

fun checkValid(sample:String):String {
    if (sample.indexOf("=") >= 3 &&sample.length == 6 && extractCal(sample).length == 1 && extractEqual(sample).length == 1 && extractNum01(sample).length + extractNum02(sample).length + extractNum03(sample).length == 4 && extractNum01(sample).isNotEmpty() && extractNum02(
            sample
        ).isNotEmpty() && extractNum03(sample).isNotEmpty()
    ) {
        if (extractCal(sample) == "+" && sample.indexOf("=") > sample.indexOf("+")) {
            var correct: Int = extractNum01(sample).toInt() + extractNum02(sample).toInt()
            if (extractNum01(sample).length == 2 && extractNum01(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (extractNum02(sample).length == 2 && extractNum02(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (extractNum03(sample).length == 2 && extractNum03(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (correct == extractNum03(sample).toInt()) {
                return "valid"
            }
            else return "等式不成立"
        }
        if (extractCal(sample) == "-"&& sample.indexOf("=") > sample.indexOf("-")) {
            var correct: Int = extractNum01(sample).toInt() - extractNum02(sample).toInt()
            if (extractNum01(sample).length == 2 && extractNum01(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (extractNum02(sample).length == 2 && extractNum02(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (extractNum03(sample).length == 2 && extractNum03(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (correct == extractNum03(sample).toInt() && extractNum03(sample).toInt() > 0) {
                return "valid"
            }
            else return "等式不成立"
        }
        if (extractCal(sample) == "*"&& sample.indexOf("=") > sample.indexOf("*")) {
            var correct: Int = extractNum01(sample).toInt() * extractNum02(sample).toInt()
            if (extractNum01(sample).length == 2 && extractNum01(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (extractNum02(sample).length == 2 && extractNum02(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (extractNum03(sample).length == 2 && extractNum03(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (correct == extractNum03(sample).toInt()) {
                return "valid"
            }
            else return "等式不成立"
        }
        if (extractCal(sample) == "/" && extractNum02(sample).toInt() !=0 && sample.indexOf("=") > sample.indexOf("/")) {
            var correct: Int = extractNum01(sample).toInt() / extractNum02(sample).toInt()
            if (extractNum01(sample).length == 2 && extractNum01(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (extractNum02(sample).length == 2 && extractNum02(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (extractNum03(sample).length == 2 && extractNum03(sample).indexOf("0") ==0){
                return "不允許leading-zero"
            }
            else if (sample.indexOf("=") < sample.indexOf("/")){
                return "格式不正確"
            }
            else if (correct == extractNum03(sample).toInt()) {
                return "valid"
            }
            else return "等式不成立"
        } else return "不符合格式"
    }
    return "不符合格式"
}

fun main() {
    val Key: String = generateKey(generateOp())
    var attempt: Int = 0
    println(Key)
    println("歡迎來到本遊戲，請輸入你猜想的答案，答案為六位數，不存在負數、交換率，僅會有一個四則運算符號，若要結束遊戲請輸入「Q」，您有6次嘗試機會。")
    while (attempt != 6) {
        val guess = readLine()!!
        if (checkValid(guess) == "valid") {
            attempt += 1
            if (guess == Key) {
                println("恭喜答對!")
                break
            }
            for (r in 0..5) {
                if (Key[r] == guess[r]) {
                    print(greenBG("${guess[r]}"))
                } else if (guess[r] in Key) {
                    print(yellowBG("${guess[r]}"))
                } else {
                    print(blackBG(guess[r].toString()))
                }
            }
            println("已用機會:$attempt/6")
        }
        else if (guess == "q" || guess == "Q"){
            println("退出遊戲")
            break
        }
        else println(checkValid(guess))
    }
    println("遊戲結束")
}


