package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import static thehighman.InimigosDoSpire.makeID;

public class EaPotenciaPower extends BasePower {
    public static final String POWER_ID = makeID("EaPotenciaPower");

    public EaPotenciaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public EaPotenciaPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, -1);
    }
    @Override

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new ErvaPower(owner, 1), 1));
    }
}