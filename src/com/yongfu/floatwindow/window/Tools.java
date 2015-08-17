
package com.yongfu.floatwindow.window;

//import android.os.SystemProperties;
import android.util.Log;

public class Tools {
    private static final String MSTAR_PRODUCT_CHARACTERISTICS = "mstar.product.characteristics";

    private static final String MSTAR_PRODUCT_STB = "stb";

    public static boolean isBox() {
    	Log.d("jack.chen","isBox() ");
//        String value = SystemProperties.get(MSTAR_PRODUCT_CHARACTERISTICS, "");
//        Log.i("Tools", "        System property is : " + value);
//        if (MSTAR_PRODUCT_STB.equals(value)) {
            return true;
//        } else {
//            return false;
//        }
    }
}
