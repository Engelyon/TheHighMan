package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.Objects;

import static thehighman.InimigosDoSpire.makeID;

public class CompreensaoElevadaPower extends BasePower {
    public static final String POWER_ID = makeID("CompreensaoElevadaPower");

    private static boolean isDuplicating = false;

    public CompreensaoElevadaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    // Este é o método que você precisa substituir
    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (isDuplicating) {
            return;
        }
        if (source == this.owner && target != this.owner && Objects.equals(power.ID, ChapadoPower.POWER_ID)) {
            this.flash();
            isDuplicating = true;
            addToTop(new AbstractGameAction() {
                @Override
                public void update() {
                    isDuplicating = false;
                    this.isDone = true;
                }
            });
            addToTop(new ApplyPowerAction(target, source, new ChapadoPower(target, power.amount)));
        }
    }
}