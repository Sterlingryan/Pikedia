package thesis.uom.pikedia.ui.camera;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;

/**
 * Created by SterlingRyan on 2/15/2017.
 */

public class AutoFitTextureView extends TextureView{
    private int mRatioHeight = 0;
    private int mRatioWidth = 0;

    public AutoFitTextureView(Context context) {
        this(context, null);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoFitTextureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setAspectRatio(int width, int height){
        if (width <0 || height < 0){
            throw new IllegalArgumentException("Size cannot be negative");
        }
        mRatioHeight = height;
        mRatioWidth = width;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        // Leave the height and width of the view as given by the parent view
        if (mRatioWidth == 0 || mRatioHeight == 0){
            setMeasuredDimension(width,height);
        } else {
            // Arrange the TextureView so that the height and the width of the view are in ratio
            if(width < height * mRatioWidth/mRatioHeight){
                setMeasuredDimension(width, width * mRatioHeight /mRatioWidth);
            } else {
                setMeasuredDimension(height * mRatioWidth/mRatioHeight, height);
            }
        }
    }
}
