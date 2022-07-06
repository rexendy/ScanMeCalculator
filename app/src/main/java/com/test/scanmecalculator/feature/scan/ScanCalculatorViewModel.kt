package com.test.scanmecalculator.feature.scan

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * ScanCalculatorViewModel
 * Handles the request and give it back to Activity
 * where the data is observed
 */
@HiltViewModel
class ScanCalculatorViewModel @Inject constructor(): ViewModel() {
    val result = MutableLiveData<String>()
    val expression = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()
    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    var bitmap: Bitmap? = null
    private val regExPattern = "^(\\d+)([\\+\\-\\*\\/]\\d+)"

    var scrope = CoroutineScope(Job() + Dispatchers.Main)

    fun readTextFromImage() {
        scrope.launch {
            resetResults()
            bitmap?.let { scanImage(it) }
        }
    }

    /**
     * Sets the bitmap
     * @param Bitmap value
     */
    @JvmName("setBitmap1")
    fun setBitmap(value: Bitmap) {
        bitmap = value
    }

    /**
     * Resets the value to empty
     */
    private fun resetResults() {
        result.value = ""
        expression.value = ""
    }

    /**
     * Scan the bitmap and gets the
     * expression and do the mathematical expression
     *
     * @param Bitmap bitmap
     */
    private fun scanImage(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val operands = listOf("+","-","*","/")
        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val regex = Regex(regExPattern)
                var matchResult = regex.find(visionText.text)
                val getMatchResult = matchResult?.value

                if (getMatchResult != null) {
                    if (getMatchResult.isNotEmpty()) {
                        operands.forEach {
                            val splitNum = split(it, getMatchResult)
                            if (splitNum.size == 2) {
                                result.value = doOperation(it, splitNum).toString()
                                return@forEach
                            }
                        }
                        expression.value = getMatchResult
                    }
                } else {
                    errorMessage.value = "Failed to get expression"
                }
            }
            .addOnFailureListener { e ->
                errorMessage.value = "Failed to get expression"
            }
    }

    /**
     * Split the expression to get the numbers
     *
     * @param String operand
     * @param String stringTxt
     * @return List<String>
     */
    private fun split(operand: String, stringTxt: String): List<String> {
        return stringTxt.split(operand)
    }

    /**
     * Do the desired operation given what operand
     *
     * @param String operand
     * @param List<String> data holds the numbers to calculate
     * @return Int
     */
    private fun doOperation(operand: String, data: List<String>): Int {
        var result = 0
        when(operand) {
            "+" -> {
                result = sum(data.first().toInt(), data.last().toInt())
            }
            "-" -> {
                result = difference(data.first().toInt(), data.last().toInt())
            }
            "*" -> {
                result = product( data.first().toInt(), data.last().toInt())
            }
            "/" -> {
                result = quotient(data.first().toInt(), data.last().toInt())
            }
        }

        return  result
    }

    /**
     * Add the numbers
     *
     * @param num1
     * @param num2
     * @return Int
     */
    private fun sum(num1: Int, num2: Int): Int {
        return num1 + num2
    }

    /**
     * Subtract the numbers
     *
     * @param num1
     * @param num2
     * @return Int
     */
    private fun difference(num1: Int, num2: Int): Int {
        return num1 - num2
    }

    /**
     * Multiply the numbers
     *
     * @param num1
     * @param num2
     * @return Int
     */
    private fun product(num1: Int, num2: Int): Int {
        return num1 * num2
    }

    /**
     * Divide the numbers
     *
     * @param num1
     * @param num2
     * @return Int
     */
    private fun quotient(num1: Int, num2: Int): Int {
        return num1 / num2
    }

    /**
     * When onCleared the scope is cancelled
     */
    override fun onCleared() {
        super.onCleared()
        scrope?.cancel()
    }
}