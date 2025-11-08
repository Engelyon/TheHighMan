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
    //The only thing TURN_BASED controls is the color of the number on the power icon.
    //Turn based powers are white, non-turn based powers are red or green depending on if their amount is positive or negative.
    //For a power to actually decrease/go away on its own they do it themselves.
    //Look at powers that do this like VulnerablePower and DoubleTapPower.

    public ChapadoPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        // Aplica stacks extras se o jogador tiver Seda
        AbstractCreature player = AbstractDungeon.player;
        if (player != null && player.hasPower(SedaPower.POWER_ID)) {
            int extra = player.getPower(SedaPower.POWER_ID).amount;
            this.amount += extra;
        }
        updateDescription();
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        // Reduz o dano causado em 5% por stack, apenas para dano NORMAL
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
            AbstractDungeon.actionManager.addToBottom(
                    new RemoveSpecificPowerAction(owner, owner, this.ID)
            );
        } else {
            updateDescription();
        }
    }

    public void updateDescription() {
        float reducao = 5f * amount;
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + reducao + DESCRIPTIONS[2];
    }
}
