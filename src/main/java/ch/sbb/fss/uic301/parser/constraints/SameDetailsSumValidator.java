package ch.sbb.fss.uic301.parser.constraints;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import ch.sbb.fss.uic301.parser.CalculatedDetailAmounts;
import ch.sbb.fss.uic301.parser.Uic301Document;
import ch.sbb.fss.uic301.parser.Uic301Total;

/**
 * Ensures that the calculated sums of the detail section and the totals are
 * identical.
 */
public class SameDetailsSumValidator
        implements ConstraintValidator<SameDetailSums, Uic301Document> {

    @Override
    public boolean isValid(final Uic301Document doc,
            final ConstraintValidatorContext context) {

        if (doc == null) {
            throw new IllegalStateException(
                    "The validated document cannot be null");
        }

        final StringBuilder err = new StringBuilder();

        final List<Uic301Total> totals = doc.getTotals().getList();
        for (final Uic301Total total : totals) {
            final CalculatedDetailAmounts calculated = doc.getDetails()
                    .getAmounts().get(total.getStatementCurrencyPeriod());

            if (calculated == null) {
                final String msg = MessageFormat.format(
                        "Total has statement currency period {0} that was not found in any detail",
                        total.getGrossCredit());
                if (err.length() > 0) {
                    err.append(", ");
                }
                err.append(msg);
                break;
            }

            if (!Objects.equals(total.getGrossCreditValue(),
                    calculated.getGrossAmountToBeCredited())) {
                final String msg = MessageFormat.format(
                        "Gross credit mismatch: total={0}, sum details={1}",
                        total.getGrossCredit(),
                        calculated.getGrossAmountToBeCredited());
                if (err.length() > 0) {
                    err.append(", ");
                }
                err.append(msg);
            }

            if (!Objects.equals(total.getGrossDebitValue(),
                    calculated.getGrossAmountToBeDebited())) {
                final String msg = MessageFormat.format(
                        "Gross debit mismatch: total={0}, sum details={1}",
                        total.getGrossDebit(),
                        calculated.getGrossAmountToBeDebited());
                if (err.length() > 0) {
                    err.append(", ");
                }
                err.append(msg);
            }

            if (!Objects.equals(total.getAmountCommissionCreditedValue(),
                    calculated.getAmountCommissionCredited())) {
                final String msg = MessageFormat.format(
                        "Amount commission credited mismatch: total={0}, sum details={1}",
                        total.getAmountCommissionCreditedValue(),
                        calculated.getAmountCommissionCredited());
                if (err.length() > 0) {
                    err.append(", ");
                }
                err.append(msg);
            }

            if (!Objects.equals(total.getAmountCommissionDebitedValue(),
                    calculated.getAmountCommissionDebited())) {
                final String msg = MessageFormat.format(
                        "Amount commission debited mismatch: total={0}, sum details={1}",
                        total.getAmountCommissionDebitedValue(),
                        calculated.getAmountCommissionDebited());
                if (err.length() > 0) {
                    err.append(", ");
                }
                err.append(msg);
            }

            if (!Objects.equals(total.getNetBalanceAmountValue(),
                    calculated.getNetBalanceAmount())) {
                final String msg = MessageFormat.format(
                        "Net balance amount mismatch: total={0}, sum details={1}",
                        total.getNetBalanceAmountValue(),
                        calculated.getNetBalanceAmount());
                if (err.length() > 0) {
                    err.append(", ");
                }
                err.append(msg);
            }

            if (!Objects.equals(total.getDebitCreditBalanceType(),
                    calculated.getNetBalanceType())) {
                final String msg = MessageFormat.format(
                        "Net balance type mismatch: total={0}, sum details={1}",
                        total.getDebitCreditBalanceType(),
                        calculated.getNetBalanceType());
                if (err.length() > 0) {
                    err.append(", ");
                }
                err.append(msg);
            }

        }

        if (err.length() > 0) {
            final HibernateConstraintValidatorContext ctx = context
                    .unwrap(HibernateConstraintValidatorContext.class);
            ctx.addMessageParameter(SameDetailSums.MSG_KEY, err.toString());
        }

        return err.length() == 0;
    }

}
