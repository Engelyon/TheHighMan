package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehighman.powers.Erva;

public class EaPotenciaPower extends AbstractPower {
    public static final String POWER_ID = "thehighman:EaPotenciaPower";

    public EaPotenciaPower(AbstractCreature owner) {
        this.name = "É a Potência";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = -1;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new ApplyPowerAction(owner, owner, new Erva(owner, owner, 1), 1));
    }

    @Override
    public void updateDescription() {
        this.description = "No início de cada turno, ganhe 1 de Erva.";
    }
}