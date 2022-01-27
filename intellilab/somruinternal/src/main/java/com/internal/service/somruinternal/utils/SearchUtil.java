package com.internal.service.somruinternal.utils;

public class SearchUtil {

    public static String replaceIllegalSearchCharacters( String searchTerm ) {
        String st = searchTerm == null ? "" : searchTerm;
        st = st.replaceAll("slash", "/");
        st = st.replaceAll("whitespace", " ");
        st = st.replaceAll("pong", "#");
        st = st.replaceAll("percent", "%");
        st = st.replaceAll("questionmark", "?");
        st = st.replaceAll("period", ".");
        return st;
    }    
}
