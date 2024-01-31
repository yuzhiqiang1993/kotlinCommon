package com.yzq.kotlincommon.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.yzq.dialog.core.BaseDialogFragment;

public class JavaDialog extends BaseDialogFragment<JavaDialog> {

    public JavaDialog(@NonNull FragmentActivity hostActivity) {
        super(hostActivity);
    }

    @NonNull
    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return null;
    }

}
