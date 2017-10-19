package com.euclid.uptiiq.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.euclid.uptiiq.R;

/**
 * @author e.fetskovich on 9/11/17.
 */

public class TextToImageConverter {

    public static void convertByCanvas(ImageView imageView, EditText editText) {
        String text = editText.getText().toString();

        final Rect bounds = new Rect();
        TextPaint textPaint = new TextPaint() {
            {
                setColor(Color.BLACK);
                setTextAlign(Paint.Align.LEFT);
                setTextSize(20f);
                setAntiAlias(true);
            }
        };
        textPaint.getTextBounds(text, 0, text.length(), bounds);
        StaticLayout mTextLayout = new StaticLayout(text, textPaint,
                bounds.width(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
        int maxWidth = -1;
        for (int i = 0; i < mTextLayout.getLineCount(); i++) {
            if (maxWidth < mTextLayout.getLineWidth(i)) {
                maxWidth = (int) mTextLayout.getLineWidth(i);
            }
        }
        final Bitmap bmp = Bitmap.createBitmap(maxWidth, mTextLayout.getHeight(),
                Bitmap.Config.ARGB_8888);

        final Canvas canvas = new Canvas(bmp);
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        mTextLayout.draw(canvas);

        imageView.setImageBitmap(bmp);
    }

    public static Bitmap convertByDrawingCache(EditText editText, Context context, boolean defaultValue) {
        Bitmap bitmap = null;

        TextView textView = new TextView(context);

        textView.setTypeface(editText.getTypeface());
        if (defaultValue) {
            textView.setText(context.getString(R.string.watermark_text_hint));
        } else {
            textView.setText(editText.getText());
        }
        textView.setTextColor(context.getResources().getColor(R.color.watermarkText));
        textView.setLineSpacing(0, 0.8f);
        textView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        textView.measure(
                View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        textView.setDrawingCacheEnabled(true);
        textView.buildDrawingCache();

        bitmap = textView.getDrawingCache().copy(Bitmap.Config.ARGB_8888, false);
        textView.destroyDrawingCache();

        return bitmap;
    }

    public static Bitmap convertByDrawingCache(String text, String font, Context context, boolean defaultValue) {
        Bitmap bitmap = null;

        TextView textView = new TextView(context);

        Typeface typeface = FontUtils.getTypefaceFromText(context, font);
        textView.setTypeface(typeface);

        if (defaultValue) {
            textView.setText(context.getString(R.string.watermark_text_hint));
        } else {
            textView.setText(org.apache.commons.lang3.StringEscapeUtils.unescapeJava(text));
        }

        textView.setTextColor(context.getResources().getColor(R.color.watermarkText));
        textView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        textView.measure(
                View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        textView.setDrawingCacheEnabled(true);
        textView.buildDrawingCache();

        bitmap = textView.getDrawingCache().copy(Bitmap.Config.ARGB_8888, false);
        textView.destroyDrawingCache();

        return bitmap;
    }
}