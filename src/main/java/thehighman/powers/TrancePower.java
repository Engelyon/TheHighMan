package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Collections;
import java.util.List;

import static thehighman.InimigosDoSpire.makeID;

public class TrancePower extends BasePower {
    public static final String POWER_ID = makeID("TrancePower");

    public TrancePower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, true, owner, source, amount);
    }

    public TrancePower(AbstractCreature owner, AbstractCreature source) {
        this(owner, source, 1);
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target == owner && power.ID.equals(makeID("Larica"))) {
            List<AbstractCard> hand = AbstractDungeon.player.hand.group;
            if (!hand.isEmpty()) {
                Collections.shuffle(hand);
                AbstractCard toExhaust = hand.get(0);
                AbstractDungeon.player.hand.moveToExhaustPile(toExhaust);
                addToBot(new ApplyPowerAction(owner, owner, new SedaPower(owner, owner, 1), 1));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.amount = 0;
        }
    }
}