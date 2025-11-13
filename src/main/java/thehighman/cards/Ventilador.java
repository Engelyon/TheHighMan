package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class Ventilador extends BaseCard {
    public static final String ID = makeID("Ventilador");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int CHAPADO_AMOUNT = 1;
    private static final int BLOCK_PER_HIT = 3;
    private static final int BLOCK_PER_HIT_UPG = 2;

    public Ventilador() {
        super(ID, info);
        setMagic(CHAPADO_AMOUNT);
        setBlock(BLOCK_PER_HIT, BLOCK_PER_HIT_UPG);
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int hits = 0;
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().monsters != null) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo != null && !mo.isDeadOrEscaped()) {
                    addToBot(new ApplyPowerAction(mo, p, new ChapadoPower(mo, this.magicNumber), this.magicNumber));
                    hits++;
                }
            }
        }
        if (hits > 0) {
            int totalBlock = hits * block;
            addToBot(new GainBlockAction(p, p, totalBlock));
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(BLOCK_PER_HIT_UPG);
            initializeDescription();
        }
    }

    @Override
    public void applyPowers() {
        super.applyPowers();
        int base = this.baseMagicNumber;
        int newMagic = base;
        if (AbstractDungeon.player != null && AbstractDungeon.player.hasPower(SedaPower.POWER_ID)) {
            int seda = AbstractDungeon.player.getPower(SedaPower.POWER_ID).amount;
            newMagic = base + Math.max(0, seda);
        }
        this.magicNumber = newMagic;
        this.isMagicNumberModified = (this.magicNumber != this.baseMagicNumber);
        initializeDescription();
    }
}