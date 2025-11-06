package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static thehighman.InimigosDoSpire.makeID;

public class Seda extends BasePower {
    public static final String POWER_ID = makeID("Seda");
    private static final int DEFAULT_AMOUNT = 1;

    public Seda(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public Seda(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, DEFAULT_AMOUNT);
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage + amount;
        }
        return damage;
    }
    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.keywords != null && card.keywords.contains("chapado")) {
            AbstractMonster target = action.target instanceof AbstractMonster ? (AbstractMonster) action.target : null;
            if (target != null && amount > 0) {
                int bonus = Math.max(1, amount / 2); // garante pelo menos 1
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(target, owner, new Chapado(target, bonus), bonus)
                );
            }
        }
    }
}