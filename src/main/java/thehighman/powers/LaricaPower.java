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
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(owner, owner, new StrengthPower(owner, 2 * amount), 2 * amount)
        );
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
        if (damageType == DamageInfo.DamageType.NORMAL) {
            return damage + (3 * amount);
        }
        return damage;
    }

    @Override
    public void onRemove() {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(owner, owner, new StrengthPower(owner, -2 * amount), -2 * amount)
        );
    }

    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0].replace("!M!", Integer.toString(amount));
    }
}