package thehighman.cards;

import com.jcraft.jorbis.Block;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.ErvaPower;
import thehighman.util.CardStats;

public class CheirinhoBom extends BaseCard {
    public static final String ID = makeID("CheirinhoBom");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int DEX_GAIN = 1;
    private static final int UPG_DEX = 1;
    private static final int BLOCK=5;
    private static final int BLOCK_UPG=3;

    public CheirinhoBom() {
        super(ID, info);
        setBlock(BLOCK);
        setMagic(DEX_GAIN, UPG_DEX);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));

        boolean anyHasChapado = false;
        for (AbstractMonster mon : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mon != null && !mon.isDeadOrEscaped() && mon.hasPower(ChapadoPower.POWER_ID)) {
                anyHasChapado = true;
                break;
            }
        }
        if (anyHasChapado) {
            addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, this.magicNumber), this.magicNumber));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(BLOCK_UPG);
            upgradeMagicNumber(UPG_DEX);
            initializeDescription();
        }
    }


}