package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
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

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        boolean badtrip = false;
        if(p.drawPile.isEmpty()){
            addToBot(new EmptyDeckShuffleAction());
        }
        for (int i = 0; i < magicNumber; i++) {
            if(p.drawPile.isEmpty()){
                addToBot(new EmptyDeckShuffleAction());
            }
            if (Objects.equals(p.drawPile.getNCardFromTop(0).cardID, BadTrip.ID)){
                badtrip = true;
            }
            addToBot(new DrawCardAction(p, 1));
        }
        if (badtrip){
            addToBot(new DrawCardAction(p, magicNumber));
        }
    }

    @Override
    public void upgrade() {
        super.upgrade();
    }
}