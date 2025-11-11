package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

import java.util.ArrayList;
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
        List<AbstractCard> hand = new ArrayList<>(p.hand.group);
        if (hand.isEmpty()) {
            return;
        }
        if (hand.size() <= EXHAUST_AMOUNT) {
            int badTripCount = 0;
            for (AbstractCard c : hand) {
                if (p.hand.contains(c)) {
                    p.hand.moveToExhaustPile(c);
                    if ("BadTrip".equals(c.cardID)) {
                        badTripCount++;
                    }
                }
            }
            if (badTripCount > 0) {
                addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));
            }
            return;
        }
        if (!upgraded) {
            List<AbstractCard> tmp = new ArrayList<>(hand);
            Collections.shuffle(tmp);
            int badTripCount = 0;
            for (int i = 0; i < EXHAUST_AMOUNT && i < tmp.size(); i++) {
                AbstractCard c = tmp.get(i);
                if (p.hand.contains(c)) {
                    p.hand.moveToExhaustPile(c);
                    if ("BadTrip".equals(c.cardID)) {
                        badTripCount++;
                    }
                }
            }
            if (badTripCount > 0) {
                addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));
            }
        } else {
            AbstractDungeon.gridSelectScreen.open(p.hand, EXHAUST_AMOUNT, "Escolha " + EXHAUST_AMOUNT + " cartas para exaurir", false);
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                        int badTripCount = 0;
                        List<AbstractCard> selected = new ArrayList<>(AbstractDungeon.gridSelectScreen.selectedCards);
                        for (AbstractCard c : selected) {
                            if (p.hand.contains(c)) {
                                p.hand.moveToExhaustPile(c);
                                if ("BadTrip".equals(c.cardID)) {
                                    badTripCount++;
                                }
                            }
                        }
                        AbstractDungeon.gridSelectScreen.selectedCards.clear();
                        if (badTripCount > 0) {
                            addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, ERVA_GAIN), ERVA_GAIN));
                        }
                        this.isDone = true;
                    } else {
                        this.isDone = false;
                    }
                }
            });
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.exhaust = false; // Remove o exhaust na versão upada
            initializeDescription();
        }
    }
}