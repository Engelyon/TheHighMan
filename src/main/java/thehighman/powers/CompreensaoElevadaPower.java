package thehighman.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static thehighman.InimigosDoSpire.makeID;

public class CompreensaoElevadaPower extends BasePower {
    public static final String POWER_ID = makeID("CompreensaoElevadaPower");

    public CompreensaoElevadaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }


    @Override
    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (Objects.equals(power.ID, ChapadoPower.POWER_ID)){
            addToBot(new ApplyPowerAction(target, source, new ChapadoPower(target, this.amount), this.amount));
        }
    }
}