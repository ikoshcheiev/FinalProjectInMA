package org.example;

import java.util.Random;

public class StringUtils {
    public static String generateRandomDigit(int stringLength) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int randomtStringLength = stringLength;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(randomtStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public static double getPriceAsDouble(String priceLine){
        String price = priceLine;
        if(priceLine.contains(","))price = priceLine.replace(",","");
        return Double.parseDouble(price.substring(1).trim());
    }
}
