package todolist.task.com.todolist.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

public class FontUtils {
    private static final String FONT_REGULAR = "Lato-Regular.ttf";
    private static final String FONT_SEMIBOLD = "Lato-Bold.ttf";

    private static Typeface font_semibold, font_regular;

    public static void setSemiBoldFont(View view) {
        if (view != null) {
            if (font_semibold == null) {
                font_semibold = getTypefaceFromAsset(view.getContext(), FONT_SEMIBOLD);
            }

            setTypeface(view, font_semibold);
        }
    }

    public static void setRegularFont(View view) {
        if (view != null) {
            if (font_regular == null) {
                font_regular = getTypefaceFromAsset(view.getContext(), FONT_REGULAR);
            }

            setTypeface(view, font_regular);
        }
    }

    private static void setTypeface(View view, Typeface typeface) {
        if (view != null && view instanceof TextView && typeface != null) {
            ((TextView) view).setTypeface(typeface);
        }
    }

    private static Typeface getTypefaceFromAsset(Context context, String filename) {
        Typeface typeface = null;
        if (context != null && !TextUtils.isEmpty(filename)) {
            typeface = Typeface.createFromAsset(context.getAssets(), filename);
        }

        return typeface;
    }
}
