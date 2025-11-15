package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.powers.ImuneABadTripPower;
import thehighman.util.CardStats;

public class AlimentoDivino extends BaseCard {
    public static final String ID = makeID("AlimentoDivino");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.RARE,
            CardTarget.SELF,
            2
    );

    private static final int COMIDO_GAIN = 5;
    private static final int COMIDO_MAX = 10;
    private static final int BLOCK = 10;
    private static final int BLOCK_UPG = 5;

    public AlimentoDivino() {
        super(ID, info);
        setBlock(BLOCK,BLOCK_UPG);
        this.cardsToPreview = new BadTrip();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, COMIDO_GAIN), COMIDO_GAIN));
        if (p.hasPower(ComidoPower.POWER_ID) && p.getPower(ComidoPower.POWER_ID).amount >= COMIDO_MAX) {
            addToBot(new ApplyPowerAction(p, p, new ImuneABadTripPower(p,p), 1));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            this.upgradeBaseCost(1);
            initializeDescription();
        }
    }
}