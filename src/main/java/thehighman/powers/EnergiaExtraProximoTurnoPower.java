package thehighman.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;

public class EnergiaExtraProximoTurnoPower extends AbstractPower {
    public static final String POWER_ID = "energiaExtraProximoTurno";

    private final int energyGain;

    public EnergiaExtraProximoTurnoPower(AbstractCreature owner, int amount) {
        this.name = "Energia Extra";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.energyGain = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        flash();
        addToBot(new GainEnergyAction(energyGain));
        this.amount = 0;
    }

    @Override
    public void updateDescription() {
        this.description = "No início do próximo turno, ganhe " + energyGain + " de energia.";
    }
}