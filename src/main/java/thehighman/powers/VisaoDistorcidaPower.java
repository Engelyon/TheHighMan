package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class VisaoDistorcidaPower extends AbstractPower {
    public static final String POWER_ID = "thehighman:VisaoDistorcidaPower";

    public VisaoDistorcidaPower(AbstractCreature owner) {
        this.name = "Visão Distorcida";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.amount = 1;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new DrawCardAction(2));
        addToBot(new GainEnergyAction(1));
        this.amount = 0;
    }

    @Override
    public void updateDescription() {
        this.description = "No início do próximo turno, compre 2 cartas e ganhe 1 de energia.";
    }
}