package com.test.scanmecalculator.feature.scan

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.test.scanmecalculator.R
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ScanText @Inject constructor() {
    private var recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    private var inputImage: InputImage? = null
    private val regExPattern = "^[0-9]+?\\.?[0-9]+([\\+\\-\\*\\/])[0-9]+?\\.?[0-9]?"
    private var context: Context? = null

    fun setInputImageFromBitmap(bitmap: Bitmap) {
        inputImage =  InputImage.fromBitmap(bitmap, 0)
    }

    fun setInputImgeFromUri(uri: Uri) {
        inputImage = context?.let { InputImage.fromFilePath(it, uri) }
    }

    fun setContext(context: Context) {
        this.context = context
    }

    suspend fun scannedImage() = suspendCoroutine<String> { continuation ->
        inputImage?.let {
            recognizer?.process(it)
                ?.addOnSuccessListener { visionText ->
                    val regex = Regex(regExPattern)
                    Log.d("visiontext", ": " + visionText.text)
                    var matchResult = regex.find(visionText.text)
                    val getMatchResult = matchResult?.value

                    if (getMatchResult != null) {
                        continuation.resume(getMatchResult)
                    } else {
                        context?.let {
                            continuation.resume(it.resources.getString(R.string.error_failed_to_get_expression))
                        }
                    }
                }
                ?.addOnFailureListener { e ->
                    context?.let {
                        continuation.resume(it.resources.getString(R.string.error_failed_to_get_expression))
                    }
                }
        }
    }
}