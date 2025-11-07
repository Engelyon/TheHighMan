package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Collections;
import java.util.List;

public class TrancePower extends AbstractPower {
    public static final String POWER_ID = "thehighman:TrancePower";

    public TrancePower(AbstractCreature owner) {
        this.name = "Trance";
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.amount = 1;
        updateDescription();
    }

    @Override
    public void updateDescription() {
        this.description = "Sempre que você ganhar Larica neste turno, exaure 1 carta da sua mão e ganhe 1 de Seda.";
    }

    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target == owner && power.ID.equals("thehighman:Larica")) {
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
        this.amount = 0;
    }
}