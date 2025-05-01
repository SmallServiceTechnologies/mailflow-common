package de.flowsuite.mailflow.common.util;

import static de.flowsuite.mailflow.common.util.Util.BERLIN_ZONE;

import de.flowsuite.mailflow.common.constant.Timeframe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.Date;

public class AnalyticsUtil {
    private static final Logger LOG = LoggerFactory.getLogger(AnalyticsUtil.class);

    public static ZonedDateTime resolveStartDate(Date from, Timeframe timeframe) {
        if (from != null) return ZonedDateTime.ofInstant(from.toInstant(), BERLIN_ZONE);

        ZonedDateTime now =
                ZonedDateTime.now(BERLIN_ZONE).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return switch (timeframe) {
            case DAILY -> now.minusDays(7);
            case WEEKLY -> now.minusWeeks(4);
            case MONTHLY -> now.minusMonths(3);
            case YEARLY -> now.minusYears(1);
        };
    }

    public static ZonedDateTime resolveEndDate(Date to) {
        return (to != null)
                ? ZonedDateTime.ofInstant(to.toInstant(), BERLIN_ZONE)
                : ZonedDateTime.now(BERLIN_ZONE);
    }

    public static void validateDateRange(ZonedDateTime start, ZonedDateTime end) {
        if (end.isBefore(start))
            throw new IllegalArgumentException("End date must be after start date.");
    }

    public static String getTruncUnitForTimeframe(Timeframe timeframe) {
        return switch (timeframe) {
            case DAILY -> "day";
            case WEEKLY -> "week";
            case MONTHLY -> "month";
            case YEARLY -> "year";
        };
    }
}
