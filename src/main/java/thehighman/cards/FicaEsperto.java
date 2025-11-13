package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.util.CardStats;

public class FicaEsperto extends BaseCard {
    public static final String ID = makeID("FicaEsperto");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 8;
    private static final int DRAW_CARD = 1;
    private static final int DRAW_CARD_UPG = 1;
    public FicaEsperto() {
        super(ID, info);
        setDamage(DAMAGE);
        setMagic(DRAW_CARD, DRAW_CARD_UPG);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if (m.hasPower(ChapadoPower.POWER_ID)) {
            addToBot(new DrawCardAction(p, this.magicNumber));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(DRAW_CARD_UPG);
            initializeDescription();
        }
    }

}