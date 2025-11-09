package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static thehighman.InimigosDoSpire.makeID;

public class VisaoDistorcidaPower extends BasePower {
    public static final String POWER_ID = makeID("VisaoDistorcidaPower");

    public VisaoDistorcidaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, true, owner, source, amount);
    }

    public VisaoDistorcidaPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, 1);
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new DrawCardAction(2));
        addToBot(new GainEnergyAction(1));
        this.amount = 0;
    }
}