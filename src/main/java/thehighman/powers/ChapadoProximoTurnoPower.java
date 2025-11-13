package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thehighman.InimigosDoSpire.makeID;

public class ChapadoProximoTurnoPower extends BasePower {
    public static final String POWER_ID = makeID("ChapadoProximoTurnoPower");

    private int turnsRemaining;
    private final int amountPerTurn;

    public ChapadoProximoTurnoPower(AbstractCreature owner, int turns, int amountPerTurn) {
        super(POWER_ID, PowerType.DEBUFF, true, owner, owner, turns);
        this.turnsRemaining = turns;
        this.amountPerTurn = amountPerTurn;
        this.amount = this.turnsRemaining;
        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        addToBot(new ApplyPowerAction(owner, owner, new ChapadoPower(owner, amountPerTurn), amountPerTurn));
        this.turnsRemaining--;
        this.amount = this.turnsRemaining;
        if (this.turnsRemaining <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        } else {
            updateDescription();
        }
    }


    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amountPerTurn + DESCRIPTIONS[1] + turnsRemaining + DESCRIPTIONS[2];
    }
}