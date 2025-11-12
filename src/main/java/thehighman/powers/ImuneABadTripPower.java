package thehighman.powers;

import basemod.helpers.dynamicvariables.DamageVariable;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.DamageHooks;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import static thehighman.InimigosDoSpire.makeID;

public class ImuneABadTripPower extends BasePower {
    public static final String POWER_ID = makeID("ImuneABadTripPower");

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