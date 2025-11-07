package thehighman.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

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
    private static final int UPG_DAMAGE = 4;
    private static final int REQUIRED_BAD_TRIPS = 3;
    private static final int DRAW_AMOUNT = 3;

    public LoopMental() {
        super(ID, info);
        setDamage(DAMAGE, UPG_DAMAGE);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Causa dano ao inimigo
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL),
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        // Conta quantas Bad Trips existem no deck
        int badTrips = 0;
        for (AbstractCard c : p.drawPile.group) {
            if (c.cardID.equals("thehighman:BadTrip")) {
                badTrips++;
            }
        }
        for (AbstractCard c : p.discardPile.group) {
            if (c.cardID.equals("thehighman:BadTrip")) {
                badTrips++;
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c.cardID.equals("thehighman:BadTrip")) {
                badTrips++;
            }
        }

        // Se tiver 3 ou mais, compra 3 cartas
        if (badTrips >= REQUIRED_BAD_TRIPS) {
            addToBot(new DrawCardAction(p, DRAW_AMOUNT));
        }
    }
}