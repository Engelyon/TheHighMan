package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class Burgao extends BaseCard {
    public static final String ID = makeID("Burgao");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.UNCOMMON,
            CardTarget.SELF,
            0
    );

    private static final int BLOCK = 3;
    private static final int COMIDO_GAIN = 1;

    public Burgao() {
        super(ID, info);
        setBlock(BLOCK);
        this.exhaust = true;
        this.rawDescription = "Ganhe !B! de Bloqueio e 1 de Comido. Exaure.";
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, this.block));
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, COMIDO_GAIN), COMIDO_GAIN));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(3); // 3 → 6 de Bloqueio
            upgradeMagicNumber(1); // 1 → 2 de Comido
            initializeDescription();
        }
    }
}