package thehighman.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehighman.relics.SacoDeLanchesSemFundo;

import static thehighman.InimigosDoSpire.makeID;

public class ComidoPower extends BasePower {
    public static final String POWER_ID = makeID("ComidoPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);

    public ComidoPower(AbstractCreature owner, AbstractCreature source, int amount) {
        super(POWER_ID, PowerType.BUFF, false, owner, source, amount);
    }

    public ComidoPower(AbstractCreature owner, int amount) {
        this(owner, owner, amount);
    }
    @Override
    public void updateDescription() {
        this.description = powerStrings.DESCRIPTIONS[0].replace("!M!", Integer.toString(amount));
    }

    @Override
    public float atDamageFinalReceive(float damage, DamageInfo.DamageType damageType) {
        AbstractCreature player = AbstractDungeon.player;
        if (player != null && player.hasPower(LaricaPower.POWER_ID)) {
            int extra = player.getPower(LaricaPower.POWER_ID).amount;
            int stacksToNegate = Math.min(extra, this.amount);
            damage -= stacksToNegate * 3;
        }
        return damage;
    }

    public static boolean consumirComido(AbstractCreature target) {
        AbstractPower comido = target.getPower(POWER_ID);
        if (comido != null && comido.amount > 0) {
            comido.amount--;
            comido.updateDescription();

            if (AbstractDungeon.player.hasRelic("SacoDeLanchesSemFundo")) {
                ((SacoDeLanchesSemFundo) AbstractDungeon.player.getRelic("SacoDeLanchesSemFundo")).onComidoConsumido(AbstractDungeon.player);
            }

            if (comido.amount == 0) {
                target.powers.remove(comido);
            }
            return true;
        }
        return false;
    }

}