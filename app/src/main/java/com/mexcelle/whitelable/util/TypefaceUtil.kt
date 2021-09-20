package com.mexcelle.whitelable.util

import android.content.Context
import android.graphics.Typeface

object TypefaceUtil {
    /**
     * Using reflection to override default typeface
     * NOTICE: DO NOT FORGET TO SET TYPEFACE FOR APP THEME AS DEFAULT TYPEFACE WHICH WILL BE OVERRIDDEN
     * @param context to work with assets
     * @param defaultFontNameToOverride for example "monospace"
     * @param customFontFileNameInAssets file name of the font from assets
     */
    fun overrideFont(
        context: Context,
        defaultFontNameToOverride: String?,
        customFontFileNameInAssets: String?
    ) {
        ////Log.e("TypefaceUtil","overrideFont");
        try {

            ////Log.e("AppController","FOnt override");
            val customFontTypeface =
                Typeface.createFromAsset(context.assets, customFontFileNameInAssets)
            val defaultFontTypefaceField =
                Typeface::class.java.getDeclaredField(defaultFontNameToOverride)
               defaultFontTypefaceField.isAccessible = true
                defaultFontTypefaceField[null] = customFontTypeface

            ////Log.e("AppController","FOnt override customFontTypeface "+customFontTypeface);
            ////Log.e("AppController","FOnt override defaultFontTypefaceField"+defaultFontTypefaceField);
        } catch (e: Exception) {

            ////Log.e("AppController","FOnt override");
            ////Log.e("Cant custom font ", customFontFileNameInAssets + " instead of " + defaultFontNameToOverride);
        }
    }
}
