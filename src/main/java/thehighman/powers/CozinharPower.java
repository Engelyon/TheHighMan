package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.DexterityPower;

import static thehighman.InimigosDoSpire.makeID;

public class CozinharPower extends BasePower {
    public static final String POWER_ID = makeID("CozinharPower");

    public CozinharPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public CozinharPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, -1);
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            flash();
            addToBot(new ApplyPowerAction(owner, owner, new DexterityPower(owner, 1), 1));
        }
        return damageAmount;
    }
}