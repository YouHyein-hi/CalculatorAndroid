package com.example.calaulatorandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    //var textViewNum = findViewById<TextView>(R.id.textView_Num)
    //val textViewresult = findViewById<TextView>(R.id.textView_result)
    
    private val textViewNum: TextView by lazy {  // textView_Num : 숫자 입력 부분, 결과 출력 부분
        findViewById<TextView>(R.id.textView_Num)
    }
    private val textViewResult: TextView by lazy {  // textView_result : 숫자 입력하면서 결과 출력 부분
        findViewById<TextView>(R.id.textView_result)
    }

    private var isMath = false  // 연산자를 입력하고 있는지
    private var haveMath = false  // 계산식에 연산자가 있는지, 없는지

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // 버튼이 여러개라 when문을 이용해서 버튼마다 함수를 정해줌
    fun buttonClicked(v: View) {
        println("buttonClicked 함수 시작")
        when (v.id) {
            R.id.button_0 -> numberButtonClicked("0")
            R.id.button_1 -> numberButtonClicked("1")
            R.id.button_2 -> numberButtonClicked("2")
            R.id.button_3 -> numberButtonClicked("3")
            R.id.button_4 -> numberButtonClicked("4")
            R.id.button_5 -> numberButtonClicked("5")
            R.id.button_6 -> numberButtonClicked("6")
            R.id.button_7 -> numberButtonClicked("7")
            R.id.button_8 -> numberButtonClicked("8")
            R.id.button_9 -> numberButtonClicked("9")

            R.id.button_pul -> mathButtonClicked("+")
            R.id.button_min -> mathButtonClicked("-")
            R.id.button_mul -> mathButtonClicked("X")
            R.id.button_div -> mathButtonClicked("/")
            R.id.button_mod -> mathButtonClicked("%")
        }
    }
    // when문 : kotiln의 switch-case문


    // imageView_erase 클릭 시 textView_Num에서 제일 마지막으로 들어간 숫자 or 연산자 지우는 함수
    private fun eraseButtonClicked(v: View){
        println("eraseButtonClicked 함수 시작")
    }


    // 각 숫자 버튼 함수
    private fun numberButtonClicked(number: String) {
        println("numberButtonClicked 함수 시작 (버튼 1234567890)")

        // 앞에 연산자가 있을 경우 뒤에 공백 추가해주기
        if (isMath) textViewNum.append(" ")
        isMath = false

        val numText = textViewNum.text.split(" ") // 공백 기준으로 자르고 추가
        if (numText.last().isEmpty() && number == "0") {  // 0이 제일 앞일 경우 입력X, 토스트 출력
            Toast.makeText(this, "0은 제일 앞에 올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        textViewNum.append(number)  // textViewNum에 숫자 추가
        textViewResult.text = calculateExpression()
    }


    // 각 연산자 버튼 함수
    private fun mathButtonClicked(math: String) {
        println("mathButtonClicked 함수 시작 (버튼 + - X ÷ /)")

        if(textViewNum.text.isEmpty()) return  // textViewNum 안이 공백일 경우 다시 함수 돌아가기

        // 연산자 뒤에 연산자 또 사용할 경우 출력
        if(isMath) Toast.makeText(this, "연산자 뒤에 연산자는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
        // 아닐 경우 그냥 추가
        else textViewNum.append(" $math")

        isMath = true
        haveMath = true
    }


    // = 버튼 함수
    fun resultButtonClicked(v: View) {
        println("resultButtonClicked 함수 시작 (버튼 =)")

        // 3가지 경우 대비하기!
        //
        val temporaryTexts = textViewNum.text.split(" ")
        // 1.계산식을 연산자로 마무리 할 경우  OR  2.계산식 입력 X, 숫자만 입력
        if ((temporaryTexts.size != 3 && haveMath) || (textViewNum.text.isEmpty() || temporaryTexts.size == 1)) {
            Toast.makeText(this, "계산식을 완성해주세요", Toast.LENGTH_SHORT).show()
            return
        }
        // 3. 입력 받은게 숫자가 아닐 경우
        if (temporaryTexts[0].isNumber().not() || temporaryTexts[2].isNumber().not()) {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        
        val temporaryText = textViewNum.text.toString()
        val resultText = calculateExpression()

        // textViewResult는 비워주고, textViewNum에 옮겨줌
        textViewResult.text = ""
        textViewNum.text = resultText

        isMath = false
        haveMath = false

    }

    // 이 함수가 입력한 숫자 계산해주는 곳
    // 입력할 때마다 숫자 여기에 입력됨
    // 첫번째 숫자 입력시 ex1, 두번째 숫자 exp2, 연산자 op
    private fun calculateExpression(): String {
        println("calculateExpression 함수 시작")

        val temporaryTexts = textViewNum.text.split(" ")

        if (haveMath.not() || temporaryTexts.size != 3) return ""
        else if (temporaryTexts[0].isNumber().not() || temporaryTexts[2].isNumber().not()) return ""
        
        val exp1 = temporaryTexts[0].toDouble()  // 첫번째 입력 숫자
        val exp2 = temporaryTexts[2].toDouble()  // 두번째 입력 숫자
        val math = temporaryTexts[1]             // 연산자

        return when (math) {
            "+" -> (exp1 + exp2).toInt().toString()
            "-" -> (exp1 - exp2).toInt().toString()
            "X" -> (exp1 * exp2).toInt().toString()
            "%" -> (exp1 % exp2).toString()
            "/" -> (exp1 / exp2).toInt().toString()
            else -> ""
        }
    }

    // C 버튼 함수 : 계산기 초기화
    fun clearButtonClicked(v: View) {
        println("clearButtonClicked 함수 시작 (버튼 C)")

        textViewNum.text = ""
        textViewResult.text = ""
        isMath = false
        haveMath = false
    }
}

fun String.isNumber(): Boolean {
    return if (this.isNullOrEmpty()) false else this.all { Character.isDigit(it) }
}
// Kotiln에는 isNumber 함수가 없길래 만들어줌
// 이 코드는 검색 후 나오는 코드 복붙함, 이 방법 말고도 여러 방법들이 있음




/*
- 연산자 여러개 구현 못함. (1+3 가능, 1+3+2 불가능) (....핳)
- 괄호 버튼 부분 구현 못함
- 소수점 버튼 부분 구현 못함 (하지만 할 수 있을거같음)

- 지우개 이미지 뷰 클릭시 textViewNum 숫자 지우기 구현 아직 못함 (해당 이미지 뷰 클릭하면 앱 꺼짐)
-
 */



