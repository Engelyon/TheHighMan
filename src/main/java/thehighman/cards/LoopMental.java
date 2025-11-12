package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

import java.util.Objects;

public class LoopMental extends BaseCard {
    public static final String ID = makeID("LoopMental");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.ATTACK,
            CardRarity.RARE,
            CardTarget.ENEMY,
            1
    );

    private static final int DAMAGE = 10;
    private static final int DRAW = 2;// up: compra +3

    public LoopMental() {
        super(ID, info);
        setDamage(DAMAGE,3);
        setMagic(DRAW,1);
        this.keywords.add("bad trip");
        this.cardsToPreview = new BadTrip();
        initializeDescription();
    }

   /* @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DrawCardAction(p, magicNumber));
    }*/

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DrawCardAction(1));
        if (Objects.equals(p.hand.getTopCard().cardID, BadTrip.ID)) {;
            addToBot(new DrawCardAction(p, 1));
        }
        addToBot(new DrawCardAction(1));
        if (Objects.equals(p.hand.getTopCard().cardID, BadTrip.ID)) {;
            addToBot(new DrawCardAction(p, 1));
        }
        if(upgraded){
            addToBot(new DrawCardAction(1));
            if (Objects.equals(p.hand.getTopCard().cardID, BadTrip.ID)) {;
                addToBot(new DrawCardAction(p, 1));
            }
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}