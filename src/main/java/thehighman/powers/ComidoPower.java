package thehighman.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.localization.PowerStrings;
import thehighman.relics.SacoDeLanchesSemFundo;

import static thehighman.InimigosDoSpire.makeID;

public class ComidoPower extends BasePower {
    public static final String POWER_ID = makeID("Comido");
    private static final PowerStrings powerStrings = com.megacrit.cardcrawl.core.CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public ComidoPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public ComidoPower(AbstractCreature owner, int amount) {
        this(owner, owner, amount);
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }


    public static boolean consumirComido(AbstractCreature target) {
        AbstractPower comido = target.getPower(POWER_ID);
        if (comido != null && comido.amount > 0) {
            comido.amount--;
            comido.updateDescription();

            if (target.isPlayer && AbstractDungeon.player.hasRelic("SacoDeLanchesSemFundo")) {
                ((SacoDeLanchesSemFundo) AbstractDungeon.player.getRelic("SacoDeLanchesSemFundo")).onComidoConsumido();
            }

            if (comido.amount == 0) {
                target.powers.remove(comido);
            }
            return true;
        }
        return false;
    }
}