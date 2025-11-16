package thehighman.powers;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static thehighman.InimigosDoSpire.makeID;

public class NirvanaPower extends BasePower {
    public static final String POWER_ID = makeID("NirvanaPower");

    public NirvanaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public NirvanaPower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, -1);
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.exhaust && !card.purgeOnUse && !card.dontTriggerOnUseCard) {
            this.flash();
            AbstractCard copy = card.makeSameInstanceOf();
            copy.freeToPlayOnce = true;
            copy.purgeOnUse = true;
            AbstractDungeon.player.limbo.addToBottom(copy);
            copy.current_x = card.current_x;
            copy.current_y = card.current_y;
            copy.target_x = card.target_x;
            copy.target_y = card.target_y;
            AbstractMonster target = null;
            if (action.target instanceof AbstractMonster) {
                target = (AbstractMonster) action.target;
            }
            AbstractDungeon.actionManager.addToBottom(new UseCardAction(copy, target));
            AbstractDungeon.actionManager.addToBottom(new UnlimboAction(copy));
        }
    }
}