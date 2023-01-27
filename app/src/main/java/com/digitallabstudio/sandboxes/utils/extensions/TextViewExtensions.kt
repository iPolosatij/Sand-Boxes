package com.digitallabstudio.sandboxes.utils.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

inline fun EditText.onTextChange(crossinline listener: (String) -> Unit) {
    this.addTextChangedListener(object: TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // НЕТ OP
        }

        override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
            listener(charSequence. toString ())
        }

        override fun afterTextChanged(p0: Editable?) {
            // NO OP
        }

    })
}