package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class IdeiaDeStartup extends BaseCard {
    public static final String ID = makeID("IdeiaDeStartup");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            2
    );

    private static final int ERVA_GAIN = 5;

    public IdeiaDeStartup() {
        super(ID, info);
        this.exhaust=true;
        setMagic(ERVA_GAIN);
        setMagic(ERVA_GAIN, ERVA_GAIN + 2); // 5 → 7 de Erva com upgrade
        this.keywords.add("erva");
        this.cardsToPreview = new BadTrip();
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ErvaPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.player.energy.use(AbstractDungeon.player.energy.energy);
        addToBot(new MakeTempCardInDrawPileAction(new BadTrip(), 1, true, true));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(2);
            initializeDescription();
        }
    }
}