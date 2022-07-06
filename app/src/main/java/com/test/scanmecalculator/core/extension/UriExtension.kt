package com.test.scanmecalculator.core.extension

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.FileNotFoundException

/**
 * Uri extenstion to get bitmap from URI
 *
 * @param ContentResolver contentResolver
 * @return Bitmap?
 */
fun Uri.toBitmap(contentResolver: ContentResolver): Bitmap? {
    return uriToBitmap(this, contentResolver)
}

/**
 * Decode the uri data and convert it to bitmap
 * @see https://developer.android.com/topic/performance/graphics/load-bitmap
 *
 * @param Uri uri
 * @param ContentResolver contentResolver
 * @return Bitmap?
 */
private fun uriToBitmap(uri: Uri, contentResolver: ContentResolver): Bitmap? {
    // convert the uri to bitmap
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    try {
        BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options)
        options.inSampleSize = calculateInSampleSize(options, 300, 300)
        options.inJustDecodeBounds = false
        val bitmapImg =
            BitmapFactory.decodeStream(contentResolver.openInputStream(uri), null, options)
       return bitmapImg;
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
    }

    return null
}

/**
 * Calculate the size given the desired width and height
 * @see https://developer.android.com/topic/performance/graphics/load-bitmap
 *
 * @param BitmapFactory.Options options
 * @param Int reqWidth
 * @param Int reqHeight
 * @return Int
 */
private fun calculateInSampleSize(
    options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int
): Int {
    // Raw height and width of image
    val height = options.outHeight
    val width = options.outWidth
    var inSampleSize = 1
    if (height > reqHeight || width > reqWidth) {
        val halfHeight = height / 2
        val halfWidth = width / 2

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while (halfHeight / inSampleSize > reqHeight
            && halfWidth / inSampleSize > reqWidth
        ) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}