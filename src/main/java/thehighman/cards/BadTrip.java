package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ImuneABadTripPower;
import thehighman.powers.LaricaPower;
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
        this.keywords.add("larica");
        this.keywords.add("maldição");
        initializeDescription();
    }

    @Override
    public void triggerOnEndOfTurnForPlayingCard() {
        AbstractPlayer p = AbstractDungeon.player;
        int dano = 1;
        if (p.hasPower(ImuneABadTripPower.POWER_ID)){
            dano = 0;
        }
        if (p.hand.contains(this)) {
            addToBot(new LoseHPAction(p, p, dano));
            if (p.hasRelic("thehighman:MaconhaMedicinal")) {
                ((thehighman.relics.MaconhaMedicinal) p.getRelic("thehighman:MaconhaMedicinal")).onBadTripDano();
            }
            addToBot(new ApplyPowerAction(p, p, new LaricaPower(p, 1), 1));
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