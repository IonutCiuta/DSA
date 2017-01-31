package twitter.masking;

import java.util.Scanner;

/**
 * Ionut Ciuta on 1/25/2017.
 */
public class Solution {
    enum Type {PHONE, EMAIL}
    private static final String PATTERN_PHONE = "P:";
    private static final String PATTERN_MAIL = "E:";
    private static final String NAT1 = "+*-";
    private static final String NAT2 = "+**-";
    private static final String NAT3 = "+***-";
    private static final String PHONE_MASK = "***-***-";
    private static final String EMAIL_MASK = "*****";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        StringBuilder builder = new StringBuilder("");

        while(scan.hasNext()) {
            String line = scan.nextLine();
            Type type = getInputType(line);

            if(type == null) {
                scan.close();
                throw new RuntimeException("Invalid input type!");
            }

            String toBeMasked = line.substring(2);
            switch (type) {
                case PHONE:
                    builder.append(maskPhone(toBeMasked));
                    builder.append(System.lineSeparator());
                    break;

                case EMAIL:
                    builder.append(maskEmail(toBeMasked));
                    builder.append(System.lineSeparator());
                    break;
            }
        }

        scan.close();
        System.out.println(builder.toString());
    }

    private static Type getInputType(String line) {
        String part = line.substring(0, 2);
        if(part.equals(PATTERN_PHONE))
            return Type.PHONE;

        if(part.equals(PATTERN_MAIL))
            return Type.EMAIL;

        return null;
    }

    private static String maskPhone(String input) {
        String aux = cleanPhone(input);
        String ending = aux.substring(aux.length()  - 4, aux.length());
        String maskedPhone = "";

        if(aux.charAt(0) == '+') {
            int natCode = aux.length() - 11;
            switch (natCode) {
                case 1:
                    maskedPhone += NAT1;
                    break;

                case 2:
                    maskedPhone += NAT2;
                    break;

                case 3:
                    maskedPhone += NAT3;
                    break;
            }
        }

        maskedPhone += PHONE_MASK + ending;
        return maskedPhone;
    }

    private static String maskEmail(String input) {
        String maskedEmail = removeSpaces(input);
        String[] tokens = maskedEmail.split("@");
        maskedEmail = tokens[0].charAt(0) + EMAIL_MASK + tokens[0].charAt(tokens[0].length() - 1) + "@" + tokens[1];
        return maskedEmail;
    }

    private static String removeSpaces(String input) {
        return input.replaceAll("\\s+","");
    }

    private static String cleanPhone(String input) {
        return input.replaceAll("[\\s\\.\\(\\)\\-]", "");
    }
}
