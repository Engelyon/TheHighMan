package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class CuidadoAlimentar extends BaseCard {
    public static final String ID = makeID("CuidadoAlimentar");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int BLOCK = 12;
    private static final int HEAL = 2;
    private static final int COMIDO_THRESHOLD = 3;

    public CuidadoAlimentar() {
        super(ID, info);
        setBlock(BLOCK, BLOCK + 4); // 12 → 16 de Bloqueio com upgrade
        setMagic(HEAL, HEAL + 1);   // 2 → 3 de Cura com upgrade

        this.rawDescription = "Se tiver 3+ de Comido, ganhe !B! de Bloqueio e cure !M! de vida.";
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower(ComidoPower.POWER_ID) && p.getPower(ComidoPower.POWER_ID).amount >= COMIDO_THRESHOLD) {
            addToBot(new GainBlockAction(p, BLOCK));
            addToBot(new HealAction(p, p, HEAL));
        }
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(4);         // 12 → 16
            upgradeMagicNumber(1);   // 2 → 3
            initializeDescription();
        }
    }
}