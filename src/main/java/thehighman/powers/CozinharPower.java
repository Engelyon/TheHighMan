package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class CozinharPower extends AbstractPower {
    public static final String POWER_ID = "thehighman:CozinharPower";

    public CozinharPower(AbstractCreature owner) {
        this.name = "Cozinhar";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.amount = -1;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = "Sempre que você ganhar Larica, ganhe 2 de Destreza.";
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target == owner && power.ID.equals("thehighman:Larica")) {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, 2), 2));
        }
    }
}