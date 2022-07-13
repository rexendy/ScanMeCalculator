package com.test.scanmecalculator.feature.scan

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * ScanCalculatorViewModel
 * Handles the request and give it back to Activity
 * where the data is observed
 */
@HiltViewModel
class ScanCalculatorViewModel @Inject constructor(
    private val scanText: ScanText
): ViewModel() {
    val result = MutableLiveData<String>()
    val expression = MutableLiveData<String>()
    val errorMessage = MutableLiveData<String>()

    private var bitmap: Bitmap? = null
    private var uri: Uri? = null
    private var  context: Context? = null
    private val operands = listOf("+","-","*","/")

    fun readTextFromImage() {
        viewModelScope.launch {
            resetResults()
            bitmap?.let {
                scanText.setInputImageFromBitmap(it)
            }

            uri?.let {
                scanText.setInputImgeFromUri(it)
            }

            val getTxt =  scanText.scannedImage()
            if (getTxt.isNotEmpty()) {
                if (getTxt.lowercase().contains("failed")) {
                    errorMessage.value = getTxt
                } else {
                    scanImage(getTxt)
                }
            }
        }
    }

    @JvmName("setBitmap1")
    fun setBitmap(value: Bitmap) {
        bitmap = value
    }

    @JvmName("setUri1")
    fun setUri(value: Uri) {
        uri = value
    }

    @JvmName("setContext1")
    fun setContext(value: Context) {
        context = value
        scanText.setContext(value)
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
     * @param String data
     */
    private fun scanImage(data: String) {
        if (data.isNotEmpty()) {
            operands.forEach {
                val splitNum = split(it, data)
                if (splitNum.size == 2) {
                    result.value = String.format("%.2f", doOperation(it, splitNum))
                    return@forEach
                }
            }
            expression.value = data
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
    private fun doOperation(operand: String, data: List<String>): Float {
        var result: Float = 0.2F
        when(operand) {
            "+" -> {
                result = sum(data.first().toFloat(), data.last().toFloat())
            }
            "-" -> {
                result = difference(data.first().toFloat(), data.last().toFloat())
            }
            "*" -> {
                result = product( data.first().toFloat(), data.last().toFloat())
            }
            "/" -> {
                result = quotient(data.first().toFloat(), data.last().toFloat())
            }
        }

        return  result
    }

    private fun sum(num1: Float, num2: Float): Float {
        return num1 + num2
    }

    private fun difference(num1: Float, num2: Float): Float {
        return num1 - num2
    }

    private fun product(num1: Float, num2: Float): Float {
        return num1 * num2
    }

    private fun quotient(num1: Float, num2: Float): Float {
        return num1 / num2
    }
}