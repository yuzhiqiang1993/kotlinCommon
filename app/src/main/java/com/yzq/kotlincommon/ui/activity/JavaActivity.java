package com.yzq.kotlincommon.ui.activity;

import android.os.Bundle;

import com.therouter.router.Route;
import com.yzq.base.ui.activity.BaseActivity;
import com.yzq.common.constants.RoutePath;
import com.yzq.dialog.LottieDialog;
import com.yzq.dialog.core.DialogConfig;
import com.yzq.kotlincommon.databinding.ActivityJavaBinding;
import com.yzq.kotlincommon.dialog.CustomDialog;

@Route(path = RoutePath.Main.JAVA_ACTIVITY)
public class JavaActivity extends BaseActivity {


    private ActivityJavaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        EdgeToEdge.enable(this);
        binding = ActivityJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initWidget() {
        binding.includedToolbar.toolbar.setTitle("JavaActivity");
        binding.btnDialogFragment.setOnClickListener(v -> {

            CustomDialog customDialogFragment = new CustomDialog(this);

            customDialogFragment.safeShow();


            new LottieDialog(this)
                    .setDialogConfig(new DialogConfig().width(100))
                    .lottieUrl("https://assets9.lottiefiles.com/packages/lf20_8xjzqz.json")
                    .safeShow();
        });

    }


}
