package HelperFunctions;

import java.util.Locale;
import java.util.ResourceBundle;

public class DetermineLanguage {

    /**
     * Get system language and resource bundle
     * @return ResourceBundle
     */
    public static ResourceBundle getLanguage() {
        Locale systemLanguage = Locale.forLanguageTag(Locale.getDefault().getLanguage());
        return ResourceBundle.getBundle("Resources/Language", systemLanguage);
    }
}
