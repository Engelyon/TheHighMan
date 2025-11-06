package thehighman.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import thehighman.cards.BaseCard;
import thehighman.character.TheHighman;

import static thehighman.InimigosDoSpire.makeID;

public class Seda extends BasePower{
    public static final String POWER_ID = makeID("Seda");
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public Seda(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount); // amount = intensidade da Seda
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && AbstractDungeon.player != null) {
            if (AbstractDungeon.player.cardInUse instanceof BaseCard) {
                BaseCard card = (BaseCard) AbstractDungeon.player.cardInUse;
                if (card.color == TheHighman.Meta.CARD_COLOR) {
                    return damage * (1 + 0.1f * this.amount); // +1 de dano por stack
                }
            }
        }
        return damage;
    }

    @Override
    public void updateDescription() {
        this.description = "Aumenta o dano de ataques da sua classe em " + amount + ".";
    }


}
