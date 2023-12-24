package com.serpider.service.megastream.util;

import java.text.NumberFormat;
import java.util.Locale;

public abstract class Utility {

    public static String timeConversion(Long millie){
        if (millie != null) {
            long seconds = (millie / 1000);
            long sec = seconds % 60;
            long min = (seconds / 60) % 60;
            long hrs = (seconds / (60 * 60)) % 24;
            if (hrs > 0) {
                return String.format("%02d:%02d:%02d", hrs, min, sec);
            } else {
                return String.format("%02d:%02d", min, sec);
            }
        } else {
            return null;
        }
    }

    public static String formatPrice(long price) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
        return numberFormat.format(price);
    }

    public static String applyDiscount(long price, long discount) {
        if (discount < 100) {
            long discountedPrice = price - (price * discount / 100);
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            String formattedDiscountedPrice = numberFormat.format(discountedPrice);
            return formattedDiscountedPrice;
        }else {
            long disprice = price - discount;
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());
            String formattedDiscountedPrice = numberFormat.format(disprice);
            return formattedDiscountedPrice;
        }
    }

}
