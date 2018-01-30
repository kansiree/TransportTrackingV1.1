package th.co.gosoft.storemobile.transporttracking.Models;

import android.graphics.Bitmap;

/**
 * Created by Jubjang on 9/14/2017.
 */

public class model_photo {
    private Bitmap bitmap;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
