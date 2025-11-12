package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ChapadoPower;
import thehighman.powers.SedaPower;
import thehighman.util.CardStats;

public class FocoFino extends BaseCard {
    public static final String ID = makeID("FocoFino");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.ENEMY,
            1
    );

    private static final int CHAPADO_AMOUNT = 1;
    private static final int UPG_CHAPADO = 1;
    private static final int DRAW_AMOUNT = 1;
    private static final int DRAW_BONUS_UPG = 1; // no upgrade compra +1 carta

    public FocoFino() {
        super(ID, info);
        setMagic(CHAPADO_AMOUNT, UPG_CHAPADO);
        this.keywords.add("chapado");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // Aplica Chapado ao inimigo (usa magicNumber, que já pode ser modificado por Seda)
        addToBot(new ApplyPowerAction(m, p, new ChapadoPower(m, this.magicNumber), this.magicNumber));

        // Compra cartas: base DRAW_AMOUNT, se upada compra DRAW_BONUS_UPG a mais
        int draws = DRAW_AMOUNT + (upgraded ? DRAW_BONUS_UPG : 0);
        addToBot(new DrawCardAction(p, draws));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            super.upgrade();
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