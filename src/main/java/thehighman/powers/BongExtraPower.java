package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thehighman.InimigosDoSpire.makeID;

public class BongExtraPower extends BasePower {
    public static final String POWER_ID = makeID("BongExtraPower");
    private static final int DEFAULT_AMOUNT = -1;

    private int pendingBlock = 0;

    public BongExtraPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public BongExtraPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, DEFAULT_AMOUNT);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target != owner && power.ID.equals("thehighman:Larica")) {
            flash();
            addToBot(new DrawCardAction(1));
            pendingBlock += 5;
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer && pendingBlock > 0) {
            addToBot(new GainBlockAction(owner, pendingBlock));
            pendingBlock = 0;
        }
    }
}