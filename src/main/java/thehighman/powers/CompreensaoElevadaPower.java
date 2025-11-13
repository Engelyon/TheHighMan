package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static thehighman.InimigosDoSpire.makeID;

public class CompreensaoElevadaPower extends BasePower {
    public static final String POWER_ID = makeID("CompreensaoElevadaPower");

    public CompreensaoElevadaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (!Objects.equals(source.id, CompreensaoElevadaPower.POWER_ID)) {
            if (Objects.equals(power.ID, ChapadoPower.POWER_ID)) {
                addToBot(new ApplyPowerAction(target, source, new ChapadoPower(target, power.amount)));
            }
        }
    }
}