package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static thehighman.InimigosDoSpire.makeID;

public class HyparPower extends BasePower {
    public static final String POWER_ID = makeID("HyparPower");

    public HyparPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public HyparPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, -1);
    }

    @Override
    public void onExhaust(AbstractCard card) {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new EnergizedPower(owner, 1), 1));
    }
}