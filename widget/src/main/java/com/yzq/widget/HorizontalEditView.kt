package com.yzq.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.core.view.doOnAttach
import com.yzq.widget.databinding.ViewHorizontalEditLayoutBinding

/**
 * @description: 水平ItemView，输入框
 * @author : yzq
 * @date : 2018/9/1
 * @time : 14:05
 *
 */

@SuppressLint("ResourceAsColor")
class HorizontalEditView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var titleStr: String?
    private var contentStr: String?


    private val binding: ViewHorizontalEditLayoutBinding =
        ViewHorizontalEditLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {

        doOnAttach {
            /**
             * 要在onAttachedToWindow 中才能获取到findViewTreeLifecycleOwner
             * ViewTreeLifecycleOwner 是Lifecycle KTX中提供的View的一个扩展方法，可以快速地获取一个最近的Fragment或者Activity的LifecycleOwner
             */
//            it.findViewTreeLifecycleOwner()?.lifecycle?.run {
//                addObserver(object :
//                    LifecycleEventObserver {
//                    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
//                        Logger.i("当前状态:$event")
//                    }
//                })
//
//                coroutineScope.launchSafety {
//                    Logger.i("测试开始执行耗时任务")
//                    withIO {
//                        delay(3000)
//                        Logger.i("执行完毕了")
//                    }
//                }
//            }
        }

        val typeArr = context.obtainStyledAttributes(attrs, R.styleable.HorizontalEditView)

        try {
            val iconRes = typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_icon, -1)
            val iconTint = typeArr.getColor(
                R.styleable.HorizontalEditView_horz_edit_icon_tint,
                com.yzq.resource.R.color.icon_primary
            )
            val inputType = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_inputType)
            val editEnable =
                typeArr.getBoolean(R.styleable.HorizontalEditView_horz_edit_editEnable, true)
            val endIconRes =
                typeArr.getResourceId(R.styleable.HorizontalEditView_horz_edit_endIcon, -1)

            val endIconTint = typeArr.getColor(
                R.styleable.HorizontalEditView_horz_edit_end_icon_tint,
                com.yzq.resource.R.color.icon_primary
            )
            titleStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_title)
            contentStr = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_content)
            val hint = typeArr.getString(R.styleable.HorizontalEditView_horz_edit_hint)


            binding.run {
                //默认隐藏图标
                iconStart.visibility = View.GONE

                iconEnd.visibility = View.GONE

                //显示前面的图标
                if (iconRes != -1) {

                    iconStart.visibility = View.VISIBLE
                    iconStart.setImageResource(iconRes)
                    iconStart.colorFilter =
                        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                            iconTint, BlendModeCompat.SRC_ATOP
                        )
                }

                //显示后面的图标
                if (endIconRes != -1) {
                    iconEnd.visibility = View.VISIBLE
                    iconEnd.setImageResource(endIconRes)
                    iconEnd.colorFilter =
                        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                            endIconTint, BlendModeCompat.SRC_ATOP
                        )
                }

                //设置inputType
                when (inputType) {

                    "0" -> inputContent.inputType = InputType.TYPE_CLASS_PHONE
                    "1" -> inputContent.inputType = InputType.TYPE_CLASS_NUMBER
                    "2" -> inputContent.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                    else -> inputContent.inputType = InputType.TYPE_CLASS_TEXT
                }

                tvTitle.text = titleStr
                inputContent.hint = hint
                inputContent.setText(contentStr)
                inputContent.isEnabled = editEnable
            }

        } finally {
            typeArr.recycle()
        }


    }

    /**
     * 更改内容
     * @param content String
     */
    fun setContent(content: String) {
        this.contentStr = content
        binding.inputContent.setText(contentStr)
    }

    /**
     * 获取输入框的文本
     * @return String
     */
    fun getContent(): String {
        return binding.inputContent.text.toString().trim()
    }

    /**
     * 更改标题
     * @param title String
     */
    fun setTitle(title: String) {
        this.titleStr = title
        binding.tvTitle.text = titleStr
    }

    /**
     * 给尾部图标加点击事件
     * @param listener OnClickListener
     */
    fun setEndIconOnClick(listener: OnClickListener) {
        binding.iconEnd.setOnClickListener(listener)
    }

    /**
     * 设置编辑框是可用
     * @param b Boolean
     */
    fun setEditEnable(b: Boolean) {
        binding.inputContent.isEnabled = b
    }
}
