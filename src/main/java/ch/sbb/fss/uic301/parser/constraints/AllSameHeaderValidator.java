package ch.sbb.fss.uic301.parser.constraints;

import java.text.MessageFormat;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.sbb.fss.uic301.parser.Uic301Detail;
import ch.sbb.fss.uic301.parser.Uic301Document;
import ch.sbb.fss.uic301.parser.Uic301Header;
import ch.sbb.fss.uic301.parser.Uic301Total;

/**
 * Ensures that all detail and total entries of the document have the same
 * information as the document header.
 */
public class AllSameHeaderValidator
        implements ConstraintValidator<AllSameHeader, Uic301Document> {

    private static final Logger LOG = LoggerFactory
            .getLogger(AllSameHeaderValidator.class);

    @Override
    public boolean isValid(final Uic301Document doc,
            final ConstraintValidatorContext context) {

        if (doc == null) {
            throw new IllegalStateException(
                    "The validated document cannot be null");
        }

        final Uic301Header header = doc.getHeader();
        final String headerIdSub = header.getIdentifier().substring(0, 3);

        String err = null;
        for (final Uic301Detail detail : doc.getDetails().getList()) {

            final String detailIdSub = detail.getIdentifier().substring(0, 3);
            if (!Objects.equals(headerIdSub, detailIdSub)) {
                final String msg = MessageFormat.format(
                        "Identifier mismatch: line #{0}, header=''{1}'', detail=''{2}''",
                        detail.getParsedLineNo(), header.getIdentifier(),
                        detail.getIdentifier());
                err = warn(err, msg);
            }
            if (!Objects.equals(header.getRailUnionCompiling(),
                    detail.getRailUnionCompiling())) {
                final String msg = MessageFormat.format(
                        "Rail Union Compiling mismatch: line #{0}, header=''{1}'', detail=''{2}''",
                        detail.getParsedLineNo(),
                        header.getRailUnionCompiling(),
                        detail.getRailUnionCompiling());
                err = warn(err, msg);
            }
            if (!Objects.equals(header.getRailUnionReceiving(),
                    detail.getRailUnionReceiving())) {
                final String msg = MessageFormat.format(
                        "Rail Union Receiving mismatch: line #{0}, header=''{1}'', detail=''{2}''",
                        detail.getParsedLineNo(),
                        header.getRailUnionReceiving(),
                        detail.getRailUnionReceiving());
                err = warn(err, msg);
            }
            if (!Objects.equals(header.getPeriod(), detail.getPeriod())) {
                final String msg = MessageFormat.format(
                        "Period mismatch: line #{0}, header=''{1}'', detail=''{2}''",
                        detail.getParsedLineNo(), header.getPeriod(),
                        detail.getPeriod());
                err = warn(err, msg);
            }

        }

        for (Uic301Total total : doc.getTotals().getList()) {

            final String detailIdSub = total.getIdentifier().substring(0, 3);
            if (!Objects.equals(headerIdSub, detailIdSub)) {
                final String msg = MessageFormat.format(
                        "Identifier mismatch: line #{0}, header=''{1}'', total=''{2}''",
                        total.getParsedLineNo(), header.getIdentifier(),
                        total.getIdentifier());
                err = warn(err, msg);
            }
            if (!Objects.equals(header.getRailUnionCompiling(),
                    total.getRailUnionCompiling())) {
                final String msg = MessageFormat.format(
                        "Rail Union Compiling mismatch: line #{0}, header=''{1}'', total=''{2}''",
                        total.getParsedLineNo(), header.getRailUnionCompiling(),
                        total.getRailUnionCompiling());
                err = warn(err, msg);
            }
            if (!Objects.equals(header.getRailUnionReceiving(),
                    total.getRailUnionReceiving())) {
                final String msg = MessageFormat.format(
                        "Rail Union Receiving mismatch: line #{0}, header=''{1}'', total=''{2}''",
                        total.getParsedLineNo(), header.getRailUnionReceiving(),
                        total.getRailUnionReceiving());
                err = warn(err, msg);
            }
            if (!Objects.equals(header.getPeriod(), total.getPeriod())) {
                final String msg = MessageFormat.format(
                        "Period mismatch: line #{0}, header=''{1}'', total=''{2}''",
                        total.getParsedLineNo(), header.getPeriod(),
                        total.getPeriod());
                err = warn(err, msg);
            }

        }

        if (err != null) {
            final HibernateConstraintValidatorContext ctx = context
                    .unwrap(HibernateConstraintValidatorContext.class);
            // ctx.disableDefaultConstraintViolation();
            ctx.addMessageParameter(AllSameHeader.MSG_KEY, err);
            // ctx.buildConstraintViolationWithTemplate(AllSameHeader.MSG_KEY);
        }

        return err == null;
    }

    private static String warn(final String err, final String msg) {
        LOG.warn(msg);
        if (err == null) {
            return msg;
        }
        return err;
    }

}
