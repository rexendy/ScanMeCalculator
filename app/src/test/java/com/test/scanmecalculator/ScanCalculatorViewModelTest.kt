package com.test.scanmecalculator

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.test.scanmecalculator.feature.scan.ScanCalculatorViewModel
import com.test.scanmecalculator.feature.scan.ScanText
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class ScanCalculatorViewModelTest {
    private lateinit var scanText: ScanText
    private lateinit var scanCalculatorViewModel: ScanCalculatorViewModel
    private lateinit var context: Context
    private lateinit var bitmap: Bitmap
    private lateinit var uri: Uri

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        scanText = mockk(relaxed = true)
        scanCalculatorViewModel = ScanCalculatorViewModel(scanText)
        context = mockk(relaxed = true)
        bitmap = mockk(relaxed = true)
        uri = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun failedToGetExpressionBitmap() {
        scanText.setContext(context)
        scanCalculatorViewModel.setBitmap(bitmap)

        coEvery { scanText.scannedImage() } returns "Failed to get expression"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, "Failed to get expression")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun failedToGetExpressionUri() {
        scanText.setContext(context)
        scanCalculatorViewModel.setUri(uri)

        coEvery { scanText.scannedImage() } returns "Failed to get expression"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, "Failed to get expression")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun addGetExpressionBitmap() {
        scanText.setContext(context)
        scanCalculatorViewModel.setBitmap(bitmap)

        coEvery { scanText.scannedImage() } returns "5+1"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, null)
        Assert.assertEquals(scanCalculatorViewModel.result.value, "6.00")
        Assert.assertEquals(scanCalculatorViewModel.expression.value, "5+1")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun subtractGetExpressionBitmap() {
        scanText.setContext(context)
        scanCalculatorViewModel.setBitmap(bitmap)

        coEvery { scanText.scannedImage() } returns "6.5-1.2"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, null)
        Assert.assertEquals(scanCalculatorViewModel.result.value, "5.30")
        Assert.assertEquals(scanCalculatorViewModel.expression.value, "6.5-1.2")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun multiplyGetExpressionBitmap() {
        scanText.setContext(context)
        scanCalculatorViewModel.setBitmap(bitmap)

        coEvery { scanText.scannedImage() } returns "5*5"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, null)
        Assert.assertEquals(scanCalculatorViewModel.result.value, "25.00")
        Assert.assertEquals(scanCalculatorViewModel.expression.value, "5*5")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun divideGetExpressionBitmap() {
        scanText.setContext(context)
        scanCalculatorViewModel.setBitmap(bitmap)

        coEvery { scanText.scannedImage() } returns "9/4"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, null)
        Assert.assertEquals(scanCalculatorViewModel.result.value, "2.25")
        Assert.assertEquals(scanCalculatorViewModel.expression.value, "9/4")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun addGetExpressionUri() {
        scanText.setContext(context)
        scanCalculatorViewModel.setUri(uri)

        coEvery { scanText.scannedImage() } returns "5+1"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, null)
        Assert.assertEquals(scanCalculatorViewModel.result.value, "6.00")
        Assert.assertEquals(scanCalculatorViewModel.expression.value, "5+1")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun subtractGetExpressionUri() {
        scanText.setContext(context)
        scanCalculatorViewModel.setUri(uri)

        coEvery { scanText.scannedImage() } returns "6.5-1.2"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, null)
        Assert.assertEquals(scanCalculatorViewModel.result.value, "5.30")
        Assert.assertEquals(scanCalculatorViewModel.expression.value, "6.5-1.2")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun multiplyGetExpressionUri() {
        scanText.setContext(context)
        scanCalculatorViewModel.setUri(uri)

        coEvery { scanText.scannedImage() } returns "5*5"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, null)
        Assert.assertEquals(scanCalculatorViewModel.result.value, "25.00")
        Assert.assertEquals(scanCalculatorViewModel.expression.value, "5*5")
    }

    @ExperimentalCoroutinesApi
    @Test
    fun divideGetExpressionUri() {
        scanText.setContext(context)
        scanCalculatorViewModel.setUri(uri)

        coEvery { scanText.scannedImage() } returns "9/4"

        scanCalculatorViewModel.readTextFromImage()

        Assert.assertEquals(scanCalculatorViewModel.errorMessage.value, null)
        Assert.assertEquals(scanCalculatorViewModel.result.value, "2.25")
        Assert.assertEquals(scanCalculatorViewModel.expression.value, "9/4")
    }
}