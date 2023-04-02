final class Utils {
    public static String token, roleName, logChannel
            , hashInstance, delimiter
            , siteTitle, siteContains, errorMessage
            , successMessage, newMemberMessage, newKeyMessage;
    static{ parseParam(); }

    private static void parseParam(){
        token = "";
        roleName = "";
        logChannel = "";

        hashInstance = "HmacSHA384";
        delimiter = "\\A";
        siteTitle = "";
        siteContains = "";
        errorMessage = "";
        successMessage = "";
        newMemberMessage = "";
        newKeyMessage = "";
    }
}
