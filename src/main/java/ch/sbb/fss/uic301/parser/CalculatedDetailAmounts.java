package ch.sbb.fss.uic301.parser;

import java.math.BigDecimal;

/**
 * Amounts calculated from all details of a given statement currency period.
 */
public final class CalculatedDetailAmounts {

    private BigDecimal grossAmountToBeCredited;

    private BigDecimal grossAmountToBeDebited;

    private BigDecimal amountCommissionCredited;

    private BigDecimal amountCommissionDebited;

    private NetBalanceType netBalanceType;

    private BigDecimal netBalanceAmount;
    
    private Uic301Type type = Uic301Type.G5_ALLOCATION;

    private boolean sealed;

    /**
     * Default constructor.
     */
    public CalculatedDetailAmounts() {
        super();
        grossAmountToBeCredited = BigDecimal.ZERO.setScale(2,
                BigDecimal.ROUND_HALF_UP);
        grossAmountToBeDebited = BigDecimal.ZERO.setScale(2,
                BigDecimal.ROUND_HALF_UP);
        amountCommissionCredited = BigDecimal.ZERO.setScale(2,
                BigDecimal.ROUND_HALF_UP);
        amountCommissionDebited = BigDecimal.ZERO.setScale(2,
                BigDecimal.ROUND_HALF_UP);
        sealed = false;
    }

    /**
     * Adds the given amounts to the internal sum.
     * 
     * @param grossAmountToBeCredited
     *            Gross amount to be credited.
     * @param grossAmountToBeDebited
     *            Gross amount to be debited.
     * @param amountCommissionCredited
     *            Amount commission credited.
     * @param amountCommissionDebited
     *            Amount commision debited.
     */
    public void addAmounts(final BigDecimal grossAmountToBeCredited,
            final BigDecimal grossAmountToBeDebited,
            final BigDecimal amountCommissionCredited,
            final BigDecimal amountCommissionDebited) {

        if (sealed) {
            throw new UnsupportedOperationException(
                    "Adding data is not allowed after sealing the instance");
        }

        this.grossAmountToBeCredited = this.grossAmountToBeCredited
                .add(grossAmountToBeCredited)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.grossAmountToBeDebited = this.grossAmountToBeDebited
                .add(grossAmountToBeDebited)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.amountCommissionCredited = this.amountCommissionCredited
                .add(amountCommissionCredited)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.amountCommissionDebited = this.amountCommissionDebited
                .add(amountCommissionDebited)
                .setScale(2, BigDecimal.ROUND_HALF_UP);

    }
    
    public void addAmounts(final Uic301Detail detail) {

        if (sealed) {
            throw new UnsupportedOperationException(
                    "Adding data is not allowed after sealing the instance");
        }

        if(detail instanceof Uic301G5Detail) {
            addG5((Uic301G5Detail)detail);
        }
        else if(detail instanceof Uic301G4Detail) {
            addG4((Uic301G4Detail)detail);
            type = Uic301Type.G4;
        }       

    }

    private void addG5(final Uic301G5Detail detail) {
        this.grossAmountToBeCredited = this.grossAmountToBeCredited
                .add(detail.getGrossAmountToBeCreditedValue())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.grossAmountToBeDebited = this.grossAmountToBeDebited
                .add(detail.getGrossAmountToBeDebitedValue())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.amountCommissionCredited = this.amountCommissionCredited
                .add(detail.getAmountOfCommissionToBeCreditedTheServiceProvidingRUValue())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.amountCommissionDebited = this.amountCommissionDebited
                .add(detail.getAmountOfCommissionToBeDebitedTheServiceProvidingRUValue())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }
    
    private void addG4(final Uic301G4Detail detail) {
        this.grossAmountToBeCredited = this.grossAmountToBeCredited
                .add(detail.getGrossAmountToBeCreditedTheServiceProvidingRUValue())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.grossAmountToBeDebited = this.grossAmountToBeDebited
                .add(detail.getGrossAmountToBeDebitedTheServiceProvidingRUValue())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.amountCommissionCredited = this.amountCommissionCredited
                .add(detail.getAmountOfCommissionToBeCreditedTheServiceProvidingRUValue())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        this.amountCommissionDebited = this.amountCommissionDebited
                .add(detail.getAmountOfCommissionToBeDebitedTheServiceProvidingRUValue())
                .setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Returns the Gross amount to be credited.
     * 
     * @return Gross amount to be credited.
     */
    public BigDecimal getGrossAmountToBeCredited() {
        return grossAmountToBeCredited;
    }

    /**
     * Returns the gross amount to be debited.
     * 
     * @return Gross amount to be debited.
     */
    public BigDecimal getGrossAmountToBeDebited() {
        return grossAmountToBeDebited;
    }

    /**
     * Returns the amount commission credited.
     * 
     * @return Amount commission credited.
     */
    public BigDecimal getAmountCommissionCredited() {
        return amountCommissionCredited;
    }

    /**
     * Returns amount commision debited.
     * 
     * @return Amount commision debited.
     */
    public BigDecimal getAmountCommissionDebited() {
        return amountCommissionDebited;
    }

    public NetBalanceType getNetBalanceType() {
        return netBalanceType;
    }

    public BigDecimal getNetBalanceAmount() {
        return netBalanceAmount;
    }

    /**
     * Determines if the instance is sealed. Any change made if the object is
     * sealed will lead to an exception.
     * 
     * @return If the instance is sealed <code>true</code>, else
     *         <code>false</code>.
     */
    public boolean isSealed() {
        return sealed;
    }

    /**
     * Seals the instance and calculates {@link #getNetBalanceAmount()} and
     * {@link #getNetBalanceType()}. Any change made after calling this method
     * will lead to an exception.
     */
    public void seal() {

        if(type == Uic301Type.G4) {
            balanceG4();
        } else {
            balanceG5();
        }
        if (netBalanceAmount.signum() == 1) {
            netBalanceType = NetBalanceType.CREDIT;
        } else if (netBalanceAmount.signum() == -1) {
            netBalanceType = NetBalanceType.DEBIT;
            netBalanceAmount = netBalanceAmount.multiply(BigDecimal.valueOf(-1))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        } else {
            netBalanceType = NetBalanceType.NONE;
        }
        sealed = true;
    }

    private void balanceG4() {
        final BigDecimal grossAmount = grossAmountToBeDebited
                .subtract(grossAmountToBeCredited)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        final BigDecimal amountCommission = amountCommissionDebited
                .subtract(amountCommissionCredited)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        netBalanceAmount = grossAmount.add(amountCommission).setScale(2,
                BigDecimal.ROUND_HALF_UP);
        
    }

    private void balanceG5() {
        final BigDecimal grossAmount = grossAmountToBeCredited
                .subtract(grossAmountToBeDebited)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        final BigDecimal amountCommission = amountCommissionCredited
                .subtract(amountCommissionDebited)
                .setScale(2, BigDecimal.ROUND_HALF_UP);
        netBalanceAmount = grossAmount.add(amountCommission).setScale(2,
                BigDecimal.ROUND_HALF_UP);
 
    }

}
