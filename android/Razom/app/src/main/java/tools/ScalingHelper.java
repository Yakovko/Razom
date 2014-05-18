package tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.math.BigInteger;

/**
 * Created by ykomasyuk on 18.05.2014.
 */
public class ScalingHelper {
    public static Bitmap scaleWithAspectRatio(Bitmap image, int displayWidth, int displayHeight) {
        int imageVerticalAspectRatio, imageHorizontalAspectRatio;
        float bestFitScalingFactor = 0;
        float percesionValue = (float) 0.2;

        //getAspect Ratio of Image
        int imageHeight = (int) (Math.ceil((double) image.getHeight() / 100) * 100);
        int imageWidth = (int) (Math.ceil((double) image.getWidth() / 100) * 100);
        int GCD = BigInteger.valueOf(imageHeight).gcd(BigInteger.valueOf(imageWidth)).intValue();
        imageVerticalAspectRatio = imageHeight / GCD;
        imageHorizontalAspectRatio = imageWidth / GCD;

        int leftMargin = 0;
        int rightMargin = 0;
        int topMargin = 0;
        int bottomMargin = 0;
        int containerWidth = displayWidth - (leftMargin + rightMargin);
        int containerHeight = displayHeight - (topMargin + bottomMargin);

        //iterate to get bestFitScaleFactor per constraints
        while ((imageHorizontalAspectRatio * bestFitScalingFactor <= containerWidth) &&
                (imageVerticalAspectRatio * bestFitScalingFactor <= containerHeight)) {
            bestFitScalingFactor += percesionValue;
        }

        //return bestFit bitmap
        int bestFitHeight = (int) (imageVerticalAspectRatio * bestFitScalingFactor);
        int bestFitWidth = (int) (imageHorizontalAspectRatio * bestFitScalingFactor);
        image = Bitmap.createScaledBitmap(image, bestFitWidth, bestFitHeight, true);

        //Position the bitmap centre of the container
        int leftPadding = (containerWidth - image.getWidth()) / 2;
        int topPadding = (containerHeight - image.getHeight()) / 2;
        Bitmap backDrop = Bitmap.createBitmap(containerWidth, containerHeight, Bitmap.Config.RGB_565);
        Canvas can = new Canvas(backDrop);
        can.drawBitmap(image, leftPadding, topPadding, null);
        return backDrop;
    }
}
