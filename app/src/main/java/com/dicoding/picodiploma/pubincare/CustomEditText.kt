package com.dicoding.picodiploma.pubincare

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat

class CustomEditText : AppCompatEditText {
    private lateinit var customBackground: Drawable

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        background = customBackground
    }

    private fun init() {
        customBackground = ContextCompat.getDrawable(context, R.drawable.bg_edit_text) as Drawable

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (
                    (inputType == EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_PASSWORD) and
                    (s.toString().length < 6)
                ) {
                    error = "Password harus lebih dari 6 karakter"
                }
                else if (
                    (inputType == EditorInfo.TYPE_CLASS_TEXT or EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS) and
                    (s.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(s).matches())
                ) {
                    error = "Email tidak valid"
                }
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }
}