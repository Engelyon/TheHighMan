package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class Marmita extends BaseCard {
    public static final String ID = makeID("Marmita");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.BASIC,
            CardTarget.SELF,
            2
    );

    private static final int COMIDO_AMOUNT = 2;
    private static final int UPG_COMIDO = 1;
    private static final int BLOCK = 9;
    private static final int BLOCK_UPG = 3;

    public Marmita() {
        super(ID, info);
        setMagic(COMIDO_AMOUNT, UPG_COMIDO);
        setBlock(BLOCK);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, this.magicNumber), this.magicNumber));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(BLOCK_UPG);
            upgradeMagicNumber(UPG_COMIDO);
            initializeDescription();
        }
    }
}