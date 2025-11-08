package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

import java.util.Collections;
import java.util.List;

public class Amnesia extends BaseCard {
    public static final String ID = makeID("Amnesia");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            1
    );

    private static final int EXHAUST_AMOUNT = 2;
    private static final int ERVA_GAIN = 2;

    public Amnesia() {
        super(ID, info);
        this.exhaust = true;
        this.keywords.add("erva");
        this.keywords.add("bad trip");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        List<AbstractCard> hand = p.hand.group;
        if (hand.size() < EXHAUST_AMOUNT) return;

        Collections.shuffle(hand);
        int badTripCount = 0;

        for (int i = 0; i < EXHAUST_AMOUNT; i++) {
            AbstractCard c = hand.get(i);
            p.hand.moveToExhaustPile(c);
            if (c.cardID.equals("thehighman:BadTrip")) {
                badTripCount++;
            }
        }

        if (badTripCount > 0) {
            addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false; // Remove o exaust da carta
            initializeDescription();
        }
    }
}