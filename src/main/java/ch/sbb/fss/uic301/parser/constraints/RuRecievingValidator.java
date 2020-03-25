package ch.sbb.fss.uic301.parser.constraints;

import java.text.MessageFormat;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import ch.sbb.fss.uic301.parser.Uic301Document;

public class RuRecievingValidator  implements ConstraintValidator<SameDetailSums, Uic301Document> {

    @Override
    public boolean isValid(Uic301Document value, ConstraintValidatorContext context) {
        String recievingRu = value.getHeader().getRailUnionReceiving();
        
        String defaultValue = Uic301Document.class.getAnnotation(RuRecieving.class).sbb();
        
        if(!recievingRu.endsWith(defaultValue)) {
            
            final String msg = MessageFormat.format(
                    "The receving RU is {0}. Only documents for SBB {1} can be processed.",
                    recievingRu, "**"+defaultValue);
            
            final HibernateConstraintValidatorContext ctx = context
                    .unwrap(HibernateConstraintValidatorContext.class);
            ctx.addMessageParameter(RuRecieving.MSG_KEY, msg);
            return false;
        }    
        return true;
        
    }

}
