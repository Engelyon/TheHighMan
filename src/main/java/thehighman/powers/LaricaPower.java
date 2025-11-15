package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.localization.PowerStrings;
import static thehighman.InimigosDoSpire.makeID;

public class LaricaPower extends BasePower {
    public static final String POWER_ID = makeID("LaricaPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private int appliedStacks = 0;

    public LaricaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, source, amount);
        this.name = powerStrings.NAME;
        this.updateDescription();
        this.img = com.megacrit.cardcrawl.helpers.ImageMaster.loadImage("images/powers/32/blur.png");
    }

    public LaricaPower(AbstractCreature owner, int amount) {
        this(owner, owner, amount);
    }

    @Override
    public void onInitialApplication() {
        if (this.amount > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(owner, owner, new StrengthPower(owner, 2 * this.amount), 2 * this.amount)
            );
            this.appliedStacks = this.amount;
        }
        this.updateDescription();
    }

    @Override
    public void stackPower(int stackAmount) {
        if (stackAmount == 0) return;
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(owner, owner, new StrengthPower(owner, 2 * stackAmount), 2 * stackAmount)
        );
        this.amount += stackAmount;
        this.appliedStacks += stackAmount;
        if (this.appliedStacks < 0) this.appliedStacks = 0;
        this.updateDescription();
    }

    @Override
    public void onRemove() {
        if (this.appliedStacks > 0) {
            AbstractDungeon.actionManager.addToBottom(
                    new ApplyPowerAction(owner, owner, new StrengthPower(owner, -2 * this.appliedStacks), -2 * this.appliedStacks)
            );
            this.appliedStacks = 0;
        }
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            return damage + (5 * amount);
        }
        return damage;
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0].replace("!M!", Integer.toString(amount * 5));
    }
}