package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.Larica;
import thehighman.util.CardStats;

public class PizzaAtomica extends BaseCard {
    public static final String ID = makeID("PizzaAtomica");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.UNCOMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int HIT_DAMAGE = 3;
    private static final int UPG_HIT_DAMAGE = 2;
    private static final int BONUS_DAMAGE = 7;

    public PizzaAtomica() {
        super(ID, info);
        setDamage(HIT_DAMAGE, UPG_HIT_DAMAGE);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Dois golpes de 3 de dano
        for (int i = 0; i < 2; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }

        // Se o inimigo tiver Larica, causa 7 de dano adicional
        if (m.hasPower(Larica.POWER_ID)) {
            addToBot(new DamageAction(m, new DamageInfo(p, BONUS_DAMAGE, DamageInfo.DamageType.NORMAL),
                    AbstractGameAction.AttackEffect.FIRE));
        }
    }
}