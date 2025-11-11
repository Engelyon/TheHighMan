package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thehighman.InimigosDoSpire.makeID;

public class ChapadoPower extends BasePower{
    public static final String POWER_ID = makeID("ChapadoPower");
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = false;

    public ChapadoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        AbstractCreature player = AbstractDungeon.player;
        if (player != null && player.hasPower(SedaPower.POWER_ID)) {
            int extra = player.getPower(SedaPower.POWER_ID).amount;
            this.amount += extra;
        }
        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * (1f - 0.05f * this.amount);
        }
        return damage;
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;

        AbstractCreature player = AbstractDungeon.player;
        if (player != null && player.hasPower(SedaPower.POWER_ID)) {
            int extra = player.getPower(SedaPower.POWER_ID).amount;
            stackAmount += extra;
        }

        this.amount += stackAmount;

        if (this.amount >= 20) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(owner, owner, new LaricaPower(owner, 1), 1)
            );
            this.amount = 5;
        }

        updateDescription();
    }
    @Override
    public void atEndOfRound() {
        if (this.amount > 0) {
            this.amount--;
            this.updateDescription();
            if (this.amount <= 0) {
                AbstractDungeon.actionManager.addToBottom(
                        new RemoveSpecificPowerAction(this.owner, this.owner, this.ID)
                );
            }
        }
    }
    public void updateDescription() {
        float reducao = 5f * amount;
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + reducao + DESCRIPTIONS[2];
    }
}
