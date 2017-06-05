package com.example.mingren.customviewset.view.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.mingren.customviewset.R;

/**
 * Created by vincent on 2017/5/27.
 */

public class CustomDialogUtil {

    private static CustomDialog createDialog(Context context, CustomDialog.OnViewCreatedListener onViewCreatedListener, int layout) {
        CustomDialog dialog = new CustomDialog(layout, context);
        dialog.setOnViewCreatedListener(onViewCreatedListener);
        return dialog;
    }
    //--------------------------自定义Dialog 管理---------------------------//

    /**
     * 高度固定单按钮dialog
     */
    public static CustomDialog showTextSingleButtonDialog(Context activity, final CharSequence title, final CharSequence content, final String buttonText, final int textColor, final View.OnClickListener listener) {
        CustomDialog dialog = createDialog(activity, new CustomDialog.OnViewCreatedListener() {
            @Override
            public void onViewCreated(CustomDialog dialog) {
                dialog.setText(R.id.tv_confirm, buttonText);
                dialog.setText(R.id.tv_content, content);
                dialog.setText(R.id.tv_title, title);
                TextView tv_confirm = (TextView) dialog.findViewById(R.id.tv_confirm);
                if (title == null)
                    dialog.findViewById(R.id.tv_title).setVisibility(View.GONE);
                else {
                    dialog.findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
                }
                if (content == null)
                    dialog.findViewById(R.id.tv_content).setVisibility(View.GONE);
                if (textColor != -1) {
                    tv_confirm.setTextColor(textColor);
                }
                if (listener != null) {
                    tv_confirm.setOnClickListener(listener);
                }
            }
        }, R.layout.dialog_text_single_button);

        dialog.setCancelable(false);
        dialog.show();
        return dialog;
    }
}
