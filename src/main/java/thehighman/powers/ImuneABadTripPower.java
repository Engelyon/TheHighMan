package thehighman.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import static thehighman.InimigosDoSpire.makeID;

public class ImuneABadTripPower extends BasePower {
    public static final String POWER_ID = makeID("ImuneABadTrip");

    public ImuneABadTripPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public ImuneABadTripPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, -1);
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}