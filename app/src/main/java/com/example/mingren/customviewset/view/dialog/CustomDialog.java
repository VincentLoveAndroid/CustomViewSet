package com.example.mingren.customviewset.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mingren.customviewset.R;

import java.util.HashMap;
import java.util.Map;



public class CustomDialog extends Dialog {
    private int mLayout;
    public View mView;
    private int width;
    private int height;
    private Map<Integer,Object> orgMap = new HashMap<>();
    private static final int CMD_DISMISS = 1;
    private static final int DEFAULT_SHOW_DELAY = 1500;
    private int mShowDelay = DEFAULT_SHOW_DELAY;  //对话框显示mShowDelay秒后，自动关闭
    private OnViewCreatedListener onViewCreatedListener;

    public Object getArg(int id){
        return orgMap.get(id);
    }
    public void setArg(int id,Object o){
        orgMap.put(id,o);
    }
    public Context mContext;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CMD_DISMISS:
                    dismiss();
                    break;
            }
        }
    };


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mContext = null;//释放context
    }

    public CustomDialog(int layout, Context context, int theme) {
        super(context, theme);
        mContext = context;
        mLayout = layout;
    }


    public CustomDialog(int layout, Context context) {
        this(layout, context, R.style.translucent_dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(mLayout, null, false);
        setContentView(mView);
        if (null != onViewCreatedListener) {
            onViewCreatedListener.onViewCreated(this);
        }
    }

    @Override
    public void dismiss() {
        try {
           super.dismiss();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        int w = lp.width;
        int h = lp.height;
        if (width != 0) {
            w = width;
        } else {
            width = w;
        }

        if (height != 0) {
            h = lp.height;
        } else {
            height = h;
        }
        getWindow().setLayout(w, h);
    }

    public void setOnClickListener(int id, View.OnClickListener onClickListener) {
        if (mView == null) {
            throw new IllegalArgumentException("this method must called after onCreateView() method, " +
                    "you can implements OnViewCreatedListener and call it in onViewCreated() method");
        }
        mView.findViewById(id).setOnClickListener(onClickListener);
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setText(int id, CharSequence text,int size) {
        if (mView == null) {
            throw new IllegalArgumentException("this method must called after onCreateView() method, " +
                    "you can implements OnViewCreatedListener and call it in onViewCreated() method");
        }
        TextView textView = (TextView) mView.findViewById(id);
        if(size != 0){
            textView.setTextSize(size);
        }
        if (text != null)
            textView.setText(text);
    }
    public void setText(int id, CharSequence text){
        setText(id,text,0);
    }
    protected View getContentView() {
        return mView;
    }

    public View findViewById(int id) {
        return mView.findViewById(id);
    }

    public void setImageResrouce(int id, int resId) {
        if (mView == null) {
            throw new IllegalArgumentException("this method must called after onCreateView() method, " +
                    "you can implements OnViewCreatedListener and call it in onViewCreated() method");
        }
        ImageView imageView = (ImageView) mView.findViewById(id);

        if (resId == -1) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setImageResource(resId);
        }

    }

    public void setLayout(int layout) {
        mLayout = layout;
    }

    public void setShowDelay(int delay) {
        mShowDelay = delay;
    }

    public void show(boolean autoDismiss) {
        try {
            show();
            if (autoDismiss) {
                handler.sendEmptyMessageDelayed(CMD_DISMISS, mShowDelay);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void show() {
        try{
            super.show();
        }catch (Throwable e){
            e.printStackTrace();
        }

    }

    public void setOnViewCreatedListener(OnViewCreatedListener onViewCreatedListener) {
        this.onViewCreatedListener = onViewCreatedListener;
    }
    public  interface OnViewCreatedListener {
        void onViewCreated(CustomDialog dialog);
    }

}
