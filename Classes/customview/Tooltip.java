package edu.msstate.nsparc.mdcpsabusereporting.ui.customviews.tooltip;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import edu.msstate.nsparc.mdcpsabusereporting.R;
import edu.msstate.nsparc.mdcpsabusereporting.utils.SizeUtils;
import edu.msstate.nsparc.mdcpsabusereporting.utils.UIUtils;


/**
 * @author Created by e.fetskovich on 4/29/17.
 */

public class Tooltip extends RelativeLayout {

    private static final int ARROW_WEIGHT_PX = 65;
    private static final int ARROW_HEIGHT_PX = 65;

    // this coefficient needs to add a small padding between the arrow and clicked item
    private static final int PADDING_COEFFICIENT = 4;

    private int tooltipHeight;

    private ViewGroup fullLayout;
    private ViewGroup tooltipWrapper;

    private View clickedItem;
    private Context context;
    private Tooltip tooltip;
    private ImageView arrow;
    private View overlayView;

    private int leftRightMargin;
    private int actionBarHeight = 0;

    private boolean isTooltipOnBottom;
    private boolean isTooltipShowed = false;

    // for Builder
    private Intent intent;
    private int layoutId;
    private String mainText;
    private String actionLinkText;

    private TextView actionLinkTextView;
    private TextView mainTextView;


    public Tooltip(Context context) {
        super(context);
        this.context = context;

        // getting the root layout
        this.fullLayout = (ViewGroup) ((ViewGroup) (getActivity()).
                findViewById(android.R.id.content)).getChildAt(0);

        // 8 is a wrapper margins in dp
        this.leftRightMargin = (int) SizeUtils.convertDpToPixel(8);
    }

    public Tooltip(Context context, Intent intent, String mainText, String actionLinkText, int layoutId) {
        this(context);
        this.intent = intent;
        this.layoutId = layoutId;
        this.mainText = mainText;
        this.actionLinkText = actionLinkText;
    }

    public Tooltip(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Tooltip(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void show(View clickedItem) {
        if (!isTooltipShowed) {
            this.clickedItem = clickedItem;

            initTooltip();
            initTooltipWrapper();
            initStartTooltipPosition();

            addOverlayView();

            calculateTooltipPosition();
            calculateArrowPosition();

            tooltipWrapper.addView(tooltip);
            tooltipWrapper.addView(arrow);

            fullLayout.addView(tooltipWrapper);
            tooltipWrapper.bringToFront();

            isTooltipShowed = true;
        }
    }

    public void hideTooltip() {
        if (isTooltipShowed) {
            fullLayout.removeView(tooltipWrapper);
            fullLayout.removeView(overlayView);
            isTooltipShowed = false;
            UIUtils.setStatusBarColor(((Activity) context).getWindow(), R.color.statusBarReport);
        }
    }

    private void initTooltip() {
        tooltip = (Tooltip) View.inflate(context, layoutId, null);

        tooltip.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                tooltipHeight = tooltip.getHeight();
                Log.i("MyTag", "OnGlobalLayout: "+tooltipHeight);
                calculateTooltipPosition();
            }
        });

        actionLinkTextView = (TextView) tooltip.findViewById(R.id.action_link_text);
        actionLinkTextView.setText(actionLinkText);

        mainTextView = (TextView) tooltip.findViewById(R.id.tooltip_main_text);
        mainTextView.setText(mainText);

        actionLinkTextView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(intent);
            }
        });
    }

    // tooltip wrapper needs to combine  the arrow and rectangle (tooltip without arrow)
    private void initTooltipWrapper() {
        tooltipWrapper = new RelativeLayout(context);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(leftRightMargin, 0, leftRightMargin, 0);
        tooltipWrapper.setLayoutParams(params);
    }

    private void calculateTooltipPosition() {
        int[] currentItemPosition = SizeUtils.getItemPosition(clickedItem);
        int yCoordinateOfItem = currentItemPosition[1] - actionBarHeight;
        int displayHeight = SizeUtils.getDisplaySize(context).y - actionBarHeight;

        LayoutParams params = (LayoutParams) tooltip.getLayoutParams();

        Log.i("MyTag", "calculateTooltipPosition: " + tooltipHeight);
        // if we have a place under the clicked item for showing our tooltip
        if (isTherePlaceForTooltipOnBottom(displayHeight, yCoordinateOfItem)) {
            Log.i("MyTag", "Bottom");

            params.leftMargin = 0;
            params.topMargin = yCoordinateOfItem + clickedItem.getHeight() / 2 + ARROW_HEIGHT_PX / PADDING_COEFFICIENT;
            isTooltipOnBottom = true;
        } else {
            Log.i("MyTag", "Top");

            params.leftMargin = 0;
            params.topMargin = yCoordinateOfItem - tooltipHeight - clickedItem.getHeight() - ARROW_HEIGHT_PX / 2 + PADDING_COEFFICIENT;
            isTooltipOnBottom = false;
        }
        tooltip.setLayoutParams(params);

    }

    private boolean isTherePlaceForTooltipOnBottom(int displayHeight, int yCoordinateOfItem) {
        return displayHeight - yCoordinateOfItem - clickedItem.getHeight() - ARROW_HEIGHT_PX > displayHeight / 2;
    }

    private void calculateArrowPosition() {
        ImageView arrowImage = new ImageView(context);
        Drawable arrowDrawable;

        int[] currentItemPosition = SizeUtils.getItemPosition(clickedItem);

        int yCoordinateOfItem = currentItemPosition[1] - actionBarHeight;
        int xCoordinateOfItem = currentItemPosition[0];

        int yOfArrow = 0;
        int xOfArrow = xCoordinateOfItem + clickedItem.getWidth() / 2 - ARROW_WEIGHT_PX / 2 - leftRightMargin;

        if (isTooltipOnBottom) {
            arrowDrawable = getResources().getDrawable(R.drawable.ic_arrow_up);
            // 2 is a small image fixes
            yOfArrow = yCoordinateOfItem + clickedItem.getHeight() / 2 - ARROW_HEIGHT_PX / 2 + 2;
        } else {
            arrowDrawable = getResources().getDrawable(R.drawable.ic_arrow_down);
            yOfArrow = yCoordinateOfItem - clickedItem.getHeight() - ARROW_HEIGHT_PX;
        }

        arrowImage.setImageDrawable(arrowDrawable);
        arrowImage.setLayoutParams(getArrowLayoutParams(xOfArrow, yOfArrow));

        this.arrow = arrowImage;
    }

    private LayoutParams getArrowLayoutParams(int x, int y) {
        LayoutParams arrowParams = new LayoutParams(ARROW_WEIGHT_PX, ARROW_HEIGHT_PX);
        arrowParams.leftMargin = x;
        arrowParams.topMargin = y;
        return arrowParams;
    }

    private void initStartTooltipPosition() {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = 0;
        params.topMargin = 0;
        tooltip.setLayoutParams(params);
    }

    // Overlay View is a dark background around the tooltip
    private void addOverlayView() {
        this.overlayView = new View(context);
        overlayView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        overlayView.setClickable(true);
        overlayView.setBackgroundColor(context.getResources().getColor(R.color.tooltipGreyOpacityBackground));
        UIUtils.setStatusBarColor(((Activity) context).getWindow(), R.color.tooltipGreyOpacityBackground);

        overlayView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                fullLayout.removeView(tooltipWrapper);
                fullLayout.removeView(overlayView);
                isTooltipShowed = false;
                UIUtils.setStatusBarColor(((Activity) context).getWindow(), R.color.statusBarReport);
            }
        });
        fullLayout.addView(overlayView);
    }

    private Activity getActivity() {
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("MyTag", "onSizeChanged: "+h);
    }
}
