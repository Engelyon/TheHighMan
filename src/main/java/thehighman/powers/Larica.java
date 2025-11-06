package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.cards.DamageInfo;

import static thehighman.InimigosDoSpire.makeID;

public class Larica extends BasePower {
    public static final String POWER_ID = makeID("Larica");
    private static final BasePower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public Larica(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(owner, owner, new StrengthPower(owner, 2), 2)
        );
    }


    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            return damage + 5;
        }
        return damage;
    }

    @Override
    public void updateDescription() {
        this.description = "Recebe +2 de força e leva +5 de dano de ataques. Aplicado " + amount + " vez(es).";
    }
}