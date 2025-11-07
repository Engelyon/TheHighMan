package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import thehighman.character.TheHighman;
import thehighman.powers.Larica;
import thehighman.util.CardStats;

public class BadTrip extends BaseCard {
    public static final String ID = makeID("BadTrip");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.CURSE,
            CardRarity.CURSE,
            CardTarget.NONE,
            -2 // não pode ser jogada
    );

    public BadTrip() {
        super(ID, info);
        this.exhaust = false;
        this.dontTriggerOnUseCard = true;
        initializeDescription();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hand.contains(this)) {
            // Perde 1 de vida
            addToBot(new LoseHPAction(p, p, 1));

            // Ativa Maconha Medicinal se estiver presente
            if (p.hasRelic("thehighman:MaconhaMedicinal")) {
                ((thehighman.relics.MaconhaMedicinal) p.getRelic("thehighman:MaconhaMedicinal")).onBadTripDano();
            }

            // Ganha 1 de Larica
            addToBot(new ApplyPowerAction(p, p, new Larica(p, 1), 1));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Não faz nada — carta não jogável
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }
}