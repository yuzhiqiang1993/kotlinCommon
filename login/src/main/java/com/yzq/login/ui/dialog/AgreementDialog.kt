package com.yzq.login.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import com.yumc.android.userauth.login.view_model.AgreementViewModel
import com.yzq.dialog.core.BaseDialogFragment
import com.yzq.dialog.core.DialogConfig
import com.yzq.login.R
import com.yzq.login.databinding.DialogAgreementBinding


/**
 *
 * @description: 用户协议弹窗
 * @author : yuzhiqiang
 *
 */

internal class AgreementDialog(
    activity: FragmentActivity,
    @StringRes private val contentRes: Int,
) : BaseDialogFragment<AgreementDialog>(activity) {

    private val agreementViewModel: AgreementViewModel by viewModels()

    private var binding: DialogAgreementBinding? = null

    private var btnClick: ((Boolean) -> Unit)? = null


    override fun initView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DialogAgreementBinding.inflate(inflater, container, false)


        binding?.run {

            val agreementArray = resources.getStringArray(R.array.agreement_links)

            agreementCheckboxView.updateAgreementText(contentRes, agreementArray)
            agreementCheckboxView.onAgreementClick {
                agreementViewModel.agreementClick(it)
            }

        }

        return binding!!.root
    }


    override fun dialogConfig(config: DialogConfig) {
        config.cancelable(false)
        config.bgRes(R.drawable.agreement_dialog_bg)

        val displayMetrics = resources.displayMetrics
        val screenWidth = displayMetrics.widthPixels
        config.dialogWidth = (screenWidth * 0.8).toInt()

//        binding?.root?.layoutParams = LayoutParams(config.dialogWidth, LayoutParams.WRAP_CONTENT)


    }


    override fun initWidget(contentView: View) {

        binding?.btnAgree?.setOnClickListener {
            safeDismiss()
            btnClick?.invoke(true)

        }
        binding?.btnDisagree?.setOnClickListener {
            safeDismiss()
            btnClick?.invoke(false)
        }
    }


    fun onBtnClick(btnClick: ((Boolean) -> Unit)) {
        this.btnClick = btnClick
    }

    interface OnBtnClickListener {
        fun click(isAgree: Boolean)
    }
}