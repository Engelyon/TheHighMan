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

public class ErvaDaPaz extends BaseCard {
    public static final String ID = makeID("ErvaDaPaz");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL,
            2
    );

    private static final int CHAPADO_AMOUNT = 1;
    private static final int BLOCK_AMOUNT = 10;
    private static final int BLOCK_UPG = 4;
    public ErvaDaPaz() {
        super(ID, info);
        setMagic(CHAPADO_AMOUNT);
        setBlock(BLOCK_AMOUNT, BLOCK_UPG);
        this.exhaust = true;
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int chapadoToApply = this.magicNumber;
        int blockToGive = this.block;
        if (p != null) {
            if (chapadoToApply > 0) {
                addToBot(new ApplyPowerAction(p, p, new ChapadoPower(p, chapadoToApply), chapadoToApply));
            }
            if (blockToGive > 0) {
                addToBot(new GainBlockAction(p, p, blockToGive));
            }
        }
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().monsters != null) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo != null && !mo.isDeadOrEscaped()) {
                    if (chapadoToApply > 0) {
                        addToBot(new ApplyPowerAction(mo, p, new ChapadoPower(mo, chapadoToApply), chapadoToApply));
                    }
                    if (blockToGive > 0) {
                        addToBot(new GainBlockAction(mo, p, blockToGive));
                    }
                }
            }
        }
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(BLOCK_UPG);
            this.selfRetain = true;
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