package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
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
    private static final int DRAW = 2;

    public LoopMental() {
        super(ID, info);
        setDamage(DAMAGE,3);
        setMagic(DRAW,1);
        this.cardsToPreview = new BadTrip();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        final boolean[] badtripEncontrada = {false};
        for (int i = 0; i < this.magicNumber; i++) {
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (p.drawPile.isEmpty()) {
                        this.isDone = true;
                        return;
                    }
                    AbstractCard topCard = p.drawPile.getNCardFromTop(0);
                    if (topCard != null && Objects.equals(topCard.cardID, BadTrip.ID)) {
                        badtripEncontrada[0] = true;
                    }
                    this.isDone = true;
                }
            });
            addToBot(new DrawCardAction(p, 1));
        }
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                if (badtripEncontrada[0]) {
                    addToTop(new DrawCardAction(p, magicNumber));
                }
                this.isDone = true;
            }
        });
    }

        @Override
    public void upgrade() {
        super.upgrade();
    }
}