package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BongExtraPower extends AbstractPower {
    public static final String POWER_ID = "thehighman:BongExtraPower";

    private int pendingBlock = 0;

    public BongExtraPower(AbstractCreature owner) {
        this.name = "Bong Extra";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = -1;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = "Sempre que um inimigo ganhar Larica, compre 1 carta e ganhe 5 de Bloqueio no final do turno.";
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