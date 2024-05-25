package org.arrowgame.client.utils;

public record CustomLocale(String language) {
    public static final CustomLocale DEUTSCH = new CustomLocale("de");
    public static final CustomLocale ENGLISH = new CustomLocale("en");
    public static final CustomLocale ROMANIAN = new CustomLocale("ro");
    public static final CustomLocale FRENCH = new CustomLocale("fr");
    public static final CustomLocale ITALIAN = new CustomLocale("it");

    public static String[] values() {
        return new String[]{DEUTSCH.language(), ENGLISH.language(), ROMANIAN.language(), FRENCH.language(), ITALIAN.language()};
    }
}
