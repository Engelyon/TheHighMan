package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;

public class HyparPower extends AbstractPower {
    public static final String POWER_ID = "thehighman:HyparPower";

    public HyparPower(AbstractCreature owner) {
        this.name = "Hypar";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = -1;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = "Sempre que você exaurir uma carta, ganhe 1 de energia no próximo turno.";
    }

    @Override
    public void onExhaust(AbstractCard card) {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new EnergizedPower(owner, 1), 1));
    }
}