package com.shl.keyboardchangedlistener;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Rect;
import android.view.View;
import android.widget.PopupWindow;

public class KeyBoardChangedUtil implements View.OnLayoutChangeListener {

    private View mContentView;
    private Rect preVisableRect;
    private OnKeyBoardChangedListener onKeyBoardChangedListener;

    public KeyBoardChangedUtil(View view) {
        mContentView = view;
    }

    public KeyBoardChangedUtil(Activity activity) {
        mContentView = activity.findViewById(android.R.id.content);
    }

    public KeyBoardChangedUtil(PopupWindow popupWindow) {
        mContentView = popupWindow.getContentView();
    }

    public KeyBoardChangedUtil(Dialog dialog) {
        mContentView = dialog.findViewById(android.R.id.content);
    }

    public OnKeyBoardChangedListener getOnKeyBoardChangedListener() {
        return onKeyBoardChangedListener;
    }

    public void setOnKeyBoardChangedListener(OnKeyBoardChangedListener onKeyBoardChangedListener) {
        this.onKeyBoardChangedListener = onKeyBoardChangedListener;
        if (mContentView!=null) {
            mContentView.addOnLayoutChangeListener(this);
        }
    }

    public static KeyBoardChangedUtil create(Object object) {
        if (object instanceof View) {
            return new KeyBoardChangedUtil((View) object);
        } else if (object instanceof Activity) {
            return new KeyBoardChangedUtil((Activity) object);

        } else if (object instanceof PopupWindow) {
            return new KeyBoardChangedUtil((PopupWindow) object);

        } else if (object instanceof Dialog) {
            return new KeyBoardChangedUtil((Dialog) object);

        }else {
            throw new RuntimeException("不支持的类型");
        }
    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        Rect rect = new Rect();
        mContentView.getGlobalVisibleRect(rect);

        if (rect.bottom < bottom) {
            if (onKeyBoardChangedListener!=null) {
                onKeyBoardChangedListener.onKeyBoardShow();
            }
        } else if (preVisableRect!= null&&preVisableRect.bottom < rect.bottom) {
            if (onKeyBoardChangedListener!=null) {
                onKeyBoardChangedListener.onKeyBoardHidden();
            }
        }
        preVisableRect = rect;
    }

    public void onDestroy() {
        if (mContentView!=null) {
            mContentView.removeOnLayoutChangeListener(this);
        }
    }

    public interface OnKeyBoardChangedListener {

        void onKeyBoardHidden();

        void onKeyBoardShow();

    }


}
