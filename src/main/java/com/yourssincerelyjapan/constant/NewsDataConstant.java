package com.yourssincerelyjapan.constant;

public enum NewsDataConstant {

    ;

    public static final String API_KEY = "?apikey={apikey}";
    public static final String COUNTRY_PARAM = "&country={country}";
    public static final String LANGUAGE_PARAM = "&language={language}";
    public static final String IMAGE_PARAM = "&image={image}";

    //Choose from which domains to receive news.
    //Example: japantoday,kyodonews,soranews24,japantimes,tokyoweekender
    public static final String DOMAIN_PARAM = "&domain={domain}";

    //Example: english.kyodonews.net,soranews24.com,japantoday.com,japantimes.co.jp,tokyoweekender.com,japan-forward.com
    public static final String DOMAIN_URL_PARAM = "&domainurl={domainurl}";

    //Search the news articles for a specific timeframe (Minutes and Hours).
    //For hours, you can set a timeframe of 1 to 48, and for minutes, you can define a timeframe of 1m to 2880m.
    //For example, if you want to get the news for the past 6 hours then use timeframe=6
    //and if you want to get news for the last 15 min then use timeframe=15m.
    //Note - You can only use timeframe either in hours or minutes.
    public static final String TIMEFRAME_IN_HOURS_PARAM = "&timeframe={timeframe}";
    public static final String TIMEFRAME_IN_MINUTES_PARAM = "timeframe={timeframe}m";
}
