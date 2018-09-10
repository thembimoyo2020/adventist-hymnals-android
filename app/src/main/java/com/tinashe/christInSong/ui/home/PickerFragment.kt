package com.tinashe.christInSong.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.button.MaterialButton
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tinashe.christInSong.R
import com.tinashe.christInSong.ui.base.RoundedBottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_picker.*

class PickerFragment : RoundedBottomSheetDialogFragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_picker, container, false)
    }

    override fun onViewCreated(contentView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(contentView, savedInstanceState)

        btnClick0.setOnClickListener(this)
        btnClick1.setOnClickListener(this)
        btnClick2.setOnClickListener(this)
        btnClick3.setOnClickListener(this)
        btnClick4.setOnClickListener(this)
        btnClick5.setOnClickListener(this)
        btnClick6.setOnClickListener(this)
        btnClick7.setOnClickListener(this)
        btnClick8.setOnClickListener(this)
        btnClick9.setOnClickListener(this)
        btnClickGo.setOnClickListener {
            dismiss()
        }
        btnBackSpace.setOnClickListener {
            val currText = label.text
            if (!currText.isNullOrEmpty()) {
                label.text = currText.dropLast(1)
            }

            if (TextUtils.isEmpty(label.text)) {
                btnClickGo.visibility = View.INVISIBLE
            }
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        if (view is MaterialButton) {
            label.text = "${label.text}${view.text}".toInt().toString()

            if (label.text == "0") return

            btnClickGo.visibility = if (TextUtils.isEmpty(label.text)) View.INVISIBLE else View.VISIBLE
        }
    }
}