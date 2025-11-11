package thehighman.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static thehighman.InimigosDoSpire.makeID;

public class ChapadoProximoTurnoPower extends AbstractPower {
    public static final String POWER_ID = makeID("ChapadoProximoTurnoPower");

    private int turnsRemaining;
    private final int amountPerTurn;

    public ChapadoProximoTurnoPower(AbstractCreature owner, int turns, int amountPerTurn) {
        this.name = "Chapado Depois";
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = turns;
        this.turnsRemaining = turns;
        this.amountPerTurn = amountPerTurn;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        updateDescription();
    }

    @Override
    public void atStartOfTurnPostDraw() {
        addToBot(new ApplyPowerAction(owner, owner, new ChapadoPower(owner, amountPerTurn), amountPerTurn));
        turnsRemaining--;
        amount = turnsRemaining;
        if (turnsRemaining <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        } else {
            updateDescription();
        }
    }

    @Override
    public void updateDescription() {
        this.description = "Aplica " + amountPerTurn + " de Chapado no início dos próximos " + turnsRemaining + " turno(s).";
    }
}