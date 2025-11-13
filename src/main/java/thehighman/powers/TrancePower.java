package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
        if (Objects.equals(power.ID, LaricaPower.POWER_ID)){
            AbstractPlayer p = AbstractDungeon.player;
            if (!p.hand.isEmpty()){
            addToBot(new com.megacrit.cardcrawl.actions.common.ExhaustAction(p, p, 1, false));
            addToBot(new ApplyPowerAction(p,p, new SedaPower(p, p, 1), 1));
            }
        }
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}