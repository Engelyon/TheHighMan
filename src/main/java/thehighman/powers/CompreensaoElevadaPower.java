package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static thehighman.InimigosDoSpire.makeID;

public class CompreensaoElevadaPower extends BasePower {
    public static final String POWER_ID = makeID("CompreensaoElevadaPower");

    public CompreensaoElevadaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public CompreensaoElevadaPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, -1);
    }


    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID.equals(makeID("Chapado")) && source == owner && target != null && target != owner) {
            if (AbstractDungeon.cardRandomRng.randomBoolean(0.5f)) {
                flash();
                addToBot(new ApplyPowerAction(target, owner, new ChapadoPower(target, 1), 1));
            }
        }
    }
}