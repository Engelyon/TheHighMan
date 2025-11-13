package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.BlueCandle;
import thehighman.character.TheHighman;
import thehighman.powers.ImuneABadTripPower;
import thehighman.powers.LaricaPower;
import thehighman.relics.MaconhaMedicinal;
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
        int dano = 1;
        if (p.hasPower(ImuneABadTripPower.POWER_ID)){
            dano = 0;
        }
        if (p.hand.contains(this)) {
            addToBot(new LoseHPAction(p, p, dano));
            addToBot(new ApplyPowerAction(p, p, new LaricaPower(p, 1), 1));
        }
        if (p.hasRelic(MaconhaMedicinal.ID)){
            p.getRelic(MaconhaMedicinal.ID).flash();
            addToBot(new HealAction(p,p,2));
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new com.megacrit.cardcrawl.actions.common.LoseHPAction(p, p, 1));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (p.hasRelic(BlueCandle.ID)){
            this.exhaust=true;
            return true;
        }
        return false;
    }
}