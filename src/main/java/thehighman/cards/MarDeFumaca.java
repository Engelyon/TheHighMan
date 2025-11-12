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

public class MarDeFumaca extends BaseCard {
    public static final String ID = makeID("MarDeFumaca");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.ALL_ENEMY,
            1
    );

    private static final int CHAPADO_AMOUNT = 1;

    public MarDeFumaca() {
        super(ID, info);
        setMagic(CHAPADO_AMOUNT);
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int defesa = 0;
        if (AbstractDungeon.getCurrRoom() != null && AbstractDungeon.getCurrRoom().monsters != null) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (mo != null && !mo.isDeadOrEscaped()) {
                    addToBot(new ApplyPowerAction(mo, p, new ChapadoPower(mo, magicNumber), magicNumber));

                }
            }
        }
        for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++)
            if (!AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isDead){
                defesa += magicNumber;
            }
        addToBot(new GainBlockAction(p, p, defesa));
    }

    @Override
    public void upgrade() {
        super.upgrade();
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