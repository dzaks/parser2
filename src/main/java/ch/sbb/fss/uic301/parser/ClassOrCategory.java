package ch.sbb.fss.uic301.parser;

/**
 * Class or category.
 */
public enum ClassOrCategory {

    /** no class or category of service. */
    UNDEFINED("000"),

    /* 1st class. */
    CLASS_1ST("001"),

    /* 2nd class. */
    CLASS_2ND("002"),

    /* couchette 1st class. */
    COUCHETTE_1ST_CLASS("007"),

    /* couchette 2nd class. */
    COUCHETTE_2ND_CLASS("008"),

    /* change to higher class. */
    CHANGE_TO_HIGHER_CLASS("009"),

    /* single sleeper. */
    SINGLE_SLEEPER("011"),

    /* special sleeper. */
    SPECIAL_SLEEPER("012"),

    /* double sleeper. */
    DOUBLE_SLEEPER("013"),

    /* tourist T2 sleeper. */
    TOURIST_T2_SLEEPER("014"),

    /* tourist T3 sleeper. */
    TOURIST_T3_SLEEPER("015"),

    /* tourist T4 sleeper. */
    TOURIST_T4_SLEEPER("016"),

    /* single with shower. */
    SINGLE_WITH_SHOWER("017"),

    /* double with shower. */
    DOUBLE_WITH_SHOWER("018"),

    /* tourist T3 with shower. */
    TOURIST_T3_WITH_SHOWER("019"),

    /* 1st class reclining seat. */
    CLASS_1ST_RECLINING_SEAT("062"),

    /* 2nd class reclining seat. */
    CLASS_2ND_RECLINING_SEAT("063"),

    /* executive compartment. */
    EXECUTIVE_COMPARTMENT("076"),

    /* video compartment. */
    VIDEO_COMPARTMENT("077"),

    /* 4-berth couchette. */
    COUCHETTE_4_BERTH("078"),

    /* bicycle with included seat. */
    BICYCLE_WITH_INCLUDED_SEAT("079"),

    /* sleeping car with shower. */
    SLEEPING_CAR_WITH_SHOWER("080"),

    /* breakfast included. */
    BREAKFAST_INCLUDED("081"),

    /* meal included/lunch. */
    MEAL_INCLUDED_LUNCH("082"),

    /* meal included/dinner. */
    MEAL_INCLUDED_DINNER("083"),

    /* panoramic seat. */
    PANORAMIC_SEAT("084"),

    /* euraffair. */
    EURAFFAIR("085"),

    /* club. */
    CLUB("087"),

    /* preferente. */
    PREFERENTE("088"),

    /* turista. */
    TURISTA("089"),

    /* bicycle only. */
    BICYCLE_ONLY("090");

    private final String code;

    private ClassOrCategory(final String code) {
        this.code = code;
    }

    /**
     * Returns the code.
     * 
     * @return Channel code.
     */
    public String getCode() {
        return code;
    }

    /**
     * Determined if the given code is valid.
     * 
     * @param code
     *            Code to verify.
     * 
     * @return <code>true</code> if the code is known.
     */
    public static boolean valid(final String code) {
        for (ClassOrCategory value : values()) {
            if (value.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the enumeration instance for the given code. Throws an
     * {@link IllegalArgumentException} if the code is unknown
     * ({@link #valid(String)} will return <code>false</code> in this case.
     * 
     * @param code
     *            Code to return an instance for.
     * 
     * @return Enumeration instance.
     */
    public static ClassOrCategory forCode(final String code) {
        for (ClassOrCategory value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }

}
