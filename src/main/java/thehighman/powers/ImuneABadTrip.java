package thehighman.powers;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ImuneABadTrip extends AbstractPower {
    public static final String POWER_ID = "thehighman:ImuneABadTrip";

    public ImuneABadTrip(AbstractCreature owner) {
        this.name = "Imune à Bad Trip";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = -1;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = "Você não recebe mais dano de Bad Trip neste combate.";
    }
}