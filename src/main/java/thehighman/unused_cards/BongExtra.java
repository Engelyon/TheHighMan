package thehighman.unused_cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.cards.BaseCard;
import thehighman.character.TheHighman;
import thehighman.util.CardStats;

public class BongExtra extends BaseCard {
    public static final String ID = makeID("BongExtra");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.POWER,
            CardRarity.SPECIAL,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK= 5;
    private static final int BLOCK_UPG = 3;

    public BongExtra() {
        super(ID, info);
        setBlock(BLOCK, BLOCK_UPG);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new BongExtraPower(p, p, this.block), this.block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(BLOCK_UPG);
            initializeDescription();
        }
    }
}