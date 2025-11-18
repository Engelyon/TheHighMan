package thehighman.unused_cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehighman.powers.BasePower;
import thehighman.powers.LaricaPower;

import static thehighman.InimigosDoSpire.makeID;

public class BongExtraPower extends BasePower {
    public static final String POWER_ID = makeID("BongExtraPower");
    private static final int DEFAULT_AMOUNT = -1;
    private static final int DEFAULT_BLOCK_PER_TRIGGER = 5;

    private int pendingBlock = 0;

    // amount aqui representa blockPerTrigger quando > 0
    public BongExtraPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public BongExtraPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, DEFAULT_AMOUNT);
    }

    @Override
    public void updateDescription() {
        int blockPerTrigger = getBlockPerTrigger();
        description = DESCRIPTIONS[0] + blockPerTrigger + DESCRIPTIONS[1];
    }

    private int getBlockPerTrigger() {
        if (this.amount > 0) {
            return this.amount;
        }
        return DEFAULT_BLOCK_PER_TRIGGER;
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target != owner && power.ID.equals(LaricaPower.POWER_ID)) {
            flash();
            addToBot(new DrawCardAction(1));
            pendingBlock += getBlockPerTrigger();
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