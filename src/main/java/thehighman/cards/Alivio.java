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

public class Alivio extends BaseCard {
    public static final String ID = makeID("Alivio");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int CHAPADO = 1;
    private static final int BLOCK = 5;
    private static final int BLOCK_UPG = 3;

    public Alivio() {
        super(ID, info);
        setBlock(BLOCK);
        setMagic(CHAPADO);
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // aplica Chapado no alvo
        addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, this.magicNumber), this.magicNumber));
        // dá block ao jogador
        addToBot(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(BLOCK_UPG);
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