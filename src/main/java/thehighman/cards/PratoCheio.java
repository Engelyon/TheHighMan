package thehighman.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import thehighman.character.TheHighman;
import thehighman.powers.ComidoPower;
import thehighman.util.CardStats;

public class PratoCheio extends BaseCard {
    public static final String ID = makeID("PratoCheio");

    private static final CardStats info = new CardStats(
            TheHighman.Meta.CARD_COLOR,
            CardType.SKILL,
            CardRarity.COMMON,
            CardTarget.SELF,
            1
    );

    private static final int COMIDO_AMOUNT = 2;
    private static final int UPG_COMIDO = 1;

    public PratoCheio() {
        super(ID, info);
        setMagic(COMIDO_AMOUNT, UPG_COMIDO);
        this.rawDescription = "Ganhe !M! de Comido.";
        this.keywords.add("comido");
        initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new ComidoPower(p, this.magicNumber), this.magicNumber));
    }
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPG_COMIDO); // 2 → 3 de Comido
            initializeDescription();
        }
    }
}