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

    private final Set<AbstractPower> ignoreSet = new HashSet<>();

    public CompreensaoElevadaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power == null || target == null) return;
        if (ignoreSet.remove(power)) {
            return;
        }
        if (power instanceof ChapadoPower) {
            ChapadoPower toApply = new ChapadoPower(target, this.amount);
            ignoreSet.add(toApply);
            addToBot(new ApplyPowerAction(target, source, toApply, this.amount));
        }
    }
}