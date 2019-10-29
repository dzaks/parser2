package ch.sbb.fss.uic301.parser;

/**
 * Type of UIC 301 data.
 */
public enum Uic301Type {

    /**
     * Services allocated. Used for bookings and cancellations (typically for
     * reservation products) between the requesting party and the allocating
     * party, compiled by the allocating party.
     */
    G4,

    /**
     * Shares apportioned. Used on allocation (typically for reservation
     * products) between the allocating party and the service providing party,
     * compiled by the allocating party.
     */
    G5_ALLOCATION,

    /**
     * Shares apportioned. Used on issue (typically for NRT or RPT products)
     * between the issuing party and the service providing party, compiled by
     * the issuing party
     */
    G5_ISSUE;

}
