package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CompreensaoElevadaPower extends AbstractPower {
    public static final String POWER_ID = "thehighman:CompreensaoElevadaPower";

    public CompreensaoElevadaPower(AbstractCreature owner) {
        this.name = "Compreensão Elevada";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = -1;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = "Ao aplicar Chapado, há 50% de chance de aplicar 1 stack adicional.";
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals("thehighman:Chapado") && source == owner && target != null && target != owner) {
            if (AbstractDungeon.cardRandomRng.randomBoolean(0.5f)) {
                flash();
                addToBot(new ApplyPowerAction(target, owner, new ChapadoPower(target, 1), 1));
            }
        }
    }
}